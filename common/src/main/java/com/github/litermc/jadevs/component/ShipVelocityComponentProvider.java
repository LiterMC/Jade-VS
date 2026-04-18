package com.github.litermc.jadevs.component;

import com.github.litermc.jadevs.JadeVSPlugin;
import com.github.litermc.jadevs.util.SerializeUtil;
import com.github.litermc.jadevs.util.UnitFormatter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import org.joml.Vector3dc;
import org.valkyrienskies.core.api.ships.LoadedServerShip;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;

public final class ShipVelocityComponentProvider extends ShipDetailsComponentProvider {
	public static final ShipVelocityComponentProvider INSTANCE = new ShipVelocityComponentProvider();

	private ShipVelocityComponentProvider() {}

	@Override
	public ResourceLocation getUid() {
		return JadeVSPlugin.SHIP_VELOCITY;
	}

	@Override
	public boolean enabledByDefault() {
		return false;
	}

	@Override
	public void appendServerDataOnShip(final CompoundTag data, final BlockAccessor accessor, final LoadedServerShip ship) {
		data.put("shipVelocity", SerializeUtil.vector3dToList(ship.getVelocity()));
	}

	@Override
	public void appendTooltip(final ITooltip tooltip, final BlockAccessor accessor, final IPluginConfig config) {
		final CompoundTag compound = accessor.getServerData();
		if (!compound.contains("shipVelocity")) {
			return;
		}
		final Vector3dc velocity = SerializeUtil.listToVector3d(compound.getList("shipVelocity", Tag.TAG_DOUBLE));
		tooltip.add(Component.translatable("jade_vs.tooltip.ship_velocity").append(": "));
		tooltip.append(
			IThemeHelper.get()
				.info(
					UnitFormatter.formatVelocity(
						velocity,
						config.getEnum(JadeVSPlugin.SHIP_VELOCITY_DISPLAY_MODE)
					)
				)
		);
	}
}
