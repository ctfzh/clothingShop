package com.tl.customclothing.controller;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestController
{
	public void test(HttpServletRequest request,HttpServletResponse response) throws ParseException
	{
		System.out.println("TestController的test被正常调用");
	}
}
