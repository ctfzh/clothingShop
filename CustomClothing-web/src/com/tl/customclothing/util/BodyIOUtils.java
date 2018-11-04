package com.tl.customclothing.util;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.tl.customclothing.param.response.ResponseTemplate;

public class BodyIOUtils
{

	public static void writeBody(HttpServletResponse response, ResponseTemplate responseTemplate)
	{
		ServletOutputStream servletOutputStream = null;

		try
		{
			servletOutputStream = response.getOutputStream();
			
			String json = JSON.toJSONString(responseTemplate);
			
			System.out.println("writeBody json = " + json);
			// 保证字符集统一
			servletOutputStream.write(json.getBytes(Charset.forName("utf-8")));
			
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (servletOutputStream != null)
				try
				{
					servletOutputStream.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
		}

	}

	public static String readBody(HttpServletRequest request)
	{

		String json = Constant.NULL;

		if (request == null)
			return json;

		ServletInputStream inputStream = null;

		try
		{

			inputStream = request.getInputStream();

			int length = request.getContentLength();

			byte bytes[] = new byte[length + Constant.EXTRA_HEAP_SIZE];

			int len = -1;
			if ((len = inputStream.read(bytes)) != -1)
			{
				// 保证字符集统一
				json = new String(bytes, 0, len, Charset.forName("utf-8"));
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		} finally
		{
			if (inputStream != null)
			{
				try
				{
					inputStream.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("getBody json = " + json);

		return json;
	}
}
