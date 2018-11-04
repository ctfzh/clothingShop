package com.tl.customclothing.util.database;

import java.sql.SQLException;

public class TransactionImpl implements Transaction
{
	public void begin()
	{
		try
		{
			JDBCUtil.getConn().setAutoCommit(false);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	// 这里进行里连接的关闭，连接关闭后不能进行数据库操作
	public void commit()
	{
		try
		{
			JDBCUtil.getConn().commit();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			JDBCUtil.close();
		}
	}

	public void rollback()
	{
		try
		{
			JDBCUtil.getConn().rollback();
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			JDBCUtil.close();
		}
	}
}
