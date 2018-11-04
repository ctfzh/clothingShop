package com.tl.customclothing.factory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.tl.customclothing.util.Constant;

public class ObjectFactory
{
	private static Map<String, Object> objs = new HashMap<>();

	static
	{
		BufferedReader br = new BufferedReader(
				new InputStreamReader(ObjectFactory.class.getClassLoader().getResourceAsStream(Constant.OBJECT_CREATE_FILE_NAME)));
		try
		{
			String line = null;
			while ((line = br.readLine()) != null)
			{
				String[] infos = line.split("=");
				objs.put(infos[0], Class.forName(infos[1]).newInstance());
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static Object get(String key)
	{
		return objs.get(key);
	}
}
