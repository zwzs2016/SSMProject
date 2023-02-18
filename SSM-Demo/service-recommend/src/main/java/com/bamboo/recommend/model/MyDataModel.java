package com.bamboo.recommend.model;

import org.apache.mahout.cf.taste.impl.model.jdbc.ConnectionPoolDataSource;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MyDataModel {

	public static JDBCDataModel myDataModel() {
        MysqlDataSource dataSource = new MysqlDataSource();
        JDBCDataModel dataModel = null;
        try {
            dataSource.setServerName("localhost");
            dataSource.setUser("root");
            dataSource.setPassword("zw123");
            dataSource.setDatabaseName("myproject");


            ConnectionPoolDataSource connectionPool=new ConnectionPoolDataSource(dataSource);
            // use JNDI
            dataModel = new MySQLJDBCDataModel(connectionPool,"room_preferences", "user_id", "room_id","preference","create_time");


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return dataModel;
    } 

}
