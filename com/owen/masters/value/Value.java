package com.owen.masters.value;

public class Value<T> {

	private T value;
	private final String identifier;

	public Value(T value, final String identifier) {
		this.value = value;
		this.identifier = identifier;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public String getIdentifier() {
		return identifier;
	}

	public T getValue() {
		return value;
	}

}
