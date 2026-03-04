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
import snownee.jade.api.ui.IElementHelper;

public final class ShipAngularVelocityComponentProvider extends ShipDetailsComponentProvider {
	public static final ShipAngularVelocityComponentProvider INSTANCE = new ShipAngularVelocityComponentProvider();

	private ShipAngularVelocityComponentProvider() {}

	@Override
	public ResourceLocation getUid() {
		return JadeVSPlugin.SHIP_ANGULAR_VELOCITY;
	}

	@Override
	public void appendServerDataOnShip(final CompoundTag data, final BlockAccessor accessor, final LoadedServerShip ship) {
		data.put("shipAngularVelocity", SerializeUtil.vector3dToList(ship.getAngularVelocity()));
	}

	@Override
	public void appendTooltip(final ITooltip tooltip, final BlockAccessor accessor, final IPluginConfig config) {
		final CompoundTag compound = accessor.getServerData();
		if (!compound.contains("shipAngularVelocity")) {
			return;
		}
		final Vector3dc angularVelocity = SerializeUtil.listToVector3d(compound.getList("shipAngularVelocity", Tag.TAG_DOUBLE));
		tooltip.add(Component.translatable("jade_vs.tooltip.angular_velocity").append(": "));
		tooltip.append(
			IThemeHelper.get()
				.info(
					UnitFormatter.formatAngularVelocity(
						angularVelocity,
						config.get(JadeVSPlugin.SHIP_ANGULAR_VELOCITY_DEGREES_MODE),
						config.getEnum(JadeVSPlugin.SHIP_ANGULAR_VELOCITY_DISPLAY_MODE)
					)
				)
		);
	}
}
