package com.owen.masters.event.events.block;

import net.minecraft.block.Block;
import net.minecraft.util.BlockPos;

import com.owen.masters.eventapi.callable.EventCancellable;

public class EventRenderBlock extends EventCancellable {

	private final BlockPos blockPos;
	private final Block block;

	public EventRenderBlock(final BlockPos bp, final Block block) {
		this.blockPos = bp;
		this.block = block;
	}

	public BlockPos getBlockPos() {
		return blockPos;
	}

	public Block getBlock() {
		return block;
	}
}
