package com.owen.masters.listener.listeners;

import com.owen.masters.command.Command;
import com.owen.masters.command.context.CommandContext;
import com.owen.masters.event.events.chat.EventSendChatMessage;
import com.owen.masters.eventapi.EventManager;
import com.owen.masters.eventapi.Subscriber;
import com.owen.masters.listener.Listener;
import com.owen.masters.utility.Invoker;

public class CommandExecuteListener extends Listener {

	public CommandExecuteListener() {
		super("CommandExecuteListener");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEnable() {
		EventManager.listen(this);
	}

	@Subscriber
	public void eventSendChatMessage(final EventSendChatMessage event) {
		if (event.getMessage().startsWith("-") || event.getMessage().substring(0, 1).equalsIgnoreCase("-")) {
			event.setCancelled(true);
			boolean commandFound = false;			
			for (final Command command : CommandContext.getCommandList()) {
				final String[] args = event.getMessage().split(" ");
				final String id = args[0].substring(1);
								
				for (final String ids : command.getNames()) {
					if (id.equalsIgnoreCase(ids)) {
						commandFound = true;
						command.run(args);
					}
				}
			}
			
			if (!commandFound) {
				Invoker.writeChatMessage("Couldn't find command matching those paramaters");
			}
		}
	}

}
