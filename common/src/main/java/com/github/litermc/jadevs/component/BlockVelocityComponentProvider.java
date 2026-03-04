package com.github.litermc.jadevs.component;

import com.github.litermc.jadevs.JadeVSPlugin;
import com.github.litermc.jadevs.util.SerializeUtil;
import com.github.litermc.jadevs.util.UnitFormatter;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import org.joml.Vector3d;
import org.joml.Vector3dc;
import org.valkyrienskies.core.api.ships.LoadedServerShip;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.api.ui.IElementHelper;

public final class BlockVelocityComponentProvider extends ShipDetailsComponentProvider {
	public static final BlockVelocityComponentProvider INSTANCE = new BlockVelocityComponentProvider();

	private BlockVelocityComponentProvider() {}

	@Override
	public ResourceLocation getUid() {
		return JadeVSPlugin.BLOCK_VELOCITY;
	}

	@Override
	public void appendServerDataOnShip(final CompoundTag data, final BlockAccessor accessor, final LoadedServerShip ship) {
		final BlockPos pos = accessor.getPosition();

		final Vector3dc velocity = ship.getVelocity();
		data.put("shipVelocity", SerializeUtil.vector3dToList(velocity));

		final Vector3dc blockRelativePos = ship.getTransform().getToWorld()
			.transformPosition(new Vector3d(pos.getX(), pos.getY(), pos.getZ()).add(0.5, 0.5, 0.5))
			.sub(ship.getTransform().getPositionInWorld());
		data.put("blockRelativePos", SerializeUtil.vector3dToList(blockRelativePos));
	}

	@Override
	public void appendTooltip(final ITooltip tooltip, final BlockAccessor accessor, final IPluginConfig config) {
		final CompoundTag compound = accessor.getServerData();
		if (
			!compound.contains("shipVelocity") ||
			!compound.contains("shipAngularVelocity") ||
			!compound.contains("blockRelativePos")
		) {
			return;
		}
		final Vector3dc velocity = SerializeUtil.listToVector3d(compound.getList("shipVelocity", Tag.TAG_DOUBLE));
		final Vector3dc angularVelocity = SerializeUtil.listToVector3d(compound.getList("shipAngularVelocity", Tag.TAG_DOUBLE));
		final Vector3dc blockRelativePos = SerializeUtil.listToVector3d(compound.getList("blockRelativePos", Tag.TAG_DOUBLE));

		final Vector3dc blockVelocity = angularVelocity.cross(blockRelativePos, new Vector3d()).add(velocity);
		tooltip.add(Component.translatable("jade_vs.tooltip.block_velocity").append(": "));
		tooltip.append(
			IThemeHelper.get()
				.info(UnitFormatter.formatVelocity(blockVelocity, config.getEnum(JadeVSPlugin.BLOCK_VELOCITY_DISPLAY_MODE)))
		);
	}
}
