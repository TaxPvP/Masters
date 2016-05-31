package com.owen.masters.event.events.chat;

import com.owen.masters.eventapi.callable.EventCancellable;

public class EventSendChatMessage extends EventCancellable {

	private String message;

	public EventSendChatMessage(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

}
