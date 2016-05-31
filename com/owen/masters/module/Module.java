package com.owen.masters.module;

import net.minecraft.client.Minecraft;

import com.owen.masters.eventapi.EventManager;
import com.owen.masters.logger.Log;
import com.owen.masters.module.enums.EnumCategory;
import com.owen.masters.value.Value;
import com.owen.masters.value.context.ValueContext;

public abstract class Module {

	protected static final Minecraft mc = Minecraft.getMinecraft();

	private final String name;
	private char keybind;
	private final EnumCategory enumCategory;
	private String displayName;

	private boolean state;

	public Module(final String name, final char keybind,
			final EnumCategory enumCategory) {
		this.name = name;
		this.keybind = keybind;
		this.enumCategory = enumCategory;
		this.displayName = name;
	}

	public char getKeybind() {
		return keybind;
	}

	public void setKeybind(char keybind) {
		this.keybind = keybind;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public EnumCategory getEnumCategory() {
		return enumCategory;
	}

	public boolean getState() {
		return state;
	}

	public void setState(final boolean state) {
		this.state = state;
	}

	public void toggle() {
		this.state = !state;

		if (this.state) {
			this.onEnable();
			EventManager.listen(this);
		} else {
			this.onDisable();
			EventManager.flush(this);
		}
	}

	protected void submitValue(final Value value) {
		ValueContext.register(value);
	}

	public abstract void onEnable();

	public abstract void onDisable();

	public abstract void registerValues();
}
