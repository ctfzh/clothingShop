package com.tl.customclothing.util.actionmapping;

import java.util.HashMap;
import java.util.Map;

public class ActionMappingsConfig
{
	private Map<String, ActionConfig> actionConfigs = new HashMap<>();

	public Map<String, ActionConfig> getActionConfigs()
	{
		return this.actionConfigs;
	}

	public void setActionConfigs(Map<String, ActionConfig> actionConfigs)
	{
		this.actionConfigs = actionConfigs;
	}

	public void addActionConfig(ActionConfig config)
	{
		this.actionConfigs.put(config.getPath(), config);
	}

	public ActionConfig findActionConfig(String path)
	{
		return (ActionConfig) this.actionConfigs.get(path);
	}
}
