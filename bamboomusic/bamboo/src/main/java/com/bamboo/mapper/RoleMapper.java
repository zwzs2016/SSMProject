package com.bamboo.mapper;

import com.bamboo.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Scope;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
