package com.owen.masters.module.impl;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import org.lwjgl.opengl.GL11;

import com.owen.masters.event.events.game.EventGameRender3D;
import com.owen.masters.eventapi.Subscriber;
import com.owen.masters.module.Module;
import com.owen.masters.module.annotation.Loadable;
import com.owen.masters.module.enums.EnumCategory;
import com.owen.masters.utility.Render3DUtility;

@Loadable
public class StatusHUD extends Module {

	public StatusHUD() {
		super("StatusHUD", ' ', EnumCategory.Render);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registerValues() {
		// TODO Auto-generated method stub
		
	}
	
	public void renderItemStack(ItemStack stack, int x, int y)
    {
        GL11.glPushMatrix();
        GL11.glDepthMask(true);
        GlStateManager.clear(256);
        RenderHelper.enableStandardItemLighting();
        this.mc.getRenderItem().zLevel = -150.0F;
        fixItemGlint();
        this.mc.getRenderItem().func_180450_b(stack, x, y);
        this.mc.getRenderItem().zLevel = 0.0F;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.disableDepth();
        GlStateManager.enableDepth();
        GlStateManager.scale(2.0F, 2.0F, 2.0F);
        GL11.glPopMatrix();
    }

    public void fixItemGlint()
    {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.func_179090_x();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.func_179098_w();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }
	
	@Subscriber
	public void onGameRender3D(final EventGameRender3D event) {
		GL11.glPushMatrix();
		
		GL11.glPopMatrix();
	}

}
