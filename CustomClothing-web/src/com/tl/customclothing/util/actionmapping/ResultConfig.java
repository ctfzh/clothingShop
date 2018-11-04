package com.tl.customclothing.util.actionmapping;

public class ResultConfig
{
	private String name;
	private String path;
	private boolean redirect = false;

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPath()
	{
		return this.path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public boolean isRedirect()
	{
		return this.redirect;
	}

	public void setRedirect(boolean redirect)
	{
		this.redirect = redirect;
	}
}
