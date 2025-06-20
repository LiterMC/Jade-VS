package com.github.litermc.jadevs.component;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;

public abstract class ShipDetailsComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
	protected ShipDetailsComponentProvider() {}

	@Override
	public void appendServerData(final CompoundTag data, final BlockAccessor accessor) {
		if (!(accessor.getLevel() instanceof ServerLevel level)) {
			return;
		}
		final LoadedServerShip ship = VSGameUtilsKt.getShipObjectManagingPos(level, accessor.getPosition());
		if (ship == null) {
			return;
		}
		this.appendServerDataOnShip(data, accessor, ship);
	}

	public abstract void appendServerDataOnShip(CompoundTag data, BlockAccessor accessor, LoadedServerShip ship);
}
