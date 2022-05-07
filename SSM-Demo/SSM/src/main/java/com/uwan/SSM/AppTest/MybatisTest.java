package com.uwan.SSM.AppTest;

import com.uwan.SSM.AppBeans.StudentSavaMapper;
import com.uwan.SSM.AppEntity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;

public class MybatisTest {
    @Test
    public void testFindall() throws Exception {
        //1.读取配置文件，生成字节输入流
        InputStream in = Resources.getResourceAsStream("MapperXml/SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //4.获取dao的代理对象
//        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
//        StudentMapper sm=sqlSession.getMapper(StudentMapper.class);
        StudentSavaMapper sm=sqlSession.getMapper(StudentSavaMapper.class);
        int sid = sm.insertstudent(new Student(2L, "王五", "杭州"));
        System.out.println(sid);
//        Student s1=sm.getStudent(1L);
//        List<Student> stu=sm.getStudent(1L);
//        for (Student st:stu){
//            System.out.println(st);
//        }
//        for (Long i:s1.getId())
        //5执行查询所有方法
//        List<User> users = userDao.findAll();
//        for (User user:users){
//            System.out.println(user);
//        }
        //释放资源
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }
}
