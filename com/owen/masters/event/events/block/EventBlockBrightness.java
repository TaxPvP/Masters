package com.owen.masters.event.events.block;

import com.owen.masters.eventapi.callable.Event;

public class EventBlockBrightness implements Event {

	private int brightness;

	public EventBlockBrightness(final int brightness) {
		this.brightness = brightness;
	}

	public int getBrightness() {
		return brightness;
	}

	public void setBrightness(final int light) {
		this.brightness = light;
	}

}
