package com.owen.masters.utility;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockVine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;

import com.owen.masters.event.events.movement.EventPlayerMovement;
import com.owen.masters.friend.manager.FriendManager;

public class Invoker {

	private static final Minecraft mc = Minecraft.getMinecraft();

	public static void drawScaledString(final String tag, final float[] scale,
			final int[] pos, final int color, final boolean shadow) {
		GL11.glPushMatrix();
		GL11.glScalef(scale[0], scale[1], scale[2]);

		if (shadow) {
			Minecraft.getMinecraft().fontRendererObj.func_175063_a(tag,
					pos[0] * 2, pos[1] * 2, color);
		} else {
			Minecraft.getMinecraft().fontRendererObj.drawString(tag,
					pos[0] * 2, pos[1] * 2, color);
		}

		GL11.glPopMatrix();
	}

	public static void setMoveSpeed(EventPlayerMovement event, double speed) {
		double forward = Minecraft.getMinecraft().thePlayer.movementInput.moveForward;
		double strafe = Minecraft.getMinecraft().thePlayer.movementInput.moveStrafe;
		float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;

		if ((forward == 0.0D) && (strafe == 0.0D)) {
			event.setX(0.0D);
			event.setZ(0.0D);
		} else {
			if (forward != 0.0D) {
				if (strafe > 0.0D) {
					yaw += (forward > 0.0D ? -45 : 45);
				} else if (strafe < 0.0D) {
					yaw += (forward > 0.0D ? 45 : -45);
				}
				strafe = 0.0D;

				if (forward > 0.0D) {
					forward = 1.0D;
				} else if (forward < 0.0D) {
					forward = -1.0D;
				}
			}
			event.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0F))
					+ strafe * speed * Math.sin(Math.toRadians(yaw + 90.0F)));
			event.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0F))
					- strafe * speed * Math.cos(Math.toRadians(yaw + 90.0F)));
		}

	}

	public static int getBestTool(BlockPos pos) {
		Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos)
				.getBlock();
		int slot = 0;
		float dmg = 0.1F;
		for (int index = 36; index < 45; index++) {
			ItemStack itemStack = Minecraft.getMinecraft().thePlayer.inventoryContainer
					.getSlot(index).getStack();
			if (itemStack != null
					&& block != null
					&& itemStack.getItem().getStrVsBlock(itemStack, block) > dmg) {
				slot = index - 36;
				dmg = itemStack.getItem().getStrVsBlock(itemStack, block);
			}
		}
		if (dmg > 0.1F)
			return slot;
		return Minecraft.getMinecraft().thePlayer.inventory.currentItem;
	}

	public static void writeChatMessage(final String msg) {
		Minecraft.getMinecraft().thePlayer
				.addChatMessage(new ChatComponentText(String.format("%s %s",
						"§8[§eMasters§8]§e", msg)));
	}
	
	public static void renderNameTag(final String type, final double pX, final double pY, final double pZ) {
        final FontRenderer var12 = Minecraft.getMinecraft().fontRendererObj;
        float scale = (float)(Minecraft.getMinecraft().thePlayer.getDistance(pX + RenderManager.renderPosX, pY + RenderManager.renderPosY, pZ + RenderManager.renderPosZ) / 8.0);
        if (scale < 1.0f) {
            scale = 1.0f;
        }
        scale /= 50.0f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)pX, (float)pY + 1.4f, (float)pZ);
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
        drawBorderedRect(-width - 2, -(Minecraft.getMinecraft().fontRendererObj.FONT_HEIGHT + 1), width + 2, 2.0f, 1.0F, -16777216, 1593835520);
        GL11.glPushMatrix();
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glPopMatrix();
    }

    private static void drawRect(final float g, final float h, final float i, final float j, final int col1) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glBegin(7);
        GL11.glVertex2d(i, h);
        GL11.glVertex2d(g, h);
        GL11.glVertex2d(g, j);
        GL11.glVertex2d(i, j);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

    public static void drawBorderedRect(final float x, final float y, final float x2, final float y2, final float l1, final int col1, final int col2) {
        drawRect(x, y, x2, y2, col2);
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(3553);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glPushMatrix();
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glLineWidth(l1);
        GL11.glBegin(1);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x2, y);
        GL11.glVertex2d(x, y2);
        GL11.glVertex2d(x2, y2);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
    }

	public static final float[] getEntityColor(final EntityPlayer player) {
		boolean var1 = player.isSneaking();
		boolean var2 = FriendManager.isFriend(player.getName());
		if (var1) {
			return new float[] { 255.0F, 0.0F, 0.0F, 1.0F };
		} else if (var2) {
			return new float[] { 0.0F, 250.0F, 212.0F, 1.0F };
		} else {
			return new float[] {
					250.0F,
					Minecraft.getMinecraft().thePlayer
							.getDistanceToEntity(player) / 64.0F, 0.0F, 1.0F };
		}
	}

	public static boolean isOnLiquid() {
		final AxisAlignedBB par1AxisAlignedBB = Minecraft.getMinecraft().thePlayer.boundingBox
				.offset(0.0, -0.01, 0.0).contract(0.001, 0.001, 0.001);
		final int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		final int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
		final int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		final int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
		final int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		final int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
		final Vec3 var10 = new Vec3(0.0, 0.0, 0.0);
		for (int var11 = var4; var11 < var5; ++var11) {
			for (int var12 = var6; var12 < var7; ++var12) {
				for (int var13 = var8; var13 < var9; ++var13) {
					final Block var14 = Minecraft.getMinecraft().theWorld
							.getBlockState(new BlockPos(var11, var12, var13))
							.getBlock();
					if (!(var14 instanceof BlockAir)
							&& !(var14 instanceof BlockLiquid)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public static boolean isInLiquid() {
		final AxisAlignedBB par1AxisAlignedBB = Minecraft.getMinecraft().thePlayer.boundingBox
				.contract(0.001, 0.001, 0.001);
		final int var4 = MathHelper.floor_double(par1AxisAlignedBB.minX);
		final int var5 = MathHelper.floor_double(par1AxisAlignedBB.maxX + 1.0);
		final int var6 = MathHelper.floor_double(par1AxisAlignedBB.minY);
		final int var7 = MathHelper.floor_double(par1AxisAlignedBB.maxY + 1.0);
		final int var8 = MathHelper.floor_double(par1AxisAlignedBB.minZ);
		final int var9 = MathHelper.floor_double(par1AxisAlignedBB.maxZ + 1.0);
		final Vec3 var10 = new Vec3(0.0, 0.0, 0.0);
		for (int var11 = var4; var11 < var5; ++var11) {
			for (int var12 = var6; var12 < var7; ++var12) {
				for (int var13 = var8; var13 < var9; ++var13) {
					final Block var14 = Minecraft.getMinecraft().theWorld
							.getBlockState(new BlockPos(var11, var12, var13))
							.getBlock();
					if (var14 instanceof BlockLiquid) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isInsideBlock(Entity entity) {
		for (int x = MathHelper
				.floor_double(entity.getEntityBoundingBox().minX); x < MathHelper
				.floor_double(entity.getEntityBoundingBox().maxX) + 1; x++) {
			for (int y = MathHelper
					.floor_double(entity.getEntityBoundingBox().minY); y < MathHelper
					.floor_double(entity.getEntityBoundingBox().maxY) + 1; y++) {
				for (int z = MathHelper.floor_double(entity
						.getEntityBoundingBox().minZ); z < MathHelper
						.floor_double(entity.getEntityBoundingBox().maxZ) + 1; z++) {
					Block block = Minecraft.getMinecraft().theWorld
							.getBlockState(new BlockPos(x, y, z)).getBlock();
					if (block != null) {
						AxisAlignedBB boundingBox = block
								.getCollisionBoundingBox(Minecraft
										.getMinecraft().theWorld, new BlockPos(
										x, y, z),
										Minecraft.getMinecraft().theWorld
												.getBlockState(new BlockPos(x,
														y, z)));
						if (block instanceof BlockHopper) {
							boundingBox = new AxisAlignedBB(x, y, z, x + 1,
									y + 1, z + 1);
						}

						if (boundingBox != null
								&& entity.getEntityBoundingBox()
										.intersectsWith(boundingBox)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
