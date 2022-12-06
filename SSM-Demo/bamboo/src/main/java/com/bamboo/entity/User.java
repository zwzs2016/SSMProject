package com.bamboo.entity;

import com.bamboo.mapper.RoleMapper;
import com.bamboo.mapper.UserWithRoleMapper;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable , UserDetails {
    private static final long serialVersionUID=1L;

    @Autowired
    UserWithRoleMapper userWithRoleMapper;

    @Autowired
    RoleMapper roleMapper;

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

    @TableField("sex")
    private String sex;

    @TableField("age")
    private String age;

    @TableField("height")
    private String height;

    @TableField("password")
    private String password;

    @TableField("locked")
    private Boolean locked;

    @TableField("enabled")
    private Boolean enabled;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities= new ArrayList<>();
        List<UserWithRole> userWithRoleList = userWithRoleMapper.selectList(null);
        for (UserWithRole userWithRole:userWithRoleList){
            Map<String,Object> map=new HashMap<>();
            map.put("role_id",userWithRole.getRoleId());
            List<Role> roles = roleMapper.selectByMap(map);
            for (Role r:roles) {
                authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
            }
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
