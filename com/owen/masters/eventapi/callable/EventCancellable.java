package com.owen.masters.eventapi.callable;

public abstract class EventCancellable implements Event, Cancellable {

	private boolean cancelled;

	protected EventCancellable() {
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean state) {
		cancelled = state;
	}

}
