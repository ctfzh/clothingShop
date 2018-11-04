package com.tl.customclothing.test;

import java.util.List;

import com.tl.customclothing.mapper.CountMapper;
import com.tl.customclothing.util.database.JDBCTemplate;

public class Test_001_connector
{

	public static void main(String[] args)
	{

		// 测试连接数据库
		JDBCTemplate jdbcTemplate = new JDBCTemplate();
		String sql = "call test001_lianjie()";
		List<Object> list = jdbcTemplate.query(sql, new CountMapper(), null);
		System.out.println(list.get(0));
	}

}
