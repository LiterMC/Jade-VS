package com.github.litermc.jadevs.mixin;

import com.github.litermc.jadevs.PlaceholderBlockEntity;
import com.github.litermc.jadevs.util.ShipWorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;

import snownee.jade.api.BlockAccessor;
import snownee.jade.impl.BlockAccessorImpl;
import snownee.jade.network.RequestBlockPacket;
import snownee.jade.util.CommonProxy;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.function.Consumer;

@Mixin(BlockAccessorImpl.class)
public class MixinBlockAccessorImpl {
	@Inject(
		method = "lambda$handleRequest$0",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;distSqr(Lnet/minecraft/core/Vec3i;)D", remap = true),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true,
		remap = false
	)
	private static void handleRequest(
		final RequestBlockPacket message,
		final ServerPlayer player,
		final Consumer responseSender,
		final CallbackInfo ci,
		final BlockAccessor accessor,
		final BlockPos pos,
		final ServerLevel world,
		final double maxDistance
	) {
		final Vec3 worldPos = ShipWorldHelper.toWorldPos(world, Vec3.atCenterOf(pos));
		if (worldPos.distanceToSqr(Vec3.atCenterOf(player.blockPosition())) > maxDistance) {
			ci.cancel();
		}
	}

	@Redirect(
		method = "lambda$handleRequest$0",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;distSqr(Lnet/minecraft/core/Vec3i;)D", remap = true),
		remap = false
	)
	private static double handleRequest$BlockPos$distSqr(final BlockPos blockPos, final Vec3i playerPos) {
		return 0;
	}
}
