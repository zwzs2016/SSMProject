package com.uwan.SSM.AppBeans;

import org.apache.ibatis.annotations.Insert;

public interface StudentSavaMapper {
    @Insert("insert into student(id,name,address) values(#{id},#{name},#{address})")
    int insertstudent(Student student);
}
