package com.github.litermc.jadevs.compat;

import com.github.litermc.jadevs.api.IShipData;

import org.joml.Matrix4dc;
import org.joml.Vector3dc;

import org.valkyrienskies.core.api.ships.LoadedServerShip;
import org.valkyrienskies.core.api.ships.Ship;

public class VsShipData<T extends Ship> implements IShipData {
	protected final T ship;

	protected VsShipData(final T ship) {
		this.ship = ship;
	}

	public static IShipData of(final Ship ship) {
		if (ship instanceof LoadedServerShip serverShip) {
			return new VsShipData.Full(serverShip);
		}
		return new VsShipData<>(ship);
	}

	@Override
	public double getMass() {
		return -1;
	}

	@Override
	public Vector3dc getVelocity() {
		return this.ship.getVelocity();
	}

	@Override
	public Vector3dc getAngularVelocity() {
		return this.ship.getAngularVelocity();
	}

	@Override
	public Matrix4dc getShipToWorldTransform() {
		return this.ship.getTransform().getToWorld();
	}

	@Override
	public Vector3dc getPositionInWorld() {
		return this.ship.getTransform().getPositionInWorld();
	}

	public static final class Full extends VsShipData<LoadedServerShip> {
		public Full(final LoadedServerShip ship) {
			super(ship);
		}

		@Override
		public double getMass() {
			final Vector3dc scaling = this.ship.getTransform().getShipToWorldScaling();
			return this.ship.getInertiaData().getMass() * 1000 * scaling.x() * scaling.y() * scaling.z();
		}
	}
}
