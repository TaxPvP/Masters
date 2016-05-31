package com.owen.masters.utility;

import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.entity.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.network.play.client.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.attributes.*;
import com.google.common.collect.*;
import java.util.*;

public class EntityHelper {

	private static Minecraft mc;
	private static float smoothYaw;
	private static float smoothPitch;

	static {
		EntityHelper.mc = Minecraft.getMinecraft();
	}

	public static float[] getRotationFromPosition(final double x,
			final double z, final double y) {
		final double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
		final double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
		final double yDiff = y - Minecraft.getMinecraft().thePlayer.posY
				+ Minecraft.getMinecraft().thePlayer.getEyeHeight();
		final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff
				* zDiff);
		final float yaw = (float) (Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
		final float pitch = (float) (-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
		return new float[] { yaw, pitch };
	}

	public static double[] teleportToPosition(final double[] startPosition,
			final double[] endPosition, final double setOffset,
			final double slack, final boolean extendOffset,
			final boolean onGround) {
		boolean wasSneaking = false;
		if (EntityHelper.mc.thePlayer.isSneaking()) {
			wasSneaking = true;
		}
		double startX = startPosition[0];
		double startY = startPosition[1];
		double startZ = startPosition[2];
		final double endX = endPosition[0];
		final double endY = endPosition[1];
		final double endZ = endPosition[2];
		double distance = Math.abs(startX - startY) + Math.abs(startY - endY)
				+ Math.abs(startZ - endZ);
		int count = 0;
		while (distance > slack) {
			distance = Math.abs(startX - endX) + Math.abs(startY - endY)
					+ Math.abs(startZ - endZ);
			if (count > 120) {
				break;
			}
			final double offset = (extendOffset && (count & 0x1) == 0x0) ? (setOffset + 0.15)
					: setOffset;
			final double diffX = startX - endX;
			final double diffY = startY - endY;
			final double diffZ = startZ - endZ;
			if (diffX < 0.0) {
				if (Math.abs(diffX) > offset) {
					startX += offset;
				} else {
					startX += Math.abs(diffX);
				}
			}
			if (diffX > 0.0) {
				if (Math.abs(diffX) > offset) {
					startX -= offset;
				} else {
					startX -= Math.abs(diffX);
				}
			}
			if (diffY < 0.0) {
				if (Math.abs(diffY) > offset) {
					startY += offset;
				} else {
					startY += Math.abs(diffY);
				}
			}
			if (diffY > 0.0) {
				if (Math.abs(diffY) > offset) {
					startY -= offset;
				} else {
					startY -= Math.abs(diffY);
				}
			}
			if (diffZ < 0.0) {
				if (Math.abs(diffZ) > offset) {
					startZ += offset;
				} else {
					startZ += Math.abs(diffZ);
				}
			}
			if (diffZ > 0.0) {
				if (Math.abs(diffZ) > offset) {
					startZ -= offset;
				} else {
					startZ -= Math.abs(diffZ);
				}
			}
			if (wasSneaking) {
				EntityHelper.mc.getNetHandler().addToSendQueue(
						new C0BPacketEntityAction(EntityHelper.mc.thePlayer,
								C0BPacketEntityAction.Action.STOP_SNEAKING));
			}
			EntityHelper.mc
					.getNetHandler()
					.getNetworkManager()
					.sendPacket(
							new C03PacketPlayer.C04PacketPlayerPosition(startX,
									startY, startZ, onGround));
			++count;
		}
		if (wasSneaking) {
			EntityHelper.mc.getNetHandler().addToSendQueue(
					new C0BPacketEntityAction(EntityHelper.mc.thePlayer,
							C0BPacketEntityAction.Action.START_SNEAKING));
		}
		return new double[] { startX, startY, startZ };
	}

	public static void updateInventory() {
		for (int index = 0; index < 44; ++index) {
			try {
				final int offset = (index < 9) ? 36 : 0;
				Minecraft.getMinecraft().thePlayer.sendQueue
						.addToSendQueue(new C10PacketCreativeInventoryAction(
								index + offset,
								Minecraft.getMinecraft().thePlayer.inventory.mainInventory[index]));
			} catch (Exception ex) {
			}
		}
	}

	public static float[] getFacingRotations(final int x, final int y,
			final int z, final EnumFacing facing) {
		final Entity temp = new EntitySnowball(EntityHelper.mc.theWorld);
		temp.posX = x + 0.5;
		temp.posY = y + 0.5;
		temp.posZ = z + 0.5;
		final Entity entity = temp;
		entity.posX += facing.getDirectionVec().getX() * 0.25;
		final Entity entity2 = temp;
		entity2.posY += facing.getDirectionVec().getY() * 0.25;
		final Entity entity3 = temp;
		entity3.posZ += facing.getDirectionVec().getZ() * 0.25;
		return getAngles(temp);
	}

	public static float getYawChangeToEntity(final Entity entity) {
		final double deltaX = entity.posX - EntityHelper.mc.thePlayer.posX;
		final double deltaZ = entity.posZ - EntityHelper.mc.thePlayer.posZ;
		double yawToEntity;
		if (deltaZ < 0.0 && deltaX < 0.0) {
			yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
		} else if (deltaZ < 0.0 && deltaX > 0.0) {
			yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
		} else {
			yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
		}
		return MathHelper
				.wrapAngleTo180_float(-(EntityHelper.mc.thePlayer.rotationYaw - (float) yawToEntity));
	}

	public static float getPitchChangeToEntity(final Entity entity) {
		final double deltaX = entity.posX - EntityHelper.mc.thePlayer.posX;
		final double deltaZ = entity.posZ - EntityHelper.mc.thePlayer.posZ;
		final double deltaY = entity.posY - 1.6 + entity.getEyeHeight()
				- EntityHelper.mc.thePlayer.posY;
		final double distanceXZ = MathHelper.sqrt_double(deltaX * deltaX
				+ deltaZ * deltaZ);
		final double pitchToEntity = -Math.toDegrees(Math.atan(deltaY
				/ distanceXZ));
		return -MathHelper
				.wrapAngleTo180_float(EntityHelper.mc.thePlayer.rotationPitch
						- (float) pitchToEntity);
	}

	public static float[] getAngles(final Entity e) {
		return new float[] {
				getYawChangeToEntity(e) + EntityHelper.mc.thePlayer.rotationYaw,
				getPitchChangeToEntity(e)
						+ EntityHelper.mc.thePlayer.rotationPitch };
	}

	public static float[] getEntityRotations(final EntityPlayer player,
			final Entity target) {
		final double posX = target.posX - player.posX;
		final double posY = target.posY + target.getEyeHeight()
				- (player.posY + player.getEyeHeight() + 0.5);
		final double posZ = target.posZ - player.posZ;
		final double var14 = MathHelper.sqrt_double(posX * posX + posZ * posZ);
		final float yaw = (float) (Math.atan2(posZ, posX) * 180.0 / 3.141592653589793) - 90.0f;
		final float pitch = (float) (-(Math.atan2(posY, var14) * 180.0 / 3.141592653589793));
		return new float[] { yaw, pitch };
	}

	private float[] getBlockRotations(final int x, final int y, final int z) {
		final double var4 = x - EntityHelper.mc.thePlayer.posX + 0.5;
		final double var5 = z - EntityHelper.mc.thePlayer.posZ + 0.5;
		final double var6 = y
				- (EntityHelper.mc.thePlayer.posY
						+ EntityHelper.mc.thePlayer.getEyeHeight() - 1.0);
		final double var7 = MathHelper.sqrt_double(var4 * var4 + var5 * var5);
		final float var8 = (float) (Math.atan2(var5, var4) * 180.0 / 3.141592653589793) - 90.0f;
		return new float[] { var8,
				(float) (-(Math.atan2(var6, var7) * 180.0 / 3.141592653589793)) };
	}

}
