package com.github.litermc.jadevs.mixin;

import snownee.jade.api.BlockAccessor;
import snownee.jade.impl.BlockAccessorClientHandler;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockAccessorClientHandler.class)
public class MixinBlockAccessorClientHandler {
	@Inject(method = "shouldRequestData", at = @At("HEAD"), cancellable = true, remap = false)
	public void shouldRequestData(final BlockAccessor accessor, final CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}
}
