package com.owen.masters.utility;

public class Timer {

	private long prevMS;

	public Timer() {
		this.prevMS = 0L;
	}

	public boolean delay(double d) {
		if (getTime() - getPrevMS() >= d) {
			reset();
			return true;
		}
		return false;
	}

	public void reset() {
		this.prevMS = getTime();
	}

	public long getTime() {
		return System.nanoTime() / 1000000L;
	}

	public long getPrevMS() {
		return this.prevMS;
	}

}
