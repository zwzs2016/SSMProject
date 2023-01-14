package com.bamboo.service.impl;

import com.anji.captcha.service.CaptchaService;
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
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.uwan.common.util.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jasypt.encryption.StringEncryptor;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
        queryWrapper.select("id");
        User newuser = userMapper.selectOne(queryWrapper);
        if (newuser != null){
            return SqlExecuteStatus.INSERT_FAIL.getValue();
        }else {
            //新用户密码采用BCryptPasswordEncoder(10)格式存入数据库
            User userRegister=new User();
            userRegister.setName(user.getName());
            userRegister.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
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

        //开始插入
        if (bambooMusicInfoService.save(bambooMusicInfo)) {
            //同步到es
            bambooMusicInfoService.addToElasticSearch(bambooMusicInfo);
            return SqlExecuteStatus.INSERT_SUCCESS.getValue();
        }else {
            return SqlExecuteStatus.INSERT_FAIL.getValue();
        }
    }
}
