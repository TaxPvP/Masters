package com.owen.masters.context;

public abstract class Context {

	private final String name;

	public Context(final String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public abstract void setup();

}
