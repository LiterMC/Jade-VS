package com.github.litermc.jadevs.util;

import com.github.litermc.jadevs.compat.CompatMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import dev.ryanhcode.sable.physics.config.block_properties.PhysicsBlockPropertyHelper;

// import org.valkyrienskies.mod.common.BlockStateInfo;

import kotlin.Pair;

public final class BlockMassHelper {
	private BlockMassHelper() {}

	public static double getBlockMass(final Level level, final BlockPos pos, final BlockState state) {
		if (CompatMod.VALKYRIENSKIES.isLoaded()) {
			// return getVsBlockMass(state);
		}
		if (CompatMod.SABLE.isLoaded()) {
			return getSableBlockMass(level, pos, state);
		}
		return 0;
	}

	// public static double getVsBlockMass(final BlockState state) {
	// 	final Pair<Double, ?> pair = BlockStateInfo.INSTANCE.get();
	// 	if (pair == null) {
	// 		return;
	// 	}
	// 	final Double mass = pair.getFirst();
	// 	if (mass == null) {
	// 		return -1;
	// 	}
	// 	return mass.doubleValue();
	// }

	public static double getSableBlockMass(final Level level, final BlockPos pos, final BlockState state) {
		return PhysicsBlockPropertyHelper.getMass(level, pos, state);
	}
}