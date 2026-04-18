// SPDX-FileCopyrightText: 2022 The CC: Tweaked Developers
//
// SPDX-License-Identifier: MPL-2.0

package com.github.litermc.jadevs.platform;

public interface PlatformHelper {
	/**
	 * Get the current {@link PlatformHelper} instance.
	 *
	 * @return The current instance.
	 */
	public static PlatformHelper get() {
		var instance = Instance.INSTANCE;
		return instance == null ? Services.raise(PlatformHelper.class, Instance.ERROR) : instance;
	}

	boolean isModLoaded(String modId);

	final class Instance {
		static final PlatformHelper INSTANCE;
		static final Throwable ERROR;

		static {
			// We don't want class initialisation to fail here (as that results in confusing errors). Instead, capture
			// the error and rethrow it when accessing. This should be JITted away in the common case.
			var helper = Services.tryLoad(PlatformHelper.class);
			INSTANCE = helper.instance();
			ERROR = helper.error();
		}

		private Instance() {
		}
	}
}
