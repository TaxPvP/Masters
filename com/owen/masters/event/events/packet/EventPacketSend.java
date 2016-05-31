package com.owen.masters.event.events.packet;

import com.owen.masters.eventapi.callable.EventCancellable;

import net.minecraft.network.Packet;

public class EventPacketSend extends EventCancellable {

	private Packet packet;

	public EventPacketSend(final Packet packet) {
		this.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(final Packet packet) {
		this.packet = packet;
	}

}
