package com.github.litermc.jadevs.util;

import com.github.litermc.jadevs.api.IShipData;
import com.github.litermc.jadevs.compat.CompatMod;
import com.github.litermc.jadevs.compat.SableShipData;
import com.github.litermc.jadevs.compat.VsShipData;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import org.valkyrienskies.core.api.ships.Ship;
// import org.valkyrienskies.mod.common.VSGameUtilsKt;

import dev.ryanhcode.sable.api.sublevel.SubLevelContainer;
import dev.ryanhcode.sable.sublevel.plot.LevelPlot;
import dev.ryanhcode.sable.sublevel.plot.ServerLevelPlot;

public final class ShipWorldHelper {
	private ShipWorldHelper() {}

	public static Vec3 toWorldPos(Level level, Vec3 pos) {
		if (CompatMod.VALKYRIENSKIES.isLoaded()) {
			// if (VSGameUtilsKt.isBlockInShipyard(level, pos)) {
			// 	return VSGameUtilsKt.toWorldCoordinates(level, pos);
			// }
		}
		if (CompatMod.SABLE.isLoaded()) {
			SubLevelContainer container = SubLevelContainer.getContainer(level);
			if (container != null) {
				LevelPlot plot = container.getPlot(SectionPos.blockToSectionCoord(pos.x), SectionPos.blockToSectionCoord(pos.z));
				if (plot != null) {
					return plot.getSubLevel().logicalPose().transformPosition(pos);
				}
			}
		}
		return pos;
	}

	public static IShipData getShipAtPos(Level level, BlockPos pos) {
		if (CompatMod.VALKYRIENSKIES.isLoaded()) {
			final Ship ship = null;
			// final LoadedServerShip ship = VSGameUtilsKt.getLoadedShipManagingPos(level, pos);
			if (ship != null) {
				return VsShipData.of(ship);
			}
		}
		if (CompatMod.SABLE.isLoaded()) {
			SubLevelContainer container = SubLevelContainer.getContainer(level);
			if (container != null) {
				LevelPlot plot = container.getPlot(new ChunkPos(pos));
				if (plot instanceof ServerLevelPlot serverPlot) {
					return SableShipData.of(serverPlot.getSubLevel());
				}
			}
		}
		return null;
	}
}
