package com.owen.masters.tabgui;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;

public class GuiHandler {
	private Minecraft mc;
	private boolean[] keyStates;
	public GuiClient gui;

	public GuiHandler() {
		this.mc = Minecraft.getMinecraft();
		this.keyStates = new boolean[256];
		this.gui = new GuiClient();
	}

	public final GuiClient getGuiClient() {
		return gui;
	}

	public boolean checkKey(final int i) {
		boolean b;
		if (this.mc.currentScreen != null) {
			b = false;
		} else if (Keyboard.isKeyDown(i) != this.keyStates[i]) {
			final boolean[] keyStates = this.keyStates;
			b = !this.keyStates[i];
			keyStates[i] = b;
		} else {
			b = false;
		}
		return b;
	}

	public void checkKeys() {
		if (this.checkKey(Keyboard.KEY_UP)) {
			this.gui.parseKeyUp();
		}
		if (this.checkKey(Keyboard.KEY_DOWN)) {
			this.gui.parseKeyDown();
		}
		if (this.checkKey(Keyboard.KEY_LEFT)) {
			this.gui.parseKeyLeft();
		}
		if (this.checkKey(Keyboard.KEY_RIGHT)) {
			this.gui.parseKeyRight();
		}
		if (this.checkKey(28)) {
			this.gui.parseKeyToggle();
		}
	}
}