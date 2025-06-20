package com.github.litermc.jadevs.component;

import com.github.litermc.jadevs.JadeVSPlugin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import org.joml.Vector3dc;
import org.valkyrienskies.core.api.ships.LoadedServerShip;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.api.ui.IElementHelper;

public final class ShipMassComponentProvider extends ShipDetailsComponentProvider {
	public static final ShipMassComponentProvider INSTANCE = new ShipMassComponentProvider();

	private ShipMassComponentProvider() {}

	@Override
	public ResourceLocation getUid() {
		return JadeVSPlugin.SHIP_MASS;
	}

	@Override
	public void appendServerDataOnShip(final CompoundTag data, final BlockAccessor accessor, final LoadedServerShip ship) {
		final Vector3dc scaling = ship.getTransform().getShipToWorldScaling();
		data.putDouble("shipMass", ship.getInertiaData().getMass() * scaling.x() * scaling.y() * scaling.z());
	}

	@Override
	public void appendTooltip(final ITooltip tooltip, final BlockAccessor accessor, final IPluginConfig config) {
		final CompoundTag compound = accessor.getServerData();
		if (!compound.contains("shipMass")) {
			return;
		}
		final double mass = compound.getDouble("shipMass");
		tooltip.add(Component.translatable("jade_vs.tooltip.ship_mass").append(": "));
		tooltip.append(IThemeHelper.get().info(Component.literal(String.valueOf(mass)).append("kg")));
	}
}
