package com.owen.masters.eventapi.callable;

public interface Cancellable {

	boolean isCancelled();

	void setCancelled(boolean state);

}
