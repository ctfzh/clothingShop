package com.tl.customclothing.util;

import java.util.Collection;

/**
 * Created by tianlin on 2017/1/23.
 * Tel : 15071485690
 * QQ 953108373
 * Function :
 */

public class NullUtils
{
	/**
	 * 用于比较字符串与集合对象是否为空或空值
	 * @param object
	 * @return
	 */
    @SuppressWarnings("rawtypes")
	public static boolean isEmpty (Object object)
    {
        if(object == null)
            return true;

        if(object instanceof String)
        {
            String temp = (String) object;
            if(temp.equals(""))
                return true;
        }
        else if(object instanceof Collection)
        {
            Collection temp = (Collection) object;
            if(temp.size() == 0)
                return true;
        }
        else
        {
            throw new RuntimeException("类型不支持！");
        }

        return false;
    }
}
