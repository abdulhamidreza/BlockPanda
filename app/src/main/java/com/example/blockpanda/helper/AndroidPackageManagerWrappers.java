package com.example.blockpanda.helper;


import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndroidPackageManagerWrappers {

    public static boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    public static boolean isSystemApp(Context context, String packageName) {

        PackageInfo targetPkgInfo;
        try {
            PackageManager mPackageManager = (PackageManager) context.getPackageManager();
            targetPkgInfo = mPackageManager.getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return (targetPkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }


    public static Map<String, Drawable> getAppIcons(Context context) {

        Map<String, Drawable> appIconsMap = new HashMap<String, Drawable>();

        //package manager (contains app icons)
        List<PackageInfo> pack = context.getPackageManager().getInstalledPackages(0);

        for (int i = 0; i < pack.size(); i++) {
            PackageInfo currentPackage = pack.get(i);

            //get app icon only if given package is not a system package
            if ((!AndroidPackageManagerWrappers.isSystemPackage(currentPackage))) {
                String appName = currentPackage.packageName;
                Drawable icon = currentPackage.applicationInfo.loadIcon(context.getPackageManager());
                appIconsMap.put(appName, icon);
            }
        }

        return appIconsMap;
    }

}
