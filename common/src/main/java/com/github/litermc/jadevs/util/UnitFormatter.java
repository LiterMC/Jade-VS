package com.github.litermc.jadevs.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class UnitFormatter {
	private static final Component GRAM = Component.translatable("jade_vs.unit.gram");
	private static final Component KILOGRAM = Component.translatable("jade_vs.unit.kilogram");
	private static final Component MEGAGRAM = Component.translatable("jade_vs.unit.megagram");
	private static final Component GIGAGRAM = Component.translatable("jade_vs.unit.gigagram");
	private static final Component TERAGRAM = Component.translatable("jade_vs.unit.teragram");
	private static final Component[] UNITS = new Component[]{MEGAGRAM, GIGAGRAM, TERAGRAM};

	private UnitFormatter() {}

	public static final MutableComponent formatWeight(final long grams) {
		long n = grams;
		Component unit = KILOGRAM;
		if (n < 1000) {
			return Component.literal(String.valueOf(n)).append(GRAM);
		}
		for (final Component u : UNITS) {
			if (n < 10_000_000) {
				break;
			}
			n /= 1000;
			unit = u;
		}
		return Component.literal(String.format("%.2f", n / 1000.0)).append(unit);
	}
} 
