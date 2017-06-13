package com.sprd.launcher3.utils;

import android.app.AddonManager;
import android.content.Context;
import com.android.launcher3.R;

public class DynamicCalendarUtil {
    static DynamicCalendarUtil sInstance;

    public DynamicCalendarUtil() {
        // TODO
    }

    public static DynamicCalendarUtil getInstance() {
        if (sInstance != null) {
            return sInstance;
        }

        sInstance = (DynamicCalendarUtil) AddonManager.getDefault().getAddon(
                R.string.feature_dynamic_calendar, DynamicCalendarUtil.class);
        return sInstance;
    }

    public static DynamicCalendarUtil getInstance(Context context) {
        if (sInstance != null) {
            return sInstance;
        }
        sInstance = (DynamicCalendarUtil) AddonManager.getDefault().getAddon(
                R.string.feature_dynamic_calendar, DynamicCalendarUtil.class);
        return sInstance;
    }

    public boolean isDynamicCalendar() {
        return false;
    }
}
