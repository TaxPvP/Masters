package com.owen.masters.module.enums;

public enum EnumCategory {

	Combat(0xAA0000), Movement(0x5555FF), Player(0xFFFF55), Render(0x00AAAA), World(
			0x55FFFF), Other(0xFFAA00), Misc(0x55FF55), Minigame(0x555555), Exploit(
			0xFF5555);

	private final int color;

	EnumCategory(final int color) {
		this.color = color;
	}

	public final int getColor() {
		return color;
	}

}
