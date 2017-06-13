package com.sprd.launcher3.utils;

import android.app.AddonManager;
import android.content.Context;
import com.android.launcher3.R;

public class DynamicDeskClockUtil {
    static DynamicDeskClockUtil sInstance;

    public DynamicDeskClockUtil() {
        // TODO
    }

    public static DynamicDeskClockUtil getInstance() {
        if (sInstance != null) {
            return sInstance;
        }

        sInstance = (DynamicDeskClockUtil) AddonManager.getDefault().getAddon(
                R.string.feature_dynamic_deskclock, DynamicDeskClockUtil.class);
        return sInstance;
    }

    public static DynamicDeskClockUtil getInstance(Context context) {
        if (sInstance != null) {
            return sInstance;
        }
        sInstance = (DynamicDeskClockUtil) AddonManager.getDefault().getAddon(
                R.string.feature_dynamic_deskclock, DynamicDeskClockUtil.class);
        return sInstance;
    }

    public boolean isDynamicDeskClock() {
        return false;
    }
}
