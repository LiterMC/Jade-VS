package com.github.litermc.jadevs.mixin;

import com.github.litermc.jadevs.api.IShipData;
import com.github.litermc.jadevs.util.ShipWorldHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3d;

import snownee.jade.overlay.RayTracing;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(RayTracing.class)
public class MixinRayTracing {
	@Unique
	private static final EntityTypeTest<Entity, Entity> ANY_ENTITY = new EntityTypeTest<>() {
		@Override
		public Entity tryCast(final Entity entity) {
			return entity;
		}

		@Override
		public Class<? extends Entity> getBaseClass() {
			return Entity.class;
		}
	};

	@Inject(method = "getEntityHitResult", at = @At("HEAD"), cancellable = true, remap = false)
	private static void getEntityHitResult(
		final Level level,
		final Entity raycaster,
		Vec3 start,
		Vec3 end,
		final AABB checkBox_,
		final Predicate<Entity> filter,
		final CallbackInfoReturnable<EntityHitResult> cir
	) {
		start = ShipWorldHelper.toWorldPos(level, start);
		end = ShipWorldHelper.toWorldPos(level, end);
		final AABB checkBox = new AABB(start, end);
		double distance = Double.MAX_VALUE;
		Entity result = null;
		Vec3 hitPos = null;

		final Vector3d tmp = new Vector3d();
		Vec3 start1, end1;
		for (final Entity target : level.getEntities(raycaster, checkBox, filter)) {
			AABB targetBox = target.getBoundingBox();
			if (targetBox.getSize() < 0.3) {
				targetBox = targetBox.inflate(0.3);
			}
			final IShipData ship = ShipWorldHelper.getShipAtPos(level, target.blockPosition());
			if (ship != null) {
				final Matrix4dc worldToShip = ship.getShipToWorldTransform().invert(new Matrix4d());
				worldToShip.transformPosition(tmp.set(start.x, start.y, start.z));
				start1 = new Vec3(tmp.x, tmp.y, tmp.z);
				worldToShip.transformPosition(tmp.set(end.x, end.y, end.z));
				end1 = new Vec3(tmp.x, tmp.y, tmp.z);
			} else {
				start1 = start;
				end1 = end;
			}
			if (targetBox.contains(start1)) {
				cir.setReturnValue(new EntityHitResult(target, start));
				return;
			}
			final Vec3 pos = targetBox.clip(start1, end1).orElse(null);
			if (pos == null) {
				continue;
			}
			final double dist = start1.distanceToSqr(pos);
			if (dist > distance) {
				continue;
			}
			distance = dist;
			result = target;
			if (ship != null) {
				ship.getShipToWorldTransform().transformPosition(tmp.set(pos.x, pos.y, pos.z));
				hitPos = new Vec3(tmp.x, tmp.y, tmp.z);
			} else {
				hitPos = pos;
			}
		}
		if (result != null) {
			cir.setReturnValue(new EntityHitResult(result, hitPos));
		}
	}
}
