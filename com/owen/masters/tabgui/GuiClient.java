package com.owen.masters.tabgui;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import com.owen.masters.main.Main;
import com.owen.masters.module.Module;
import com.owen.masters.module.context.ModuleContext;
import com.owen.masters.module.enums.EnumCategory;
import com.owen.masters.utility.Render2DUtility;
import com.owen.masters.value.context.ValueContext;

public class GuiClient {
	protected int colorBorder = 0xCCCAC9;
	protected int colorBody = 553648127;
	protected int guiWidth = 65;
	protected int guiHeight = 0;
	protected int tabHeight = 12;
	protected int selectedTab = 0;
	protected int selectedItem = 0;
	private boolean mainMenu;
	public ArrayList<GuiTab> tabsList;
	private final Minecraft mc;

	public GuiClient() {
		this.mainMenu = true;
		this.mc = Minecraft.getMinecraft();
		this.tabsList = new ArrayList<GuiTab>();
		for (final EnumCategory type : EnumCategory.values()) {
			final GuiTab tab = new GuiTab(this, type.name());
			for (final Module module : ModuleContext.getModuleList()) {
				if (module.getEnumCategory() == type) {
					tab.items.add(module);
				}
			}

			if (!tab.items.isEmpty()) {
				this.tabsList.add(tab);
			}
		}
		this.guiHeight = this.tabsList.size() * this.tabHeight;
	}

	public void drawGui(final FontRenderer fr) {
		if (Main.reloading) {
			return;
		}
		
		byte y = 0;
		byte x = 0;
		if ((boolean) ValueContext.get("hud_display").getValue()) {
			y = 26;
		}
		
		Render2DUtility.drawBorderedRect(x, y, (float) (x + this.guiWidth - 5.2), y
				+ this.guiHeight, -1725816540, -16777216);
		int yOff = y + 2;
		for (int i = 0; i < this.tabsList.size(); ++i) {
			final GuiTab tab = this.tabsList.get(i);
			Render2DUtility.drawRect(x + .5f, yOff - 1.5f,
					x + this.guiWidth - 5.6f, yOff + 9.5f,
					(i == this.selectedTab) ? 1342177279 : 15);
			fr.func_175063_a(this.getTabString(tab), x + 2, yOff, 16777215);
			if (i == this.selectedTab && !this.mainMenu) {
				tab.drawTabMenu(x + this.guiWidth - 5, yOff - 2, fr);
			}
			yOff += this.tabHeight;
		}
		yOff += 5;
	}

	private final String getTabString(final GuiTab tab) {

		if (!this.mainMenu) {
			return this.selectedTab == tabsList.indexOf(tab) ? String.format(
					"§f%s", tab.tabName) : "§7" + tab.tabName;
		}

		return this.selectedTab == tabsList.indexOf(tab) ? String.format(
				"§f%s", tab.tabName) : "§7" + tab.tabName;
	}

	public void parseKeyUp() {
		if (this.mainMenu) {
			--this.selectedTab;
			if (this.selectedTab < 0) {
				this.selectedTab = this.tabsList.size() - 1;
			}
		} else {
			--this.selectedItem;
			if (this.selectedItem < 0) {
				this.selectedItem = this.tabsList.get(this.selectedTab).items
						.size() - 1;
			}
		}
	}

	public void parseKeyDown() {
		if (this.mainMenu) {
			++this.selectedTab;
			if (this.selectedTab > this.tabsList.size() - 1) {
				this.selectedTab = 0;
			}
		} else {
			++this.selectedItem;
			if (this.selectedItem > this.tabsList.get(this.selectedTab).items
					.size() - 1) {
				this.selectedItem = 0;
			}
		}
	}

	public void parseKeyLeft() {
		if (!this.mainMenu) {
			this.mainMenu = true;
		}
	}

	public void parseKeyRight() {
		if (this.mainMenu) {
			this.mainMenu = false;
			this.selectedItem = 0;
		}
	}

	public void parseKeyToggle() {
		if (!this.mainMenu) {
			final int sel = this.selectedItem;
			this.tabsList.get(this.selectedTab).items.get(sel).toggle();
		}
	}
}