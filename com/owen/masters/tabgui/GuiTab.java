package com.owen.masters.tabgui;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;

import com.owen.masters.module.Module;
import com.owen.masters.utility.Render2DUtility;

public class GuiTab {
	private GuiClient gui;
	public ArrayList<Module> items;
	public String tabName;
	public int selectedItem;
	public int menuHeight;
	public int menuWidth;

	public GuiTab(final GuiClient GUI, final String TabName) {
		this.selectedItem = 0;
		this.menuHeight = 0;
		this.menuWidth = 0;
		this.tabName = TabName;
		this.gui = GUI;
		this.items = new ArrayList<Module>();
	}

	public void countMenuSize(final FontRenderer font) {
		int maxWidth = 0;
		for (int i = 0; i < this.items.size(); ++i) {
			if (font.getStringWidth(this.items.get(i).getName()) > maxWidth) {
				maxWidth = font.getStringWidth(this.items.get(i).getName()) + 7;
			}
		}
		this.menuWidth = maxWidth;
		this.menuHeight = this.items.size() * this.gui.tabHeight - 1;
	}

	public void drawTabMenu(final int x, final int y, final FontRenderer font) {

		this.countMenuSize(font);
		Render2DUtility.drawBorderedRect(x, y, x + this.menuWidth + 2, y
				+ this.menuHeight, -1725816540, -16777216);
		String color;
		for (int i = 0; i < this.items.size(); ++i) {
			if (this.items.size() != i) {
				Render2DUtility.drawRect(x + 0.6f, y + (i + 1.0f)
						* this.gui.tabHeight - 11.5f,
						x + this.menuWidth + 1.5f, y + (i + 1)
								* this.gui.tabHeight - 1.0f,
						(i == this.gui.selectedItem) ? 1342177279 : 15);
			}
			if (this.items.get(i).getState()) {
				color = "§f";
			} else {
				color = "§7";
			}
			font.func_175063_a(
					String.format("%s%s", color, this.items.get(i).getName()),
					x + 2, y + this.gui.tabHeight * i + 2, 16711680);
		}
	}
}