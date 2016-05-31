package com.owen.masters.event.events.movement;

import com.owen.masters.eventapi.callable.EventCancellable;

public class EventPlayerMovement extends EventCancellable {

	public double x;
	public double y;
	public double z;

	public EventPlayerMovement(final double x, final double y, final double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public double getZ() {
		return this.z;
	}

	public void setX(final double x) {
		this.x = x;
	}

	public void setY(final double y) {
		this.y = y;
	}

	public void setZ(final double z) {
		this.z = z;
	}
}
