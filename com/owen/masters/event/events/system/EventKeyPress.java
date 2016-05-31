package com.owen.masters.event.events.system;

import com.owen.masters.eventapi.callable.Event;

public class EventKeyPress implements Event {

	private final String key;

	public EventKeyPress(final String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
