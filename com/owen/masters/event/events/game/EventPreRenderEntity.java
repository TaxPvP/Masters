package com.owen.masters.event.events.game;

import net.minecraft.entity.Entity;

import com.owen.masters.eventapi.callable.Event;

public class EventPreRenderEntity implements Event {

	private final Entity entity;

	public EventPreRenderEntity(final Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

}
