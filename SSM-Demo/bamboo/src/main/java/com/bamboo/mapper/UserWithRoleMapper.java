package com.bamboo.mapper;

import com.bamboo.entity.UserWithRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserWithRoleMapper extends BaseMapper<UserWithRole> {
}
