package com.owen.masters.module.impl;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;

import com.owen.masters.event.events.game.EventGameRender3D;
import com.owen.masters.eventapi.Subscriber;
import com.owen.masters.friend.manager.FriendManager;
import com.owen.masters.module.Module;
import com.owen.masters.module.annotation.Loadable;
import com.owen.masters.module.enums.EnumCategory;
import com.owen.masters.utility.Invoker;

@Loadable
public class Nametags extends Module {

	public Nametags() {
		super("Nametags", ' ', EnumCategory.Render);
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
	
	private void renderNameTag(final String type, final double pX, final double pY, final double pZ) {
        final FontRenderer var12 = Minecraft.getMinecraft().fontRendererObj;
        float scale = (float)(Minecraft.getMinecraft().thePlayer.getDistance(pX + RenderManager.renderPosX, pY + RenderManager.renderPosY, pZ + RenderManager.renderPosZ) / 8.0);
        if (scale < 1.0f) {
            scale = 1.0f;
        }
        scale /= 43.0f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)pX, (float)pY + 2.2f, (float)pZ);
        GL11.glNormal3f(0.0f, 1.0f, 0.0f);
        GL11.glRotatef(-RenderManager.playerViewY, 0.0f, 1.0f, 0.0f);
        GL11.glRotatef(RenderManager.playerViewX, 1.0f, 0.0f, 0.0f);
        GL11.glScalef(-scale, -scale, scale);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        final int width = Minecraft.getMinecraft().fontRendererObj.getStringWidth(type) / 2;
        GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        var12.func_175063_a(type, -width, -(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT - 1), -65536);
        Invoker.drawBorderedRect(-width - 2, -(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1), width + 2, 2.0f, 1.0F, -16777216, 1593835520);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }
	
	private final String getHealthString(final EntityPlayer player) {
		return String.format("§4%s", (int) player.getHealth() * 5);
	}
	
	private final String getNametagString(final EntityPlayer player) {
		final String name = player.getName();
		if (FriendManager.isFriend(name)) {
			return String.format("§b%s %s§4%s", FriendManager.getByName(name).getAlias(), this.getHealthString(player), "%");
		}
		else if (player.isSneaking()) {
			return String.format("§c%s %s§4%s", name, this.getHealthString(player), "%");
		}
		else if (mc.thePlayer.canEntityBeSeen(player)) {
			return String.format("§6%s %s§4%s", name, this.getHealthString(player), "%");
		}
		else
		{
			return String.format("§7%s %s§4%s", name, this.getHealthString(player), "%");
		}
	}
	
	@Subscriber
	public void onGameRender3D(final EventGameRender3D event) {
		for (final Object o : mc.theWorld.playerEntities) {
			final EntityPlayer player = (EntityPlayer) o;
			
			if (player != mc.thePlayer && player.isEntityAlive()) {
				
				double pX = player.lastTickPosX + (player.posX - player.lastTickPosX) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosX;
                double pY = player.lastTickPosY + (player.posY - player.lastTickPosY) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosY;
                double pZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * this.mc.timer.renderPartialTicks - this.mc.getRenderManager().renderPosZ;
				
                GL11.glPushMatrix();
                renderNameTag(this.getNametagString(player), pX, pY, pZ);
                GL11.glPopMatrix();
			}
		}
	}

}
