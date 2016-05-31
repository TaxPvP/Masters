package com.owen.masters.command.impl;

import com.owen.masters.command.Command;
import com.owen.masters.command.annotation.CommandEntry;
import com.owen.masters.utility.Invoker;
import com.owen.masters.value.Value;
import com.owen.masters.value.context.ValueContext;

@CommandEntry
public class CommonCommands extends Command {

	public CommonCommands() {
		super(new String[] {"client", "cl"}, "Client commands", "-cl <command> <?<value>>");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void run(String[] args) {
		final String id = args[1];
		
		switch (id) {
		case "tabgui":
			final Value tab = ValueContext.get("tabgui_display");
			final boolean tabValue = (boolean) ValueContext.get("tabgui_display").getValue();
			tab.setValue(!tabValue);
			
			Invoker.writeChatMessage(String.format("Set tab gui display status to %s", !tabValue ? "§2true" : "§cfalse"));
			break;
		case "hud":
			final Value hud = ValueContext.get("hud_display");
			final boolean hudValue = (boolean) ValueContext.get("hud_display").getValue();
			hud.setValue(!hudValue);
			
			Invoker.writeChatMessage(String.format("Set hud display status to %s", !hudValue ? "§2true" : "§cfalse"));
			break;
		case "array":
			final Value array = ValueContext.get("arraylist_display");
			final boolean arrayValue = (boolean) ValueContext.get("arraylist_display").getValue();
			array.setValue(!arrayValue);
			
			Invoker.writeChatMessage(String.format("Set array display status to %s", !arrayValue ? "§2true" : "§cfalse"));
			break;
		}
	}

}
