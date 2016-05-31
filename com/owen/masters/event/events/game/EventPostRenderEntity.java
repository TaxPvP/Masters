package com.owen.masters.event.events.game;

import net.minecraft.entity.Entity;

import com.owen.masters.eventapi.callable.Event;

public class EventPostRenderEntity implements Event {

	private final Entity entity;

	public EventPostRenderEntity(final Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

}
