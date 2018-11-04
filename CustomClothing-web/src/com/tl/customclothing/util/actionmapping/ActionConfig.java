package com.tl.customclothing.util.actionmapping;

import java.util.HashMap;
import java.util.Map;

public class ActionConfig
{
	private String path;
	private String classname;
	private String method;
	private String result;
	private Map<String, ResultConfig> resultConfigs = new HashMap<>();

	public String getResult()
	{
		return this.result;
	}

	public void setResult(String result)
	{
		this.result = result;
	}

	public String getPath()
	{
		return this.path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getClassname()
	{
		return this.classname;
	}

	public void setClassname(String classname)
	{
		this.classname = classname;
	}

	public String getMethod()
	{
		return this.method;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}

	public Map<String, ResultConfig> getResultConfigs()
	{
		return this.resultConfigs;
	}

	public void setResultConfigs(Map<String, ResultConfig> resultConfigs)
	{
		this.resultConfigs = resultConfigs;
	}

	public void addResultConfig(ResultConfig config)
	{
		this.resultConfigs.put(config.getName(), config);
	}

	public ResultConfig findResultConfig(String name)
	{
		return (ResultConfig) this.resultConfigs.get(name);
	}
}
