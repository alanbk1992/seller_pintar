package project.kimora.sellerpintar.activities;

import android.content.Intent;
import android.os.Bundle;

import project.kimora.sellerpintar.BuildConfig;
import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.extensions.BaseActivity;
import project.kimora.sellerpintar.network.VolleyNetwork;
import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.models.ModelStoreSessionData;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.GSON;
/**
 * Created by msinfo on 09/02/23.
 */


import org.json.JSONException;
import org.json.JSONObject;

public class ActivityCashier extends BaseActivity implements VolleyNetwork.NetworkListener {

    private VolleyNetwork network;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cashier);
        network = new VolleyNetwork(this, this);
        autoFinishActivity = false;
        initView();


    }

    @Override
    public void onNetworkResponse(JSONObject response, String key) throws JSONException {
        dialog.dismiss();
        if(key.equals("login") && response.getInt("status") == 200) {
            //RoleID =
            JSONObject userData = response.getJSONArray("data").getJSONObject(0);
            ModelStoreSessionData modelStoreSessionData = new ModelStoreSessionData(this);
            userData.put("profile_complete", response.getString("profile_complete"));
            userData.put("version", BuildConfig.VERSION_CODE);
            modelStoreSessionData.storeUser(userData.toString());
            Constants.initHeader((ModelUser) GSON.modelFromJson(userData.toString(), ModelUser.class));


         //   Intent intent = new Intent(ActivityLogin.this, ActivityMain.class);
           // intent.putExtra("forgot", response.getBoolean("is_forgot"));
            //startActivity(intent);
            //Constants.REQUEST_LOGOUT = false;
            //setResult(RESULT_OK);
            //finish();

        }
    }

    @Override
    public void onNetworkError(JSONObject responseCache, String key) throws JSONException {
        dialog.dismiss();
    }

    private void initView() {
       // etEmail         = findViewById(R.id.et_email);


       // Button btn_GoogleLogin = findViewById(R.id.btn_google_login);



    }


    //Keadaan saat acitivity resume
    @Override
    protected void onResume() {
        super.onResume();
        Constants.REQUEST_LOGOUT = false;
    }


    //Keadaan result saat activity setelah ini, handle hasil login google
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  if(resultCode == RESULT_OK) {
     //   if (requestCode == Constants.REQUEST_FINISH_ACTIVITY && !Constants.REQUEST_LOGOUT) {
         //   finish();
       // }
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
       // else if (requestCode == RC_GOOGLE_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
         //   Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
           // handleSignInResult(task);

            //  }
       // }
    }

}
