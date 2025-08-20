package com.github.litermc.jadevs.mixin;

import com.github.litermc.jadevs.PlaceholderBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.phys.Vec3;

import org.valkyrienskies.mod.common.VSGameUtilsKt;

import snownee.jade.Jade;
import snownee.jade.api.BlockAccessor;
import snownee.jade.impl.BlockAccessorImpl;
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
		final BlockAccessor accessor,
		final ServerPlayer player,
		final Consumer responseSender,
		final CallbackInfo ci,
		final BlockPos pos,
		final ServerLevel world
	) {
		Vec3 worldPos = VSGameUtilsKt.toWorldCoordinates(player.serverLevel(), Vec3.atCenterOf(pos));
		if (worldPos.distanceToSqr(Vec3.atCenterOf(player.blockPosition())) > Jade.MAX_DISTANCE_SQR) {
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

	@Redirect(
		method = "lambda$handleRequest$0",
		at = @At(value = "INVOKE", target = "Lsnownee/jade/api/BlockAccessor;getBlockEntity()Lnet/minecraft/world/level/block/entity/BlockEntity;", remap = true),
		remap = false
	)
	private static BlockEntity handleRequest$BlockAccessor$getBlockEntity(final BlockAccessor accessor) {
		BlockEntity be = accessor.getBlockEntity();
		if (be != null) {
			return be;
		}
		return new PlaceholderBlockEntity(accessor.getBlockState());
	}

	@Redirect(
		method = "lambda$handleRequest$0",
		at = @At(value = "INVOKE", target = "Lsnownee/jade/util/CommonProxy;getId(Lnet/minecraft/world/level/block/entity/BlockEntityType;)Lnet/minecraft/resources/ResourceLocation;", remap = true),
		remap = false
	)
	private static ResourceLocation handleRequest$CommonProxy$getId(final BlockEntityType<?> beType) {
		if (beType != null) {
			return CommonProxy.getId(beType);
		}
		return new ResourceLocation("minecraft", "air");
	}
}
