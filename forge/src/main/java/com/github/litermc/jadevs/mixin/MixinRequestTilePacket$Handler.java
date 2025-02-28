package com.github.litermc.jadevs.mixin;

import mcp.mobius.waila.network.RequestTilePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.mod.common.VSGameUtilsKt;

import java.util.function.Supplier;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(RequestTilePacket.Handler.class)
public class MixinRequestTilePacket$Handler {
	@Inject(
		method = "lambda$onMessage$0",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;m_123331_(Lnet/minecraft/core/Vec3i;)D"),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private static void handleRequest(Supplier context, RequestTilePacket message, CallbackInfo ci, ServerPlayer player) {
		Vec3 worldPos = VSGameUtilsKt.toWorldCoordinates(player.getLevel(), Vec3.atCenterOf(message.pos));
		if (worldPos.distanceToSqr(Vec3.atCenterOf(player.blockPosition())) > RequestTilePacket.MAX_DISTANCE_SQR) {
			ci.cancel();
		}
	}

	@Redirect(
		method = "lambda$onMessage$0",
		at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;m_123331_(Lnet/minecraft/core/Vec3i;)D")
	)
	private static double handleRequest$BlockPos$distSqr(BlockPos blockPos, Vec3i playerPos) {
		return 0;
	}
}
