package com.owen.masters.command;

public abstract class Command {

	private final String name;
	private final String[] ids;
	private final String syntax;

	public Command(final String[] ids, final String name, final String syntax) {
		this.ids = ids;
		this.name = name;
		this.syntax = syntax;
	}

	public String[] getNames() {
		return ids;
	}
	
	public String getIdentifier() {
		return name;
	}

	public String getSyntax() {
		return syntax;
	}

	public abstract void run(final String[] args);

}
