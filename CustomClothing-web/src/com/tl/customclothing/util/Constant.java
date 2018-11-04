package com.tl.customclothing.util;

public class Constant
{

	// 配置数据库的文件名
	public static final String DATA_BASE_FILE_NAME = "datasorce.properties";
	// 工厂对象的全类名配置
	public static final String OBJECT_CREATE_FILE_NAME = "object.properties";
	// Servlet的配置
	public static final String MVC_ACTION_MAPPING_FILE_NAME = "mvc.xml";
	// 空值定义
	public static final String NULL = "null";
	// 读取字节流的额外的空间
	public static final int EXTRA_HEAP_SIZE = 15 * 1024;

	// 返回状态
	public static final String STATUS_SUCCESS = "0001";
	public static final String STATUS_ERROR = "0002";

	// 注册
	public static final String REGISTER_SUCCESS = "S";
	public static final String REGISTER_ERROR = "E";
	
	// 登录,同步用户数据
	public static final String LOGIN_SUCCESS = "S";
	public static final String LOGIN_ERROR = "E";
	public static final String ASYNC_SUCCESS = "S";
	public static final String ASYNC_FAILED = "E";
	
	// 下单
	public static final String SUBMIT_SUCCESS = "下单成功";
	public static final String SUBMIT_FAILED = "下单失败";
	
	// 分页的每页大小
	public static int PAGE_SIZE = 7;
	
	// 评论
	public static String POST_COMMENT_SUCCESS = "评价成功";
	public static String POST_COMMENT_FAILED = "评价失败";

}
