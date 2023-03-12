package com.bamboo.mapper;

import com.bamboo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    /** 如果是主键自动生成的，切记不要传入id，否则会报错 */
//    @Insert("insert into user(name,password,age,sex) values(#{name},#{password},#{age},#{sex})")
//    void insertToMysql(User user);
}
