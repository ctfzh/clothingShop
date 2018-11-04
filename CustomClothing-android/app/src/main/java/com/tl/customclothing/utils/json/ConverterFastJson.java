package com.tl.customclothing.utils.json;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

public class ConverterFastJson extends Converter.Factory
{

    public static ConverterFastJson create()
    {
        return new ConverterFastJson();
    }


    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit)
    {
        return new FastJSONResponseBodyConverter<>(type);
    }


    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit)
    {
        return new FastJsonRequestBodyConverter<>(type);
    }
}
