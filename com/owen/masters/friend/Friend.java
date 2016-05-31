package com.owen.masters.friend;

public class Friend {

	private final String name;
	private String alias;

	public Friend(final String name, final String alias) {
		this.name = name;
		this.alias = alias;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

}
