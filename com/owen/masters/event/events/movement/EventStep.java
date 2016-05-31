package com.owen.masters.event.events.movement;

import com.owen.masters.eventapi.callable.EventCancellable;

import net.minecraft.entity.Entity;

public class EventStep extends EventCancellable {

	private Entity entity;

	public EventStep(Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return this.entity;
	}

}
