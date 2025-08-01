package com.github.litermc.jadevs.util;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class UnitFormatter {
	private static final Component GRAM = Component.translatable("jade_vs.unit.gram");
	private static final Component KG = Component.translatable("jade_vs.unit.kg");
	private static final Component TON = Component.translatable("jade_vs.unit.ton");

	private UnitFormatter() {}

	public static final MutableComponent formatWeight(final long grams) {
		long n = grams;
		Component unit = KG;
		if (n < 1000) {
			return Component.literal(String.valueOf(n)).append(GRAM);
		}
		if (n >= 10_000_000) {
			n /= 1000;
			unit = TON;
		}
		return Component.literal(String.format("%.2f", n / 1000.0)).append(unit);
	}
} 
