package com.owen.masters.utility.module;

public class WaypointDestination {

	private final String name, server;
	private final double[] pos;
	private final int dimension;

	public WaypointDestination(String name, String server, double[] pos, int dimension) {
		this.name = name;
		this.server = server;
		this.pos = pos;
		this.dimension = dimension;
	}

	public String getName() {
		return name;
	}

	public String getServer() {
		return server;
	}

	public double[] getPos() {
		return pos;
	}

	public int getDimension() {
		return dimension;
	}
}