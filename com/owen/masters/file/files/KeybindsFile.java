package com.owen.masters.file.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import com.owen.masters.file.ConfigFile;
import com.owen.masters.logger.Log;
import com.owen.masters.module.Module;
import com.owen.masters.module.context.ModuleContext;

public class KeybindsFile extends ConfigFile {

	public KeybindsFile() {
		super("Keybinds");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save() throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(
				this.file))) {
			ModuleContext.getModuleList().stream().forEach(
					module -> {
						try {
							writer.write(String.format("%s:%s" + System.lineSeparator(),
									module.getName(), module.getKeybind() == ' ' ? "null" : module.getKeybind()));
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					});
		}
	}

	@Override
	public void load() throws IOException {
		Files.lines(this.file.toPath()).forEach(line -> {
			try {
				
				final Module module = ModuleContext.getByName(line.split(":")[0]);
				
				if (!(line.split(":")[1] == "null")) {
					module.setKeybind(line.split(":")[1].toUpperCase().toCharArray()[0]);
					Log.log(String.format("Set module %s's keybind to: %s", module.getName(), module.getKeybind()));
				}
				else
				{
					module.setKeybind(' ');
				}
				
			} catch (Exception e) {}
		});
	}

}
