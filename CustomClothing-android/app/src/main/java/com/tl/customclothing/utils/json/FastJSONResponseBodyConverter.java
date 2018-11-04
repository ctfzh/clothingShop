package com.tl.customclothing.utils.json;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class FastJSONResponseBodyConverter<T> implements Converter<ResponseBody, T>
{

    private final Type type;

    FastJSONResponseBodyConverter(Type type)
    {
        this.type = type;
    }


    /**
     * 把网络响应体转成相应的对象
     *
     * @param value
     * @return
     * @throws IOException
     */
    @Override
    public T convert(ResponseBody value) throws IOException
    {
        // 取得响应体的JSON字符串
        String reader = value.string();
        try
        {
            // 把JSON字符串用JSON工具类转成对应类型的对象
            return JSON.parseObject(reader, type);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }
}
