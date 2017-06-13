package com.android.launcher3;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import android.app.AddonManager;
import android.content.ComponentName;
import android.util.Log;

// SPRD: bug476770/375932 2014-12-02 Feature customize app icon sort.
public class SprdAppSortAddonStub {

    private static final String TAG = "SprdAppSortAddonStub";

    private static SprdAppSortAddonStub sInstance;
    protected boolean mHasCustomizeData = false;

    public static SprdAppSortAddonStub getInstance() {
        Log.d(TAG, "=====SprdAppSortAddonStub   getInstance");
        if (sInstance != null) {
            return sInstance;
        }
        sInstance = (SprdAppSortAddonStub) AddonManager.getDefault().getAddon(R.string.feature_app_sort,
                SprdAppSortAddonStub.class);
        return sInstance;
    }

    public final boolean hasCustomizeData() {
        return mHasCustomizeData;
    }

    protected void onSortApps(ArrayList<ComponentName> componentNames) {
        Log.d(TAG, "onSortApps");
    }

    public final void sortApps(List<AppInfo> apps) {
        Log.d(TAG, "sortApps :" + hasCustomizeData());
        if (!hasCustomizeData()) {
            return;
        }

        ArrayList<ComponentName> sortedCNs = new ArrayList<ComponentName>();
        HashMap<ComponentName, AppInfo> maps = new HashMap<ComponentName, AppInfo>();

        for (ItemInfo appInfo : apps) {
            sortedCNs.add(((AppInfo) appInfo).componentName);
            maps.put(((AppInfo) appInfo).componentName, (AppInfo) appInfo);
        }

        onSortApps(sortedCNs);

        apps.clear();
        for (ComponentName cn : sortedCNs) {
            apps.add(maps.get(cn));
        }
    }

}
