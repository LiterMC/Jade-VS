package com.github.litermc.jadevs.platform;

import com.google.auto.service.AutoService;
import net.fabricmc.loader.api.FabricLoader;

@AutoService(PlatformHelper.class)
public final class PlatformHelperImpl implements PlatformHelper {
	@Override
	public boolean isModLoaded(String modid) {
		return FabricLoader.getInstance().isModLoaded(modid);
	}
}
