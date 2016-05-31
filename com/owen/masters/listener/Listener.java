package com.owen.masters.listener;

public abstract class Listener {

	private final String name;

	public Listener(final String name) {
		this.name = name;
	}

	public final String getName() {
		return name;
	}

	public abstract void onEnable();

}
