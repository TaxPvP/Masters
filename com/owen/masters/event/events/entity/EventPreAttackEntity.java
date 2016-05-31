package com.owen.masters.event.events.entity;

import net.minecraft.entity.Entity;

import com.owen.masters.eventapi.callable.Event;

public class EventPreAttackEntity implements Event {

	private Entity entity;

	public EventPreAttackEntity(final Entity entity) {
		this.entity = entity;
	}

	public Entity getEntity() {
		return entity;
	}

}
