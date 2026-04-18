package com.github.litermc.jadevs.component;

import com.github.litermc.jadevs.JadeVSPlugin;
import com.github.litermc.jadevs.api.IShipData;
import com.github.litermc.jadevs.util.UnitFormatter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public final class ShipMassComponentProvider extends ShipDetailsComponentProvider {
	public static final ShipMassComponentProvider INSTANCE = new ShipMassComponentProvider();

	private ShipMassComponentProvider() {}

	@Override
	public ResourceLocation getUid() {
		return JadeVSPlugin.SHIP_MASS;
	}

	@Override
	public void appendServerDataOnShip(final CompoundTag data, final BlockAccessor accessor, final IShipData ship) {
		data.putLong("shipMass", (long) (ship.getMass() * 1000));
	}

	@Override
	public void appendTooltip(final ITooltip tooltip, final BlockAccessor accessor, final IPluginConfig config) {
		final CompoundTag compound = accessor.getServerData();
		if (!compound.contains("shipMass")) {
			return;
		}
		final long mass = compound.getLong("shipMass");
		tooltip.add(Component.translatable("jade_vs.tooltip.ship_mass").append(": "));
		tooltip.append(IThemeHelper.get().info(UnitFormatter.formatWeight(mass)));
	}
}
