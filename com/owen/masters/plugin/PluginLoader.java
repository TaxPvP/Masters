package com.owen.masters.plugin;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.owen.masters.command.Command;
import com.owen.masters.command.annotation.CommandEntry;
import com.owen.masters.command.context.CommandContext;
import com.owen.masters.common.Commons;
import com.owen.masters.logger.Log;
import com.owen.masters.module.Module;
import com.owen.masters.module.annotation.Loadable;
import com.owen.masters.module.context.ModuleContext;
import com.owen.masters.plugin.enums.PluginType;

public class PluginLoader {

	private final File base_directory = new File(Commons.BASE_DIRECTORY()
			+ "/Plugins");

	private final File module_directory = new File(base_directory.getPath()
			+ "/Modules");
	private final File command_directory = new File(base_directory.getPath()
			+ "/Commands");

	private File[] pluginFiles;

	public void load(final PluginType type) {
		switch (type) {
		case Module:
			this.pluginFiles = null;
			if (!this.module_directory.exists()) {
				this.module_directory.mkdirs();
			} else {
				this.pluginFiles = this.module_directory.listFiles();
				if (this.pluginFiles.length > 0) {
					for (final File file : pluginFiles) {
						final Plugin pluginModule = new Plugin(file.getName(),
								file.toPath().toString(), PluginType.Module);
						try {
							this.loadPlugin(pluginModule);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			break;
			
		case Command:
			this.pluginFiles = null;
			if (!this.command_directory.exists()) {
				this.command_directory.mkdirs();
			} else {
				this.pluginFiles = this.command_directory.listFiles();
				if (this.pluginFiles.length > 0) {
					for (final File file : pluginFiles) {
						final Plugin pluginModule = new Plugin(file.getName(),
								file.toPath().toString(), PluginType.Command);
						try {
							this.loadPlugin(pluginModule);
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					}
				}
			}
			
			break;

		default:
			break;
		}
	}

	private void loadPlugin(final Plugin plugin) throws MalformedURLException {
		JarFile origin = null;
		try {
			origin = new JarFile(plugin.getPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Enumeration<JarEntry> entries = origin.entries();

		URL[] urls = { new URL("jar:file:" + plugin.getPath() + "!/") };
		URLClassLoader cl = URLClassLoader.newInstance(urls);

		while (entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			if (entry.isDirectory() || !entry.getName().endsWith(".class")) {
				continue;
			}
			String className = entry.getName().substring(0,
					entry.getName().length() - 6);
			className = className.replace('/', '.');
			try {
				Class clazz = cl.loadClass(className);

				switch (plugin.getType()) {
				
				case Module:
					if (clazz.isAnnotationPresent(Loadable.class)) {
						final Module module = (Module) clazz.newInstance();
						ModuleContext.register(module);
						module.registerValues();
						Log.log(String
								.format("Successfully loaded plugin module: %s [Bind: %s, Category: %s]",
										module.getName(), module.getKeybind(),
										module.getEnumCategory().name()));

					}
					
				case Command:
					if (clazz.isAnnotationPresent(CommandEntry.class)) {
						final Command command = (Command) clazz.newInstance();
						CommandContext.registerCommand(command);
						Log.log(String
								.format("Successfully loaded plugin command: %s",
										command.getIdentifier()));
					}
				default:
					break;
				}

			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			}
		}
	}

}
