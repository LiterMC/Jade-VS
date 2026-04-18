package com.github.litermc.jadevs.forge.mixin;

import com.github.litermc.jadevs.api.IShipData;
import com.github.litermc.jadevs.util.ShipWorldHelper;

import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3d;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;

import snownee.jade.addon.create.CreatePlugin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;

@Pseudo
@Mixin(CreatePlugin.class)
public class MixinCreatePlugin {
	@WrapOperation(
		method = "lambda$registerClient$1",
		at = @At(
			value = "INVOKE",
			target = "Lcom/simibubi/create/content/contraptions/AbstractContraptionEntity;toLocalVector(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;"
		),
		require = 2,
		remap = false
	)
	private static Vec3 registerClient$toLocalVector(final AbstractContraptionEntity contraption, Vec3 pos, final float partialTicks, final Operation<Vec3> operation) {
		final Level level = contraption.level();
		final IShipData ship = ShipWorldHelper.getShipAtPos(level, contraption.blockPosition());
		if (ship != null) {
			final Vector3d p = ship.getShipToWorldTransform().transformPosition(new Vector3d(pos.x, pos.y, pos.z));
			pos = new Vec3(p.x, p.y, p.z);
		}
		return operation.call(contraption, pos, partialTicks);
	}
}
