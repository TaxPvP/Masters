package com.owen.masters.listener.context;

import java.util.Arrays;
import java.util.List;

import com.owen.masters.context.Context;
import com.owen.masters.listener.Listener;
import com.owen.masters.listener.listeners.ClientAutoSaveListener;
import com.owen.masters.listener.listeners.CommandCompleteListener;
import com.owen.masters.listener.listeners.CommandExecuteListener;
import com.owen.masters.listener.listeners.IngameUIListener;
import com.owen.masters.listener.listeners.ModuleToggleListener;
import com.owen.masters.logger.Log;

public class ListenerContext extends Context {

	private List<Listener> listenerList;

	public ListenerContext() {
		super("Listeners");
		this.listenerList = Arrays.asList(new ModuleToggleListener(),
				new CommandExecuteListener(), new IngameUIListener(), new CommandCompleteListener(), new ClientAutoSaveListener());
	}

	@Override
	public void setup() {
		if (this.listenerList.size() == 0 || this.listenerList == null) {
			Log.log("!! LISTENER ARRAY IS CORRUPT !!");
			return;
		}

		this.listenerList.stream().forEach(listener -> {
			listener.onEnable();
			Log.log("Enabled Listener: " + listener.getName());
		});
	}

}
