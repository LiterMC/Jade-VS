package com.github.litermc.jadevs.api;

import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

public interface IShipData {
	double getMass();

	Vector3dc getVelocity();

	Vector3dc getAngularVelocity();

	Matrix4dc getShipToWorldTransform();

	Vector3dc getPositionInWorld();
}
