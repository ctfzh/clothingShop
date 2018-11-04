package com.tl.customclothing.utils.json;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

public class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody>
{

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Type type;

    FastJsonRequestBodyConverter(Type type)
    {
        this.type = type;
    }

    /**
     * 目标类转JSON格式的网络请求体
     *
     * @param target
     * @return
     * @throws IOException
     */
    @Override
    public RequestBody convert(T target) throws IOException
    {

        // 开缓存
        Buffer buffer = new Buffer();
        // 开刷写器
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        try
        {
            JSON.writeJSONStringTo(target, writer);
            writer.flush();
        } catch (IOException e)
        {
            throw new AssertionError(e); // Writing to Buffer does no I/O.
        }
        // 读取buffer的字节串，创建成请求体
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());


    }
}
