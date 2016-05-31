package com.owen.masters.event.events.chat;

import com.owen.masters.eventapi.callable.Event;

public class EventChatType implements Event {
	
	private final char typed;
	private final int length;

	public EventChatType(final char typed, final int length) {
		this.typed = typed;
		this.length = length;
	}
	
	public final char getTyped() {
		return typed;
	}
	
	public int getLength() {
		return length;
	}

}
