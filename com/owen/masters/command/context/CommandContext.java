package com.owen.masters.command.context;

import java.util.ArrayList;
import java.util.List;

import com.owen.masters.command.Command;
import com.owen.masters.command.annotation.CommandEntry;
import com.owen.masters.command.exception.CommandRegisterException;
import com.owen.masters.context.Context;

public class CommandContext extends Context {

	private static List<Command> commandList;

	public CommandContext() {
		super("Commands");
		this.commandList = new ArrayList<>();
	}

	@Override
	public void setup() {
		// TODO Auto-generated method stub

	}

	public static List<Command> getCommandList() {
		return commandList;
	}
	
	public static void registerCommand(final Command command) {
		if (commandList.contains(command)) {
			return;
		}
		
		if (command != null && !commandList.contains(command) && command.getClass().isAnnotationPresent(CommandEntry.class)) {
			commandList.add(command);
		}
		else
		{
			throw new CommandRegisterException("Unable to load command: " + command.getNames());
		}
	}

}
