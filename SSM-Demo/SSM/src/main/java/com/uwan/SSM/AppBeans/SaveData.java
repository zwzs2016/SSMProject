package com.uwan.SSM.AppBeans;

import com.uwan.SSM.AppEntity.Student;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SaveData {
    public static void sava(Long id,String name,String address) throws IOException {
        //1.读取配置文件，生成字节输入流
        InputStream in = Resources.getResourceAsStream("MapperXml/SqlMapConfig.xml");
        //2.获取SqlSessionFactory
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.获取sqlsession对象
        SqlSession sqlSession = factory.openSession();
        //4.获取dao的代理对象
//        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        StudentSavaMapper sm=sqlSession.getMapper(StudentSavaMapper.class);
        int sid = sm.insertstudent(new Student(id, name, address));
        sqlSession.commit();
        sqlSession.close();
        in.close();
    }
}
