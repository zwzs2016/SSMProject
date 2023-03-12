package com.bamboo.controller;

import com.bamboo.constant.request.RedisExecuteStatus;
import com.bamboo.constant.request.SqlExecuteStatus;
import com.bamboo.dto.BambooMusicInfoDTO;
import com.bamboo.dto.UserDTO;
import com.bamboo.entity.BambooMusicInfo;
import com.bamboo.entity.Role;
import com.bamboo.entity.User;
import com.bamboo.entity.UserWithRole;
import com.bamboo.entity.out.ResponseEntityResult;
import com.bamboo.mapper.RoleMapper;
import com.bamboo.mapper.UserMapper;
import com.bamboo.mapper.UserWithRoleMapper;
import com.bamboo.service.BambooMusicInfoService;
import com.bamboo.service.UserService;
import com.bamboo.service.feign.KafkaFeignService;
import com.bamboo.util.RedisUtil;
import com.bamboo.vo.UserVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.uwan.common.constant.KafkaSendMessageOperate;
import com.uwan.common.dto.KafkaSendMessageDTO;
import com.uwan.common.dto.MusicInfoDTO;
import com.uwan.common.util.JwtTokenUtils;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.jasypt.encryption.StringEncryptor;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("user")
@Api("用户操作")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserWithRoleMapper userWithRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private BambooMusicInfoService bambooMusicInfoService;

    @Qualifier("myStringEncryptor")
    @Autowired
    StringEncryptor stringEncryptor;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RBloomFilter bloomFilter;

    @Operation(summary = "测试获取用户信息")
    @GetMapping("getAll")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userMapper.selectList(null));
    }

    @Operation(summary = "用户注册")
    @PostMapping("addUser")
    public ResponseEntity addUser(@Valid @RequestBody UserDTO userDTO){
        if (userDTO.getPassword().equals(userDTO.getRepassword())){
            User user=new User();
            user.setName(userDTO.getName());
            user.setSex(userDTO.getSex());
            user.setAge(userDTO.getAge());
            user.setHeight(userDTO.getHeight());
            user.setPassword(userDTO.getPassword());
            if (SqlExecuteStatus.INSERT_SUCCESS.getValue()==userService.addUser(user)){
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(SqlExecuteStatus.INSERT_SUCCESS.getMsg());
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(SqlExecuteStatus.INSERT_FAIL.getMsg());
    }

    @Operation(summary = "获取用户信息(livecode),开启电台")
    @PostMapping(value = "/username")
    public ResponseEntity currentUserName(Authentication authentication,@Valid @RequestBody BambooMusicInfoDTO bambooMusicInfoDTO) throws SQLException {
        String username=authentication.getName();
        //先去redis获取
        UserVO userVOformRedis= (UserVO) redisTemplate.opsForValue().get(username);
        if (userVOformRedis!=null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userVOformRedis);
        }

        if (username!=null){
            //是当前用户
            QueryWrapper<BambooMusicInfo> queryWrapper=Wrappers.query();
            queryWrapper.eq("author",username);
            long count = bambooMusicInfoService.count(queryWrapper);
            if (count==1){
                //如果是当前用户，则返回userVO信息
                UserVO userVO=userService.getUserVo(username);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(userVO);
            }
        }


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

        //liveCode生成
        String liveCode=stringEncryptor.encrypt(username);
        String token = JwtTokenUtils.createToken(liveCode, false);

        userVO.setUsername(username);
        userVO.setRoles(roleList);
        userVO.setLivecode(liveCode);
        userVO.setToken(token);

        //存入musicinfo
        bambooMusicInfoService.beforeInsertCheck(username);
        bambooMusicInfoDTO.setAuthor(username);
        bambooMusicInfoDTO.setLiveCode(liveCode);
        bambooMusicInfoDTO.setToken(token);
        int result=userService.saveToMusicInfo(bambooMusicInfoDTO);

        if (result==SqlExecuteStatus.INSERT_FAIL.getValue()){
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
        }

        //存入Redis
        redisTemplate.opsForValue().set(username,userVO,1, TimeUnit.DAYS);

        //布隆过滤存储
        bloomFilter.add(username);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userVO);
    }

    @Operation(summary = "关闭电台")
    @PostMapping("/shutdown")
    public ResponseEntity shutdownmusic(Authentication authentication){
        String username=authentication.getName();
        Integer shutdown = null;
        if (username!=null){
            //关闭电台删除电台信息
            QueryWrapper<BambooMusicInfo> queryWrapper=Wrappers.query();
            queryWrapper.eq("author",username);
            long count = bambooMusicInfoService.count(queryWrapper);
            boolean remove = bambooMusicInfoService.remove(queryWrapper);
            if (!remove && count==1) return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                            new ResponseEntityResult<>(String.valueOf(HttpStatus.BAD_REQUEST.value()),SqlExecuteStatus.DELETE_FAIL.getMsg(),null,Boolean.FALSE)
                    );
            shutdown = userService.shutdown(username);
            if (shutdown== RedisExecuteStatus.DELETE_SUCCESS.getValue()){
                //异步通知
                bambooMusicInfoService.delete(username);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(
                                new ResponseEntityResult<>(String.valueOf(HttpStatus.OK.value()),RedisExecuteStatus.DELETE_SUCCESS.getMsg(),null,Boolean.TRUE)
                        );
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        new ResponseEntityResult<>(String.valueOf(HttpStatus.BAD_REQUEST.value()),RedisExecuteStatus.valueOf(shutdown).getMsg(),null,false)
                );
    }

    @Operation(summary = "是否当前用户")
    @PostMapping(value = "/iscurrentuser",consumes ="application/json" ,produces = "application/json")
    public ResponseEntity iscurrentuser(@RequestBody Map<String, Object> map, Authentication authentication){
        String username=authentication.getName();
        String liveCode= (String) map.get("liveCode");
        if (!StringUtils.isEmpty(liveCode)){
            if (stringEncryptor.decrypt(liveCode).equals(authentication.getName())){
                return ResponseEntity.status(HttpStatus.OK)
                        .body(null);
            }
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(null);
    }
}
