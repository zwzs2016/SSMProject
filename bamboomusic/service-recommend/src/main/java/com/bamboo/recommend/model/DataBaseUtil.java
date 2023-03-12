package com.bamboo.recommend.model;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataBaseUtil {
	private static DataSource ds;

	public static void createDataSource() throws Exception {
		try {
			Context context = new InitialContext();
			if (context == null) {
				throw new Exception("create context failed!");
			}
			ds = (DataSource) context.lookup("java:comp/env/jdbc/movie");
			if (ds == null) {
				Thread.sleep(2000L);
				ds = (DataSource) context.lookup("java:comp/env/jdbc/movie");
				if (ds == null)
					throw new Exception("get datasource failed!");
			}
		} catch (NamingException ne) {
			throw ne;
		} catch (Exception e) {
			throw e;
		}
	}

	public static DataSource getDataSource() {
		try {
			if (ds == null) {
				createDataSource();
			}
			return ds;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnection() {
		DataSource dataSource = getDataSource();
		try {
			return dataSource.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Connection getConnectionFromDataSource() {
		try {
			Connection con = null;
			if (ds == null) {
				createDataSource();
			}
			return ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}