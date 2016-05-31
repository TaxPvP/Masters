package com.owen.masters.main;

import java.io.IOException;
import java.net.Proxy;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import com.mojang.authlib.Agent;
import com.mojang.authlib.UserAuthentication;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.owen.masters.common.Commons;
import com.owen.masters.common.InternalValues;
import com.owen.masters.context.ContextManager;
import com.owen.masters.file.files.KeybindsFile;
import com.owen.masters.logger.Log;
import com.owen.masters.plugin.PluginLoader;
import com.owen.masters.plugin.enums.PluginType;
import com.owen.masters.tabgui.GuiHandler;

public class Main {

	public Main() {
	}

	private static ContextManager contextManager;
	public static PluginLoader pluginLoader;
	public static GuiHandler guiHandler;
	
	public static boolean isOutdated = true;
	public static boolean reloading = false;

	public static final void run() {
		Log.log("Client start initiated");
		contextManager = new ContextManager();
		pluginLoader = new PluginLoader();
		pluginLoader.load(PluginType.Module);
		pluginLoader.load(PluginType.Command);
		guiHandler = new GuiHandler();
		/*
		 * This was causing conflict due to the file loading context initiating
		 * before the plugins being loaded thus throwing null pointers
		 * everywhere, I'll add some sort of context prioritising to fix this in
		 * the future
		 */
		try {
			new KeybindsFile().load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		InternalValues.init();
		
		Minecraft.getMinecraft().session = Main.authenticate(/* REDACTED */, /* REDACTED */);
		isOutdated = Commons.IS_OUTDATED();
		
		if (isOutdated) {
			Log.log("OUTDATED");
		}
	}
	
	public static final void reload() {
		pluginLoader = new PluginLoader();
		pluginLoader.load(PluginType.Module);
		pluginLoader.load(PluginType.Command);
		guiHandler = new GuiHandler();
		try {
			new KeybindsFile().load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static GuiHandler getTabGuiHandler() {
		return guiHandler;
	}

	public static Session authenticate(String username, String password) {
		YggdrasilAuthenticationService yService = new YggdrasilAuthenticationService(
				Proxy.NO_PROXY, UUID.randomUUID().toString());
		UserAuthentication userAuth = yService
				.createUserAuthentication(Agent.MINECRAFT);
		userAuth.setUsername(username);
		userAuth.setPassword(password);
		try {
			userAuth.logIn();
			return new Session(userAuth.getSelectedProfile().getName(),
					userAuth.getSelectedProfile().getId().toString(),
					userAuth.getAuthenticatedToken(),
					username.contains("@") ? "mojang" : "legacy");
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return null;
	}

}
