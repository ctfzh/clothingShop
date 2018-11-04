package com.tl.customclothing.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.tl.customclothing.util.database.ObjectMapper;

public class CountMapper implements ObjectMapper
{

	@Override
	public Object rowMapper(ResultSet rs) throws SQLException
	{
		int count = rs.getInt(1);
		return Integer.valueOf(count);
	}

}
