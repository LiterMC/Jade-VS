package com.github.litermc.jadevs;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class PlaceholderBlockEntity extends BlockEntity {
	public PlaceholderBlockEntity(final BlockState state) {
		super(null, BlockPos.ZERO, state);
	}
}
