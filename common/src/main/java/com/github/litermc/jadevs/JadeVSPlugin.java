package com.github.litermc.jadevs;

import com.github.litermc.jadevs.component.BlockMassComponentProvider;
import com.github.litermc.jadevs.component.BlockVelocityComponentProvider;
import com.github.litermc.jadevs.component.ShipAngularVelocityComponentProvider;
import com.github.litermc.jadevs.component.ShipMassComponentProvider;
import com.github.litermc.jadevs.component.ShipVelocityComponentProvider;
import com.github.litermc.jadevs.config.VelocityDisplayMode;

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
	public static final ResourceLocation BLOCK_VELOCITY = new ResourceLocation(Constants.MOD_ID, "block_velocity");
	public static final ResourceLocation BLOCK_VELOCITY_DISPLAY_MODE = new ResourceLocation(Constants.MOD_ID, "block_velocity.display_mode");
	public static final ResourceLocation SHIP_ANGULAR_VELOCITY = new ResourceLocation(Constants.MOD_ID, "ship_angular_velocity");
	public static final ResourceLocation SHIP_ANGULAR_VELOCITY_DEGREES_MODE = new ResourceLocation(Constants.MOD_ID, "ship_angular_velocity.degrees_mode");
	public static final ResourceLocation SHIP_ANGULAR_VELOCITY_DISPLAY_MODE = new ResourceLocation(Constants.MOD_ID, "ship_angular_velocity.display_mode");
	public static final ResourceLocation SHIP_MASS = new ResourceLocation(Constants.MOD_ID, "ship_mass");
	public static final ResourceLocation SHIP_VELOCITY = new ResourceLocation(Constants.MOD_ID, "ship_velocity");
	public static final ResourceLocation SHIP_VELOCITY_DISPLAY_MODE = new ResourceLocation(Constants.MOD_ID, "ship_velocity.display_mode");

	@Override
	public void register(IWailaCommonRegistration registration) {
		registration.registerBlockDataProvider(BlockMassComponentProvider.INSTANCE, BlockEntity.class);
		registration.registerBlockDataProvider(BlockVelocityComponentProvider.INSTANCE, BlockEntity.class);
		registration.registerBlockDataProvider(ShipAngularVelocityComponentProvider.INSTANCE, BlockEntity.class);
		registration.registerBlockDataProvider(ShipMassComponentProvider.INSTANCE, BlockEntity.class);
		registration.registerBlockDataProvider(ShipVelocityComponentProvider.INSTANCE, BlockEntity.class);
	}

	@Override
	public void registerClient(IWailaClientRegistration registration) {
		registration.registerBlockComponent(BlockMassComponentProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(BlockVelocityComponentProvider.INSTANCE, Block.class);
		registration.addConfig(BLOCK_VELOCITY_DISPLAY_MODE, VelocityDisplayMode.SPEED_ONLY);
		registration.registerBlockComponent(ShipAngularVelocityComponentProvider.INSTANCE, Block.class);
		registration.addConfig(SHIP_ANGULAR_VELOCITY_DEGREES_MODE, true);
		registration.addConfig(SHIP_ANGULAR_VELOCITY_DISPLAY_MODE, VelocityDisplayMode.SPEED_ONLY);
		registration.registerBlockComponent(ShipMassComponentProvider.INSTANCE, Block.class);
		registration.registerBlockComponent(ShipVelocityComponentProvider.INSTANCE, Block.class);
		registration.addConfig(SHIP_VELOCITY_DISPLAY_MODE, VelocityDisplayMode.SPEED_ONLY);
	}
}
