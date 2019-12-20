package com.services.update;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import com.utils.EnvironmentVariables;
import com.services.web_connections.SimpleWebConnectionService;

import java.util.concurrent.ExecutionException;

/**
 * Created by Jorge on 14/9/18.
 */

public class CheckUpdateService {

    private static String versionPath = "http://"+ EnvironmentVariables.IP +":8080/RM/apk_upd/version.txt";
    private static String apkPath = "http://"+ EnvironmentVariables.IP +":8080/RM/apk_upd/RestManager.apk";
    private static int serverVersionCode = 0;
    private static boolean availableUpdate = false;
    private static Context context = null;



    public static boolean check() throws Exception {
        SimpleWebConnectionService s = new SimpleWebConnectionService();
        try {
            serverVersionCode = Integer.parseInt(s.connect(versionPath));
            availableUpdate = getVersionCode() < serverVersionCode;

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return availableUpdate;
    }

    public static void update(Activity activity){
        if(availableUpdate){
            Intent updateIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(apkPath));
             activity.startActivity(updateIntent);
        }

    }



    public static String getVersionName() {
        if(context != null){
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException ex) {}}
        return "n/f";
    }

    public static int getVersionCode() {
        if(context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                return pi.versionCode;
            } catch (PackageManager.NameNotFoundException ex) {
            }
        }return 0;
    }

    public static void setContext(Context c) {
        context = c;
    }

    public static boolean isAvailableUpdate() {
        return availableUpdate;
    }


}
