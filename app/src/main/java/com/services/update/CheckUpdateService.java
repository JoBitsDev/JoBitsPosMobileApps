package com.services.update;

import android.net.Uri;
import android.content.*;
import android.content.pm.*;
import android.app.Activity;

import com.utils.EnvironmentVariables;
import com.services.web_connections.SimpleWebConnectionService;

import java.util.concurrent.ExecutionException;

/**
 * Capa: Services
 * Esta clase NO FUNCIONA ACTUALMENTE, se supone que en algun momento sera la que se use
 * para actualizar la aplicacion y no tener que reinstalarla.
 * Created by Jorge on 14/9/18.
 */

public class CheckUpdateService {

    /**
     * Path de la version.
     */
    private static String versionPath = "http://" + EnvironmentVariables.getIP() + ":" + EnvironmentVariables.getPORT() + "/RM/apk_upd/version.txt";

    /**
     * Path de la apk.
     */
    private static String apkPath = "http://" + EnvironmentVariables.getIP() + ":" + EnvironmentVariables.getPORT() + "/RM/apk_upd/RestManager.apk";

    /**
     * Codigo de la version del servidor.
     */
    private static int serverVersionCode = 0;

    /**
     * Si existe o no updates disponibles
     */
    private static boolean availableUpdate = false;

    /**
     * Contexto.
     */
    private static Context context = null;


    /**
     * Checkea si hay actualizacion disponible.
     *
     * @return true si hay actualizacion, false de lo contrario.
     * @throws Exception Si existe algun error chekeando el update.
     */
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

    /**
     * Actualiza la apk.
     *
     * @param activity A mostrar.
     */
    public static void update(Activity activity) {
        if (availableUpdate) {
            Intent updateIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(apkPath));
            activity.startActivity(updateIntent);
        }
    }

    /**
     * Obtiene el nombre de la version actual de la aplicacion.
     *
     * @return el nombre de la version actual.
     */
    public static String getVersionName() {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                return pi.versionName;
            } catch (PackageManager.NameNotFoundException ex) {
            }
        }
        return "n/f";
    }

    /**
     * Obtiene el codigo de la version actual de la aplicacion.
     *
     * @return el codigo de la version actual.
     */
    public static int getVersionCode() {
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                return pi.versionCode;
            } catch (PackageManager.NameNotFoundException ex) {
            }
        }
        return 0;
    }

    /**
     * Pone el contexto.
     *
     * @param c Contexto a poner
     */
    public static void setContext(Context c) {
        context = c;
    }

    /**
     * Si hay actualizacion o no.
     *
     * @return true si hay actualizacion disponible, false de lo contrario.
     */
    public static boolean isAvailableUpdate() {
        return availableUpdate;
    }


}
