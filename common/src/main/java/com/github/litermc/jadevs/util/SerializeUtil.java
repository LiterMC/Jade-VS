package com.github.litermc.jadevs.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;

import org.joml.Vector3d;
import org.joml.Vector3dc;

public final class SerializeUtil {
	private SerializeUtil() {}

	/** Vector3d **/

	public static ListTag vector3dToList(final Vector3dc v) {
		final ListTag tag = new ListTag();
		tag.add(DoubleTag.valueOf(v.x()));
		tag.add(DoubleTag.valueOf(v.y()));
		tag.add(DoubleTag.valueOf(v.z()));
		return tag;
	}

	public static Vector3d listToVector3d(final ListTag tag) {
		return listToVector3d(tag, new Vector3d());
	}

	public static Vector3d listToVector3d(final ListTag tag, final Vector3d dest) {
		dest.x = tag.getDouble(0);
		dest.y = tag.getDouble(1);
		dest.z = tag.getDouble(2);
		return dest;
	}

	public static CompoundTag putVector3d(final CompoundTag tag, final Vector3dc v) {
		tag.putDouble("x", v.x());
		tag.putDouble("y", v.y());
		tag.putDouble("z", v.z());
		return tag;
	}

	public static Vector3d getVector3d(final CompoundTag tag) {
		return getVector3d(tag, new Vector3d());
	}

	public static Vector3d getVector3d(final CompoundTag tag, final Vector3d dest) {
		dest.x = tag.getDouble("x");
		dest.y = tag.getDouble("y");
		dest.z = tag.getDouble("z");
		return dest;
	}

	public static void writeVector3d(final FriendlyByteBuf buf, final Vector3dc v) {
		buf.writeDouble(v.x());
		buf.writeDouble(v.y());
		buf.writeDouble(v.z());
	}

	public static Vector3d readVector3d(final FriendlyByteBuf buf) {
		return readVector3d(buf, new Vector3d());
	}

	public static Vector3d readVector3d(final FriendlyByteBuf buf, final Vector3d dest) {
		dest.x = buf.readDouble();
		dest.y = buf.readDouble();
		dest.z = buf.readDouble();
		return dest;
	}
}
