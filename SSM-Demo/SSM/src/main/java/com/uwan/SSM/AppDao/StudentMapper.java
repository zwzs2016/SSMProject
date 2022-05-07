package com.uwan.SSM.AppDao;

import com.uwan.SSM.AppEntity.Student;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper {
    @Select("select id,name,address from student where id<=#{id}")
    public List<Student> getStudent(Long id);
}
