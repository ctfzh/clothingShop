package com.tl.customclothing.http.request;

public class RequestTemplate<T>
{
    // 请求的servlet
    private String servlet;
    // 请求的内容
    private T content;

    public String getServlet()
    {
        return servlet;
    }

    public void setServlet(String servlet)
    {
        this.servlet = servlet;
    }

    public T getContent()
    {
        return content;
    }

    public void setContent(T content)
    {
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "RequestTemplate [servlet=" + servlet + ", content=" + content + "]";
    }


}
