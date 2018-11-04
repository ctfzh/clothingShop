package com.tl.customclothing.http.response;

public class ResponseTemplate<T>
{

    // 返回的servlet
    private String servlet;
    // 返回的内容
    private T content;
    // 返回提供的消息
    private String msg;
    // 返回的状态
    private String status;

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

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "RegisterResponse [servlet=" + servlet + ", content=" + content + ", msg=" + msg + ", status=" + status
                + "]";
    }


}
