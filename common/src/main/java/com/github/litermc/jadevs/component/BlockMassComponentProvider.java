package com.github.litermc.jadevs.component;

import com.github.litermc.jadevs.JadeVSPlugin;
import com.github.litermc.jadevs.util.UnitFormatter;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import org.valkyrienskies.mod.common.BlockStateInfo;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.theme.IThemeHelper;
import snownee.jade.api.ui.IElementHelper;

import kotlin.Pair;

public final class BlockMassComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
	public static final BlockMassComponentProvider INSTANCE = new BlockMassComponentProvider();

	private BlockMassComponentProvider() {} 

	@Override
	public ResourceLocation getUid() {
		return JadeVSPlugin.BLOCK_MASS;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public void appendServerData(final CompoundTag data, final BlockAccessor accessor) {
		final Pair<Double, ?> pair = BlockStateInfo.INSTANCE.get(accessor.getBlockState());
		if (pair == null) {
			return;
		}
		final Double mass = pair.getFirst();
		if (mass == null) {
			return;
		}
		data.putDouble("mass", (long) (mass * 1000));
	}

	@Override
	public void appendTooltip(final ITooltip tooltip, final BlockAccessor accessor, final IPluginConfig config) {
		final CompoundTag compound = accessor.getServerData();
		if (!compound.contains("mass")) {
			return;
		}
		final long mass = compound.getLong("mass");
		tooltip.add(Component.translatable("jade_vs.tooltip.standard_mass").append(": "));
		tooltip.append(IThemeHelper.get().info(UnitFormatter.formatWeight(mass)));
	}
}
