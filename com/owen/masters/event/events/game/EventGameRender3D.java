package com.owen.masters.event.events.game;

import com.owen.masters.eventapi.callable.EventCancellable;

public class EventGameRender3D extends EventCancellable {

	private final float partialTicks;

	public EventGameRender3D(final float partialTicks2) {
		this.partialTicks = partialTicks2;
	}

	public float getPartialTicks() {
		return partialTicks;
	}

}
