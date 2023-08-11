package project.kimora.sellerpintar.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.BuildConfig;
import project.kimora.sellerpintar.extensions.BaseActivity;
import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.models.ModelStoreSessionData;
import project.kimora.sellerpintar.network.VolleyNetwork;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.Dialog;
import project.kimora.sellerpintar.utils.GSON;

import org.json.JSONException;
import org.json.JSONObject;

import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;

/**
 * Created by msinfo on 09/02/23.
 */

public class ActivityLogin extends BaseActivity implements VolleyNetwork.NetworkListener,  View.OnClickListener {
    private GoogleSignInClient mGoogleSignInClient;
    private VolleyNetwork network;
    private GoogleSignInAccount account;
    private final int RC_GOOGLE_SIGN_IN = 1805;
    private final int AGREEMENT_GOOGLE = 1806;
    @NotEmpty(messageId = R.string.field_cannot_empty, order = 1)
    private EditText etPassword;
    private EditText etEmail;
    private TextView etRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        network = new VolleyNetwork(this, this);
        autoFinishActivity = false;
       // dialog      = new Dialog(getBaseContext());
        prepareGoogleSignIn();
        initView();


    }


    private void initView() {
        etEmail         = findViewById(R.id.et_email);
        etPassword      = findViewById(R.id.et_password);
        etRegister    = findViewById(R.id.tv_register);
        Button btn_login = findViewById(R.id.btn_login);
        Button btn_GoogleLogin = findViewById(R.id.btn_google_login);



        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isDataValid()) {
                    dialog.showProgressDialog(getString(R.string.validating_user_data));
                    try {
                        JSONObject param = new JSONObject();
                        Constants.headers.put("Content-Type", "application/json");
                        param.put("email", etEmail.getText().toString());
                        param.put("password", etPassword.getText().toString());
                       // param.put("FirebaseID", FirebaseInstanceId.getInstance().getToken());
                        param.put("device_brand", Build.BRAND);
                        param.put("device_model", Build.MODEL);
                        network.jsonPOST(param, Constants.URL_API + "users/login", "login");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(ActivityLogin.this, R.string.please_validate_data, Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.tv_forgot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  startActivity(new Intent(ActivityLogin.this, ForgotPassword.class));
            }
        });
        findViewById(R.id.btn_google_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           //     startActivity(new Intent(ActivityLogin.this, ActivityRegister.class));
            }
        });
    }

    @Override
    public void onNetworkResponse(JSONObject response, String key) throws JSONException {
        dialog.dismiss();
        if(key.equals("login") && response.getInt("status") == 200) {
            //RoleID =
            JSONObject userData = response.getJSONArray("data").getJSONObject(0);
            ModelStoreSessionData modelStoreSessionData = new ModelStoreSessionData(this);
           // userData.put("profile_complete", response.getString("profile_complete"));
            userData.put("version", BuildConfig.VERSION_CODE);
            modelStoreSessionData.storeUser(userData.toString());
            Constants.initHeader((ModelUser) GSON.modelFromJson(userData.toString(), ModelUser.class));


                Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
                intent.putExtra("forgot", response.getBoolean("is_forgot"));
                startActivity(intent);
                Constants.REQUEST_LOGOUT = false;
                setResult(RESULT_OK);
                finish();

        }else if(key.equals("google")) {
            ModelStoreSessionData modelStoreSessionData = new ModelStoreSessionData(this);
            JSONObject userData = response.getJSONArray("data").getJSONObject(0);
            userData.put("GooglePhoto", account.getPhotoUrl());
            userData.put("profile_complete", response.getString("profile_complete"));
            userData.put("version", BuildConfig.VERSION_CODE);
            modelStoreSessionData.storeUser(userData.toString());

            Constants.initHeader((ModelUser) GSON.modelFromJson(userData.toString(), ModelUser.class));

            Intent intent = new Intent(this, ActivityMain.class);
            intent.putExtra("forgot", response.getBoolean("is_forgot"));
            startActivity(intent);

            Constants.REQUEST_LOGOUT = false;
            setResult(RESULT_OK);
            finish();
        }
    }


    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_ok:

                break;
        }
    }

    //Buka google login popup
    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
        setResult(RESULT_OK);
    }

    //Persiapan google login
    private void prepareGoogleSignIn() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//        mGoogleSignInClient.silentSignIn();
    }
    @Override
    public void onNetworkError(JSONObject responseCache, String key) throws JSONException {
        dialog.dismiss();
    }
    //Keadaan saat acitivity resume
    @Override
    protected void onResume() {
        super.onResume();
        Constants.REQUEST_LOGOUT = false;
    }

    //handle data hasil respon login google
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
        Toast.makeText(this , "tes" , Toast.LENGTH_LONG).show();
            String[] name = account.getDisplayName().split(" ");

            try {
                JSONObject param = new JSONObject();
                param.put("FirstName", name.length > 2 ? name[0]+" "+name[1] : name[0]);
                param.put("LastName", name.length > 1 ? name[name.length-1] : "");
                param.put("Email", account.getEmail());
                param.put("GoogleUserID", account.getId());
                param.put("FirebaseID", FirebaseInstanceId.getInstance().getToken());
                param.put("FirebaseTime", FirebaseInstanceId.getInstance().getCreationTime());
                param.put("DeviceBrand", Build.BRAND);
                param.put("DeviceModel", Build.MODEL);
                param.put("DeviceSerial", Build.SERIAL);
                if (Build.VERSION.SDK_INT > 25) {
                    param.put("DeviceSerial", "");
                } else {

                    param.put("DeviceSerial", Build.SERIAL);
                }
                param.put("DeviceOS", Build.VERSION.RELEASE);

                dialog.showProgressDialog(getString(R.string.validating_user_data));
                network.jsonPOST(param, Constants.URL_BASE+ "v1/api.php?request=register_google", "google");
            } catch (JSONException e){e.printStackTrace();}
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d(getClass().getName(), "signInResult:failed code=" + e.getStatusCode());
        }
    }

    //Check data field valid atau tidak
    private boolean isDataValid() {
        final boolean isValid = FormValidator.validate(this, new SimpleErrorPopupCallback(this, true));
        return isValid;
    }

    //Keadaan saat activity stop, hentikan aktivitas validasi
    @Override
    public void onStop() {
        super.onStop();
        FormValidator.stopLiveValidation(this);
    }

    //Keadaan ketika activity di hancurkan, hentikan aktivitas popup dialog
    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
    }


    //Keadaan ketika activity dimulai, actifkan aktifitas validasi
    @Override
    public void onStart() {
        super.onStart();
        if(!FormValidator.isLiveValidationRunning(this))
            FormValidator.startLiveValidation(this, findViewById(R.id.container), new SimpleErrorPopupCallback(this, false));
    }

    //Keadaan result saat activity setelah ini, handle hasil login google
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      //  if(resultCode == RESULT_OK) {
            if (requestCode == Constants.REQUEST_FINISH_ACTIVITY && !Constants.REQUEST_LOGOUT) {
                finish();
            }
            // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
            else if (requestCode == RC_GOOGLE_SIGN_IN) {
                // The Task returned from this call is always completed, no need to attach
                // a listener.
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                handleSignInResult(task);

          //  }
        }
    }
}
