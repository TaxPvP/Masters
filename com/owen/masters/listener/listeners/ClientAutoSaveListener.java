package com.owen.masters.listener.listeners;

import java.io.IOException;

import com.owen.masters.event.events.game.EventGameTick;
import com.owen.masters.eventapi.EventManager;
import com.owen.masters.eventapi.Subscriber;
import com.owen.masters.file.files.KeybindsFile;
import com.owen.masters.listener.Listener;
import com.owen.masters.utility.Invoker;
import com.owen.masters.utility.Timer;

public class ClientAutoSaveListener extends Listener {
	
	private final Timer timer;

	public ClientAutoSaveListener() {
		super("ClientAutoSaveListener");
		this.timer = new Timer();
	}

	@Override
	public void onEnable() {
		EventManager.listen(this);
	}
	
	@Subscriber
	public void onGameTick(final EventGameTick event) {
		if (timer.delay(90000L)) {
			Invoker.writeChatMessage("Autosaving...");
			try {
				new KeybindsFile().save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Invoker.writeChatMessage("...Autosaving Complete.");
			this.timer.reset();
		}
	}

}
