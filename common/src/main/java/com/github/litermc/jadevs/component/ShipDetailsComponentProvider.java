package com.github.litermc.jadevs.component;

import com.github.litermc.jadevs.api.IShipData;
import com.github.litermc.jadevs.util.ShipWorldHelper;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;

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
		final IShipData ship = ShipWorldHelper.getShipAtPos(level, accessor.getPosition());
		if (ship == null) {
			return;
		}
		this.appendServerDataOnShip(data, accessor, ship);
	}

	public abstract void appendServerDataOnShip(CompoundTag data, BlockAccessor accessor, IShipData ship);
}
