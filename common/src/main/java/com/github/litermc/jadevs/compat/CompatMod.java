package com.github.litermc.jadevs.compat;

import com.github.litermc.jadevs.platform.PlatformHelper;

public enum CompatMod {
	SABLE("sable"),
	VALKYRIENSKIES("valkyrienskies");

	private final String id;
	private boolean loaded = false;

	private CompatMod(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public boolean isLoaded() {
		if (this.loaded) {
			return true;
		}
		this.loaded = PlatformHelper.get().isModLoaded(this.id);
		return this.loaded;
	}
}
