package com.owen.masters.file;

import java.io.File;
import java.io.IOException;

import com.owen.masters.common.Commons;
import com.owen.masters.logger.Log;

public abstract class ConfigFile {

	protected final String name;
	protected final File file;

	public ConfigFile(final String name) {
		this.name = name;
		this.file = new File(Commons.BASE_DIRECTORY() + "/" + name + ".txt");

		try {
			if (!file.exists() || file.isDirectory() && file.createNewFile()) {
				file.createNewFile();
				Log.log("Created config file: " + name);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void save() throws IOException;

	public abstract void load() throws IOException;

}
