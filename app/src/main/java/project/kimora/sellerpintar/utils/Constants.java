package project.kimora.sellerpintar.utils;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.util.DisplayMetrics;

import com.google.android.gms.maps.model.LatLng;
import project.kimora.sellerpintar.BuildConfig;
import project.kimora.sellerpintar.fragments.FragmentHome;
import project.kimora.sellerpintar.models.ModelUser;

import java.util.HashMap;

/**
 * Created by msinfo on 09/02/23.
 */


public class Constants {
    public static int userVersion = 7;
    public static boolean isDev = true;
    public static String URL_BASE = isDev ? "http://anekasatwa.masuk.id/public/"  : "http://twinzahra.com/";
    public static String URL_API  = URL_BASE + "api/";

    public static String PUBLIC_IMAGE_HTML  = isDev ? "http://119.110.66.169/" : "http://vtal.id/";
    public static String URL_IMAGE_PATIENT  = PUBLIC_IMAGE_HTML + "image/patients/";
    public static String URL_IMAGE_NURSE    = PUBLIC_IMAGE_HTML + "image/nurses/";
    public static String URL_IMAGE_DOCTOR   = PUBLIC_IMAGE_HTML + "image/doctors/";
    public static String URL_IMAGE_PHARMACY = PUBLIC_IMAGE_HTML + "image/pharmacies/";
    public static String URL_IMAGE_PROMO = PUBLIC_IMAGE_HTML + "image/promo/";
    public static String URL_GOOGLE_PLAY    = "https://play.google.com/store/apps/details?id=project.msinfo.pasarburung";

    public static HashMap<String, String> headers = new HashMap<>();

    public static LatLng myLatlng;

    public static int displayWidth;
    public static int displayHeight;

    public final static int GPS_INTERVAL_UPDATE = 10000;

    public final static int MY_PERMISSION_REQUEST_CODE = 225;
    public final static int REQUEST_FINISH_ACTIVITY = 95;
    public static boolean REQUEST_LOGOUT = false;

    public static void initHeader(ModelUser modelUser) {
        headers.put("TOKEN", modelUser.token);
        headers.put("USER-ID", modelUser.user_id);
        headers.put("VERSION-NAME", ""+BuildConfig.VERSION_NAME);
        headers.put("VERSION-CODE", ""+BuildConfig.VERSION_CODE);
    }

    public static void initDisplaySize(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        Constants.displayWidth = displaymetrics.widthPixels;
        Constants.displayHeight = displaymetrics.heightPixels;
    }

    // schedule the start of the service every 10 - 30 seconds
    public static void scheduleJob(Context context) {
        ComponentName serviceComponent = new ComponentName(context, FragmentHome.class);
        JobInfo.Builder builder = new JobInfo.Builder(0, serviceComponent);
        builder.setMinimumLatency(1 * 1000); // wait at least
        builder.setOverrideDeadline(3 * 1000); // maximum delay
        //builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED); // require unmetered network
        //builder.setRequiresDeviceIdle(true); // device should be idle
        //builder.setRequiresCharging(false); // we don't care if the device is charging or not
        JobScheduler jobScheduler = context.getSystemService(JobScheduler.class);
        jobScheduler.schedule(builder.build());
    }
}
