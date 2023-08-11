package project.kimora.sellerpintar.utils;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.activities.ActivityLogin;
import project.kimora.sellerpintar.extensions.BaseActivity;
import project.kimora.sellerpintar.models.ModelStoreSessionData;

/**
 * Created by msinfo on 09/02/23.
 */
public class Checker {

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static void checkLoginStatus(final BaseActivity activity, String status, String message){
        if(status.equals("406")){
            activity.dialog.setDialogMessage(activity.getString(R.string.oops), message);
            activity.dialog.btnOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent out = new Intent(activity, ActivityLogin.class);
                    activity.startActivity(out);
                    ModelStoreSessionData store = new ModelStoreSessionData(activity);
                    Constants.REQUEST_LOGOUT = true;

                    if(!store.getUser().GoogleUserID.isEmpty()) {
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .build();
                        GoogleSignInClient mGoogleSignInClient  = GoogleSignIn.getClient(activity, gso);
                        mGoogleSignInClient.signOut();
                    }

                    store.clearUser();
                    activity.finish();
                    activity.dialog.dismiss();
                }
            });
        } else if(status.equals("407")){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=com.twinzahra.twinzahra"));
            activity.finish();
        } else {
            activity.dialog.setDialogMessage(activity.getString(R.string.oops), message);
        }
    }

    public static boolean isNetWorkAvailable(Context context){
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            boolean isAvailable = activeNetInfo != null;

            return isAvailable;
        }

        return false;
    }

    public static boolean isAirplaneModeOn(Context context){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.System.AIRPLANE_MODE_ON,
                    0) != 0;
        } else {
            return Settings.System.getInt(
                    context.getContentResolver(),
                    Settings.Global.AIRPLANE_MODE_ON,
                    0) != 0;
        }
    }

    public static boolean isGPSEnabled(Context context) {
        LocationManager locationManager = null;
        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean checkPermission(Activity activity){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                doManualPermission(activity);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE   },
                        Constants.MY_PERMISSION_REQUEST_CODE);
            }
        } else return true;
        return false;
    }

    public static boolean checkLocationPermission(Activity activity){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                doManualPermission(activity);
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        Constants.MY_PERMISSION_REQUEST_CODE);
            }
        } else return true;
        return false;
    }

    public static boolean checkCameraPermission(final BaseActivity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)) {
                //Need manual permmison
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
                alertDialog.setTitle("Oops");
                alertDialog.setMessage("Aplikasi ini membutuhkan ijin untuk dapat menggunakan camera di aplikasi ini");
                alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", activity.getPackageName(), null));
                        activity.startActivityForResult(intent, Constants.MY_PERMISSION_REQUEST_CODE);
                    }
                });
                alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finish();
                        dialog.cancel();
                    }
                });
                alertDialog.show();
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA},
                        Constants.MY_PERMISSION_REQUEST_CODE);
            }
        } else return true;
        return false;
    }

    private static void doManualPermission(final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setTitle("Permission Denied");
        alertDialog.setMessage("Need permission for access Location service, Read & Write storage!");
        alertDialog.setPositiveButton("Setting", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.fromParts("package", activity.getPackageName(), null));
                activity.startActivityForResult(intent, Constants.MY_PERMISSION_REQUEST_CODE);
            }
        });
        alertDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                activity.finish();
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

}
