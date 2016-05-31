package com.owen.masters.listener.listeners;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;

import org.lwjgl.opengl.GL11;

import com.owen.masters.event.events.game.EventGameTick;
import com.owen.masters.eventapi.EventManager;
import com.owen.masters.eventapi.Subscriber;
import com.owen.masters.listener.Listener;
import com.owen.masters.main.Main;
import com.owen.masters.module.Module;
import com.owen.masters.module.ModuleDependancy;
import com.owen.masters.module.context.ModuleContext;
import com.owen.masters.utility.Invoker;
import com.owen.masters.value.context.ValueContext;

public class IngameUIListener extends Listener {

	private int yPos;

	private FontRenderer fontRenderer;
	private ScaledResolution scaledResolution;

	private List<Module> toRender;

	public IngameUIListener() {
		super("IngameUIListener");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEnable() {
		this.fontRenderer = Minecraft.getMinecraft().fontRendererObj;
		this.scaledResolution = new ScaledResolution(Minecraft.getMinecraft(),
				Minecraft.getMinecraft().displayWidth,
				Minecraft.getMinecraft().displayHeight);
		EventManager.listen(this);
	}

	private void sort() {
		final List<Module> var = this.getToRender();
		Collections.sort(var, new Comparator<Module>() {
			@Override
			public int compare(Module mod, Module mod1) {
				if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(mod
						.getDisplayName()) > Minecraft.getMinecraft().fontRendererObj
						.getStringWidth(mod1.getDisplayName())) {
					return -1;
				}
				if (Minecraft.getMinecraft().fontRendererObj.getStringWidth(mod
						.getDisplayName()) < Minecraft.getMinecraft().fontRendererObj
						.getStringWidth(mod1.getDisplayName())) {
					return 1;
				}
				return 0;
			}
		});

		this.toRender = var;
	}

	private List<Module> getToRender() {
		List<Module> var = new ArrayList<>();
		for (final Module mod : ModuleContext.getModuleList()) {
			if (mod.getState()) {
				var.add(mod);
			}
		}

		return var;
	}

	@Subscriber
	public void onGameTick(final EventGameTick event) {
		this.scaledResolution = new ScaledResolution(Minecraft.getMinecraft(),
				Minecraft.getMinecraft().displayWidth,
				Minecraft.getMinecraft().displayHeight);
		GL11.glPushMatrix();
		
		if (Main.isOutdated) {
			Invoker.drawScaledString("YOU ARE NOT RUNNING THE LATEST VERSION OF MASTERS", new float[] {4.5F, 4.5F, 4.5F}, new int[] {0, 20}, 0XFFFFFF, true);
		}
		
		Minecraft.getMinecraft().fontRendererObj.func_175063_a("Inspired by §eStamina", this.scaledResolution.getScaledWidth() - (Minecraft.getMinecraft().fontRendererObj.getStringWidth("Inspired by §eStamina")), this.scaledResolution.getScaledHeight() - 10, 0xffffffff);
		
		if ((boolean) ValueContext.get("hud_display").getValue()) {
			Invoker.drawScaledString("M", new float[] { 2.5F, 2.5F, 2.5F },
					new int[] { 0, 0 }, 0xFCF400, true);
			Invoker.drawScaledString("asters",
					new float[] { 1.6F, 1.6F, 1.6F }, new int[] { 5, 0 },
					0xFCF400, true);
			
			Invoker.drawScaledString(String.format("%s, %s, %s",
					(int) Minecraft.getMinecraft().thePlayer.posX,
					(int) Minecraft.getMinecraft().thePlayer.posY,
					(int) Minecraft.getMinecraft().thePlayer.posZ),
					new float[] { 0.9F, 0.9F, 0.9F }, new int[] { 10, 8 },
					0xFCF400, true);
		}

		if (ModuleDependancy.isModulePresent("Radar")
				&& ModuleContext.getByName("Radar").getState()
				&& Minecraft.getMinecraft().currentScreen == null) {
			GL11.glPopMatrix();
			return;
		}

		if ((boolean) ValueContext.get("arraylist_display").getValue()) {

			this.sort();
			int y = 0;

			for (final Module module : toRender) {
				if (module.getState()) {
					final String name = module.getDisplayName().toString();
					Minecraft.getMinecraft().fontRendererObj.func_175063_a(
							name,
							this.scaledResolution.getScaledWidth()
									- (Minecraft.getMinecraft().fontRendererObj
											.getStringWidth(name)), y, 0xffffffff);
					y += 9;
				}

			}

		}
		
		GL11.glPopMatrix();

	}

}
