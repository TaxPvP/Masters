package com.owen.masters.event.events.block;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

import com.owen.masters.eventapi.callable.EventCancellable;

public class EventBoundingBoxCollision extends EventCancellable {

	public AxisAlignedBB boundingBox;
	private Block block;
	private BlockPos blockPos;

	public EventBoundingBoxCollision(AxisAlignedBB boundingBox, Block block,
			BlockPos blockPos) {
		this.boundingBox = boundingBox;
		this.block = block;
		this.blockPos = blockPos;
	}

	public AxisAlignedBB getBoundingBox() {
		return boundingBox;
	}

	public void setBoundingBox(AxisAlignedBB boundingBox) {
		this.boundingBox = boundingBox;
	}

	public Block getBlock() {
		return block;
	}

	public BlockPos getBlockPos() {
		return blockPos;
	}

}
