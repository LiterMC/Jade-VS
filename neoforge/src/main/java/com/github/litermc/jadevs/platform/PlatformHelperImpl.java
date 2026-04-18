package com.github.litermc.jadevs.platform;

import com.google.auto.service.AutoService;
import net.neoforged.fml.ModList;

@AutoService(PlatformHelper.class)
public final class PlatformHelperImpl implements PlatformHelper {
	@Override
	public boolean isModLoaded(String modId) {
		return ModList.get().isLoaded(modId);
	}
}
