package com.owen.masters.listener.listeners;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;

import com.owen.masters.command.Command;
import com.owen.masters.command.context.CommandContext;
import com.owen.masters.event.events.chat.EventChatType;
import com.owen.masters.event.events.game.EventGameTick;
import com.owen.masters.eventapi.EventManager;
import com.owen.masters.eventapi.Subscriber;
import com.owen.masters.listener.Listener;

public class CommandCompleteListener extends Listener {
	
	private String cur;
	private int length;

	public CommandCompleteListener() {
		super("CommandCompleteListener");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEnable() {
		EventManager.listen(this);
	}
	
	@Subscriber
	public void onGameTick(final EventGameTick event) {
		if (cur != null && Minecraft.getMinecraft().currentScreen instanceof GuiChat) {
			GL11.glPushMatrix();
			Minecraft.getMinecraft().fontRendererObj.drawString(cur, 3, 557, 0x50FFFFFF);
			GL11.glPopMatrix();
			return;
		}
		else
		{
			cur = null;
		}
	}
	
	@Subscriber
	public void eventTypeInChat(final EventChatType event) {
		if (GuiChat.inputField.getText().startsWith("-")) {
			this.cur = null;
			final String typed = String.valueOf(event.getTyped());
			for (final Command command : CommandContext.getCommandList()) {
				for (final String str : command.getNames()) {
					if (str.substring(0).contains(typed)) {
						this.length = event.getLength();
						this.cur = command.getSyntax();
					}
				}
			}
		}
	}

}
