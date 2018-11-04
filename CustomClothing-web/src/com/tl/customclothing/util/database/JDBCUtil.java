package com.tl.customclothing.util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.tl.customclothing.util.Constant;


public class JDBCUtil
{
	private static DataSource ds;
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

	static
	{
		Properties p = new Properties();
		try
		{
			p.load(JDBCUtil.class.getClassLoader().getResourceAsStream(Constant.DATA_BASE_FILE_NAME));
			ds = BasicDataSourceFactory.createDataSource(p);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Connection getConn()
	{
		Connection conn = null;
		try
		{
			conn = (Connection) threadLocal.get();
			if (conn == null)
			{
				conn = ds.getConnection();
				threadLocal.set(conn);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return conn;
	}

	public static void close(PreparedStatement pstmt, ResultSet rs)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		if (pstmt != null)
			try
			{
				pstmt.close();
			} catch (SQLException e)
			{
				e.printStackTrace();
			}
	}

	public static void close()
	{
		try
		{
			((Connection) threadLocal.get()).close();
			threadLocal.remove();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
