package com.github.litermc.jadevs.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import snownee.jade.Jade;
import snownee.jade.api.BlockAccessor;
import snownee.jade.impl.BlockAccessorImpl;

import java.util.function.Consumer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(BlockAccessorImpl.class)
public class MixinBlockAccessorImpl {
	@Inject(
		method = "lambda$handleRequest$0",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;distSqr(Lnet/minecraft/core/Vec3i;)D"),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private static void handleRequest(BlockAccessor accessor, ServerPlayer player, Consumer responseSender, CallbackInfo ci, BlockPos pos, ServerLevel world) {
		Vec3 worldPos = VSGameUtilsKt.toWorldCoordinates(player.serverLevel(), Vec3.atCenterOf(pos));
		if (worldPos.distanceToSqr(Vec3.atCenterOf(player.blockPosition())) > Jade.MAX_DISTANCE_SQR) {
			ci.cancel();
		}
	}

	@Redirect(
		method = "lambda$handleRequest$0",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;distSqr(Lnet/minecraft/core/Vec3i;)D")
	)
	private static double handleRequest$BlockPos$distSqr(BlockPos blockPos, Vec3i playerPos) {
		return 0;
	}
}
