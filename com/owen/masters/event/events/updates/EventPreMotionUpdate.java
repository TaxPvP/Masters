package com.owen.masters.event.events.updates;

import com.owen.masters.eventapi.callable.EventCancellable;

public class EventPreMotionUpdate extends EventCancellable {

	public float yaw;
	public float pitch;
	private float headYaw;
	private boolean onGround;

	private final double x, y, z;

	public EventPreMotionUpdate(final float yaw, final float pitch,
			final float headYaw, final boolean onGround, final double x,
			final double y, final double z) {
		this.yaw = yaw;
		this.pitch = pitch;
		this.headYaw = headYaw;
		this.onGround = onGround;
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float getYaw() {
		return yaw;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public float getHeadYaw() {
		return headYaw;
	}

	public void setHeadYaw(float headYaw) {
		this.headYaw = headYaw;
	}

	public boolean isOnGround() {
		return onGround;
	}

	public void setOnGround(boolean onGround) {
		this.onGround = onGround;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

}
