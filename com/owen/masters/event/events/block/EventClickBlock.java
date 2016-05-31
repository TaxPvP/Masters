package com.owen.masters.event.events.block;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;

import com.owen.masters.eventapi.callable.Event;

public class EventClickBlock implements Event {

	private final BlockPos pos;
	private final Block block;

	public EventClickBlock(final BlockPos blockPos) {
		this.pos = blockPos;
		this.block = Minecraft.getMinecraft().theWorld.getBlockState(blockPos)
				.getBlock();
	}

	public BlockPos getPos() {
		return pos;
	}

	public Block getBlock() {
		return block;
	}
}
