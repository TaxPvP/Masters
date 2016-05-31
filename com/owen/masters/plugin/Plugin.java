package com.owen.masters.plugin;

import com.owen.masters.plugin.enums.PluginType;

public class Plugin {
	private final String name, path;
	private final PluginType type;

	public Plugin(final String name, final String path, final PluginType type) {
		this.name = name;
		this.path = path;
		this.type = type;
	}

	public PluginType getType() {
		return type;
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

}
