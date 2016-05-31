package com.owen.masters.module.context;

import java.util.ArrayList;
import java.util.List;

import com.owen.masters.context.Context;
import com.owen.masters.module.Module;
import com.owen.masters.module.annotation.Loadable;
import com.owen.masters.module.exception.ModuleLoadException;

public class ModuleContext extends Context {

	private static List<Module> moduleList = new ArrayList<>();

	public ModuleContext() {
		super("Modules");
	}

	public static List<Module> getModuleList() {
		return moduleList;
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub

	}

	private static void addModule(final Module module) {
		moduleList.add(module);
	}
	
	public static final Module getByName(final String name) {
		for (final Module module : moduleList) {
			if (name.equalsIgnoreCase(module.getName())) {
				return module;
			}
		}
		
		return null;
	}

	public static final void register(final Module module) {
		if (module != null && !moduleList.contains(module)
				&& module.getClass().isAnnotationPresent(Loadable.class)) {
			addModule(module);
		} else {
			throw new ModuleLoadException("Failed to load module: "
					+ module.getClass());
		}
	}

}
