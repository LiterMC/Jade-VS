package com.github.litermc.jadevs.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import snownee.jade.Jade;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(Jade.class)
public class MixinJade {
	@Inject(
		method = "lambda$onInitialize$4",
		at = @At(value = "INVOKE", target = "net/minecraft/class_2338.method_10262(Lnet/minecraft/class_2382;)D"),
		locals = LocalCapture.CAPTURE_FAILHARD,
		cancellable = true
	)
	private static void handleRequest(net.minecraft.server.MinecraftServer server, ServerPlayer player, net.minecraft.server.network.ServerGamePacketListenerImpl handler, net.minecraft.network.FriendlyByteBuf buf, net.fabricmc.fabric.api.networking.v1.PacketSender responseSender, CallbackInfo ci, BlockPos pos) {
		Vec3 worldPos = VSGameUtilsKt.toWorldCoordinates(player.getLevel(), Vec3.atCenterOf(pos));
		if (worldPos.distanceToSqr(Vec3.atCenterOf(player.blockPosition())) > Jade.MAX_DISTANCE_SQR) {
			ci.cancel();
		}
	}

	@Redirect(
		method = "lambda$onInitialize$4",
		at = @At(value = "INVOKE", target = "net/minecraft/class_2338.method_10262(Lnet/minecraft/class_2382;)D")
	)
	private static double handleRequest$BlockPos$distSqr(BlockPos blockPos, Vec3i playerPos) {
		return 0;
	}
}
