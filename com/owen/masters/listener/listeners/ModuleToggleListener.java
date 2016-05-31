package com.owen.masters.listener.listeners;

import com.owen.masters.event.events.system.EventKeyPress;
import com.owen.masters.eventapi.EventManager;
import com.owen.masters.eventapi.Subscriber;
import com.owen.masters.listener.Listener;
import com.owen.masters.logger.Log;
import com.owen.masters.module.context.ModuleContext;

public class ModuleToggleListener extends Listener {

	public ModuleToggleListener() {
		super("ModuleToggleListener");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEnable() {
		EventManager.listen(this);
	}

	@Subscriber
	public void eventKeyPress(final EventKeyPress event) {
		ModuleContext
				.getModuleList()
				.stream()
				.forEach(
						module -> {
							if (event.getKey().equalsIgnoreCase(
									String.valueOf(module.getKeybind()))) {
								module.toggle();
							}
						});
	}

}
