package com.bamboo.service.impl;

import com.bamboo.config.MybatisPlusConfig;
import com.bamboo.constant.request.RedisExecuteStatus;
import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.dto.BambooMusicInfoDTO;
import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.entity.Role;
import com.bamboo.entity.User;
import com.bamboo.entity.UserWithRole;
import com.bamboo.mapper.RoleMapper;
import com.bamboo.mapper.UserMapper;
import com.bamboo.mapper.UserWithRoleMapper;
import com.bamboo.service.BambooMusicInfoService;
import com.bamboo.service.UserService;
import com.bamboo.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uwan.common.util.JwtTokenUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    UserWithRoleMapper userWithRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    BambooMusicInfoService bambooMusicInfoService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RBloomFilter bloomFilter;

    @Value("${custom.prefix.live_url}")
    private  String LIVE_URL_PREFIX;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> userWrapper=Wrappers.query();
        userWrapper.eq("name",username);
        User user = userMapper.selectOne(userWrapper);

        QueryWrapper<UserWithRole> queryWrapper= Wrappers.query();
        queryWrapper.eq("user_id",user.getId());
        List<UserWithRole> userWithRoleList = userWithRoleMapper.selectList(queryWrapper);
        List roleIdlist=new ArrayList();
        for (UserWithRole userWithRole:userWithRoleList) {
            roleIdlist.add(userWithRole.getRoleId());
        }
        List roles = roleMapper.selectBatchIds(roleIdlist);
        user.setRoles(roles);
        if(user == null){
            throw new UsernameNotFoundException("账户不存在！");
        }
        return user;
    }

    @Override
    public int addUser(User user){
        QueryWrapper<User> queryWrapper=Wrappers.query();
        queryWrapper.eq("name",user.getUsername());
        queryWrapper.eq("age",user.getAge());
        queryWrapper.select("id,age");
//        MybatisPlusConfig.IdTableNameHandler.initCurrentId(Long.valueOf(user.getAge()));
        User newuser = userMapper.selectOne(queryWrapper);
        if (newuser != null){
            return SqlExecuteStatus.INSERT_FAIL.getValue();
        }else {
            //新用户密码采用BCryptPasswordEncoder(10)格式存入数据库
            User userRegister=new User();
            userRegister.setName(user.getName());
            userRegister.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            userRegister.setAge(user.getAge());
            userRegister.setSex(user.getSex());

            //设置用户状态可用，没有锁定
            userRegister.setEnabled(true);
            userRegister.setLocked(false);
            //执行用户注册
            int adduser = userMapper.insert(userRegister);
            //用户成功注册后，添加用户角色
            if(adduser > 0){
                //创建关联关系
                UserWithRole userWithRole=new UserWithRole();
                userWithRole.setUserId(userRegister.getId());
                //暂且默认普通用户...
                int adduserWithRole = userWithRoleMapper.insert(userWithRole);
                if (adduserWithRole > 0){
                    return SqlExecuteStatus.INSERT_SUCCESS.getValue();
                }else{
                    return SqlExecuteStatus.INSERT_FAIL.getValue();
                }
            }else {
                return SqlExecuteStatus.INSERT_FAIL.getValue();
            }
        }
    }

    @Override
    public int shutdown(String username) {
        //布隆过滤
        boolean contains = bloomFilter.contains(username);
        if (!contains){
            return RedisExecuteStatus.NO_VALUE_EXISTS.getValue();
        }
        UserVO userVO = (UserVO) redisTemplate.opsForValue().get(username);
        if (userVO!=null){
            Boolean delete = redisTemplate.delete(username);
            if (delete){
                return RedisExecuteStatus.DELETE_SUCCESS.getValue();
            }
        }
        return RedisExecuteStatus.DELETE_FAIL.getValue();
    }

    @Override
    public int saveToMusicInfo(BambooMusicInfoDTO bambooMusicInfoDTO) {
        BambooMusicInfo bambooMusicInfo=new BambooMusicInfo();
        bambooMusicInfo.setRoomId(IdWorker.getIdStr());
        bambooMusicInfo.setTitle(bambooMusicInfoDTO.getTitle());
        bambooMusicInfo.setAuthor(bambooMusicInfoDTO.getAuthor());
        bambooMusicInfo.setRemarks(bambooMusicInfoDTO.getRemark());
        bambooMusicInfo.setImgFile(Base64.getDecoder().decode(StringUtils.substringAfter(bambooMusicInfoDTO.getImageBase64File(),";base64,")));
        bambooMusicInfo.setLiveUrl(LIVE_URL_PREFIX+bambooMusicInfoDTO.getLiveCode());
        bambooMusicInfo.setToken(bambooMusicInfoDTO.getToken());

        //开始插入
        if (bambooMusicInfoService.save(bambooMusicInfo)) {
            //同步到es
            bambooMusicInfoService.addToElasticSearch(bambooMusicInfo);
            return SqlExecuteStatus.INSERT_SUCCESS.getValue();
        }else {
            return SqlExecuteStatus.INSERT_FAIL.getValue();
        }
    }

    @Override
    public UserVO getUserVo(String username) {
        UserVO userVOformRedis= (UserVO) redisTemplate.opsForValue().get(username);
        if (userVOformRedis!=null){
            return userVOformRedis;
        }
        //redis不存在去mysql查询
        UserVO userVO=new UserVO();
        QueryWrapper<User> userQueryWrapper= Wrappers.query();
        userQueryWrapper.eq("name",username);
        userQueryWrapper.select("id");
        User user = userMapper.selectOne(userQueryWrapper);
        //查询user role list
        QueryWrapper<UserWithRole> userWithRoleQueryWrapper=Wrappers.query();
        userWithRoleQueryWrapper.eq("user_id",user.getId());
        userWithRoleQueryWrapper.select("role_id");
        List<UserWithRole> userWithRoles = userWithRoleMapper.selectList(userWithRoleQueryWrapper);

        //role表查询用户角色
        List list=new ArrayList();
        for(UserWithRole userWithRole:userWithRoles){
            list.add(userWithRole.getRoleId());
        }
        List<Role> roleList = roleMapper.selectBatchIds(list);

        //在到musicinfo查询 live_url token
        QueryWrapper<BambooMusicInfo> bambooMusicInfoQueryWrapper=Wrappers.query();
        bambooMusicInfoQueryWrapper.eq("author",username);
        bambooMusicInfoQueryWrapper.select("live_url,token");
        //bamboomusic 先决条件判断已不为空
        BambooMusicInfo bambooMusicInfo = bambooMusicInfoService.getOne(bambooMusicInfoQueryWrapper, true);
        String liveUrl=bambooMusicInfo.getLiveUrl();
        String token = bambooMusicInfo.getToken();

        userVO.setUsername(username);
        userVO.setRoles(roleList);
        userVO.setLivecode(StringUtils.substringAfter(liveUrl,"?liveShareUrl="));
        userVO.setToken(token);

        return userVO;
    }
}
