package com.github.litermc.jadevs.compat;

import com.github.litermc.jadevs.api.IShipData;
import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector3d;
import org.joml.Vector3dc;

import dev.ryanhcode.sable.sublevel.ServerSubLevel;
import dev.ryanhcode.sable.sublevel.SubLevel;

public class SableShipData<T extends SubLevel> implements IShipData {
	protected final T ship;

	protected SableShipData(final T ship) {
		this.ship = ship;
	}

	public static IShipData of(final SubLevel ship) {
		if (ship instanceof ServerSubLevel serverShip) {
			return new SableShipData.Full(serverShip);
		}
		return new SableShipData<>(ship);
	}

	@Override
	public double getMass() {
		return -1;
	}

	@Override
	public Vector3dc getVelocity() {
		return new Vector3d();
	}

	@Override
	public Vector3dc getAngularVelocity() {
		return new Vector3d();
	}

	@Override
	public Matrix4dc getShipToWorldTransform() {
		return this.ship.logicalPose().bakeIntoMatrix(new Matrix4d());
	}

	@Override
	public Vector3dc getPositionInWorld() {
		return this.ship.logicalPose().position();
	}

	public static final class Full extends SableShipData<ServerSubLevel> {
		public Full(final ServerSubLevel ship) {
			super(ship);
		}

		@Override
		public double getMass() {
			return this.ship.getMassTracker().getMass();
		}

		@Override
		public Vector3dc getVelocity() {
			return this.ship.latestLinearVelocity;
		}

		@Override
		public Vector3dc getAngularVelocity() {
			return this.ship.latestAngularVelocity;
		}
	}
}
