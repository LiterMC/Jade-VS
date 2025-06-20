package com.github.litermc.jadevs;

import com.github.litermc.jadevs.component.BlockMassComponentProvider;
import com.github.litermc.jadevs.component.ShipMassComponentProvider;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadeVSPlugin implements IWailaPlugin {
	public static final ResourceLocation BLOCK_MASS = new ResourceLocation(Constants.MOD_ID, "block_mass");
	public static final ResourceLocation SHIP_MASS = new ResourceLocation(Constants.MOD_ID, "ship_mass");

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(BlockMassComponentProvider.INSTANCE, BlockEntity.class);
		registration.registerBlockDataProvider(ShipMassComponentProvider.INSTANCE, BlockEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerBlockComponent(BlockMassComponentProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(ShipMassComponentProvider.INSTANCE, Block.class);
	}
}
