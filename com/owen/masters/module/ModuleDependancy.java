package com.owen.masters.module;

import com.owen.masters.module.context.ModuleContext;

public class ModuleDependancy {
	
	public static final boolean isModulePresent(final String name) {
		for (final Module module : ModuleContext.getModuleList()) {
			if (module.getClass().getSimpleName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

}
