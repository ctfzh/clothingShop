package com.tl.customclothing.util.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCTemplate
{
	public boolean update(String sql, Object[] params)
	{
		PreparedStatement pstmt = null;
		try
		{
			Connection conn = JDBCUtil.getConn();
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt, params);
			boolean result = pstmt.executeUpdate() > 0 ? true : false;
			return result;
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			JDBCUtil.close(pstmt, null);
		}
		return false;
	}

	public List<Object> query(String sql, ObjectMapper om, Object[] params)
	{
		List<Object> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			Connection conn = JDBCUtil.getConn();
			pstmt = conn.prepareStatement(sql);
			setParams(pstmt, params);
			rs = pstmt.executeQuery();
			while (rs.next())
			{
				Object obj = om.rowMapper(rs);
				list.add(obj);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			JDBCUtil.close(pstmt, rs);
		}
		return list;
	}

	private void setParams(PreparedStatement pstmt, Object[] params)
	{
		try
		{
			if ((params != null) && (params.length > 0))
				for (int i = 0; i < params.length; i++)
					pstmt.setString(i + 1, params[i].toString());
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}
