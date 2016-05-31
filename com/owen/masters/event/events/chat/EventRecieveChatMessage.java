package com.owen.masters.event.events.chat;

import net.minecraft.network.Packet;

import com.owen.masters.eventapi.callable.Event;

public class EventRecieveChatMessage implements Event {

	private Packet packet;
	private String message;

	public EventRecieveChatMessage(final Packet packet, final String message) {
		this.packet = packet;
		this.message = message;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		this.packet = packet;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
