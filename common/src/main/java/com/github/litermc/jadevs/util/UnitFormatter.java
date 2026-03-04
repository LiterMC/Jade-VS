package com.github.litermc.jadevs.util;

import com.github.litermc.jadevs.config.VelocityDisplayMode;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import org.joml.Vector3dc;

public final class UnitFormatter {
	private static final Component GRAM = Component.translatable("jade_vs.unit.gram");
	private static final Component KILOGRAM = Component.translatable("jade_vs.unit.kilogram");
	private static final Component MEGAGRAM = Component.translatable("jade_vs.unit.megagram");
	private static final Component GIGAGRAM = Component.translatable("jade_vs.unit.gigagram");
	private static final Component TERAGRAM = Component.translatable("jade_vs.unit.teragram");
	private static final Component[] GRAM_UNITS = new Component[]{MEGAGRAM, GIGAGRAM, TERAGRAM};

	private static final Component METER = Component.translatable("jade_vs.unit.meter");
	private static final Component KILOMETER = Component.translatable("jade_vs.unit.kilometer");
	private static final Component MEGAMETER = Component.translatable("jade_vs.unit.megameter");
	private static final Component[] METER_UNITS = new Component[]{KILOMETER, MEGAMETER};

	private static final Component DEGREE = Component.literal("°");

	private static final Component SECOND = Component.translatable("jade_vs.unit.second");

	private UnitFormatter() {}

	public static final MutableComponent formatWeight(final long grams) {
		long n = grams;
		Component unit = KILOGRAM;
		if (n < 1000) {
			return Component.literal(String.valueOf(n)).append(GRAM);
		}
		for (final Component u : GRAM_UNITS) {
			if (n < 10_000_000) {
				break;
			}
			n /= 1000;
			unit = u;
		}
		return Component.literal(String.format("%.2f", n / 1000.0)).append(unit);
	}

	public static final MutableComponent formatSpeed(final double speed) {
		double n = speed;
		Component unit = METER;
		for (final Component u : METER_UNITS) {
			if (n < 10_000) {
				break;
			}
			n /= 1000;
			unit = u;
		}
		return Component.literal(String.format("%.2f", n)).append(unit).append("/").append(SECOND);
	}

	private static final MutableComponent formatVelocityNoPer(final double velocity) {
		double n = velocity;
		Component unit = METER;
		for (final Component u : METER_UNITS) {
			if (-10_000 < n && n < 10_000) {
				break;
			}
			n /= 1000;
			unit = u;
		}
		return Component.literal(String.format("%+.2f", n)).append(unit);
	}

	public static final MutableComponent formatVelocity(final double velocity) {
		return formatVelocityNoPer(velocity).append("/").append(SECOND);
	}

	public static final MutableComponent formatVelocity(final Vector3dc velocity) {
		return Component.empty()
			.append(formatVelocity(velocity.x()))
			.append(" ")
			.append(formatVelocity(velocity.y()))
			.append(" ")
			.append(formatVelocity(velocity.z()));
	}

	public static final MutableComponent formatVelocity(final Vector3dc velocity, final VelocityDisplayMode mode) {
		return switch (mode) {
			case SPEED_ONLY -> formatSpeed(velocity.length());
			case SEPARATE -> formatVelocity(velocity);
			case COMBINED -> Component.literal("(")
				.append(formatVelocityNoPer(velocity.x()))
				.append(" ")
				.append(formatVelocityNoPer(velocity.y()))
				.append(" ")
				.append(formatVelocityNoPer(velocity.z()))
				.append(") ")
				.append(formatSpeed(velocity.length()));
		};
	}

	public static final MutableComponent formatAngularSpeed(final double speed, final boolean degreesMode) {
		final MutableComponent comp = Component.literal(
			degreesMode
				? String.format("%.2f", Math.toDegrees(speed))
				: String.format("%.4f", speed)
		);
		if (degreesMode) {
			comp.append(DEGREE);
		}
		return comp.append("/").append(SECOND);
	}

	private static final MutableComponent formatAngularVelocityNoUnit(final double velocity, final boolean degreesMode) {
		return Component.literal(
			degreesMode
				? String.format("%+.2f", Math.toDegrees(velocity))
				: String.format("%+.4f", velocity)
		);
	}

	public static final MutableComponent formatAngularVelocity(final double velocity, final boolean degreesMode) {
		final MutableComponent comp = formatAngularVelocityNoUnit(velocity, degreesMode);
		if (degreesMode) {
			comp.append(DEGREE);
		}
		return comp.append("/").append(SECOND);
	}

	public static final MutableComponent formatAngularVelocity(final Vector3dc velocity, final boolean degreesMode) {
		return Component.empty()
			.append(formatAngularVelocity(velocity.x(), degreesMode))
			.append(" ")
			.append(formatAngularVelocity(velocity.y(), degreesMode))
			.append(" ")
			.append(formatAngularVelocity(velocity.z(), degreesMode));
	}

	public static final MutableComponent formatAngularVelocity(
		final Vector3dc velocity,
		final boolean degreesMode,
		final VelocityDisplayMode mode
	) {
		return switch (mode) {
			case SPEED_ONLY -> formatAngularSpeed(velocity.length(), degreesMode);
			case SEPARATE -> formatAngularVelocity(velocity, degreesMode);
			case COMBINED -> Component.literal("(")
				.append(formatAngularVelocityNoUnit(velocity.x(), degreesMode))
				.append(" ")
				.append(formatAngularVelocityNoUnit(velocity.y(), degreesMode))
				.append(" ")
				.append(formatAngularVelocityNoUnit(velocity.z(), degreesMode))
				.append(") ")
				.append(formatAngularSpeed(velocity.length(), degreesMode));
		};
	}
}
