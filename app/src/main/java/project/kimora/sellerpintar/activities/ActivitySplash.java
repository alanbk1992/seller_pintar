package project.kimora.sellerpintar.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.models.ModelStoreSessionData;
import project.kimora.sellerpintar.utils.Constants;


public class ActivitySplash extends AppCompatActivity{
    protected int _splashTime = 1500;
    private ModelUser modelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Constants.initDisplaySize(this);


        ModelStoreSessionData store  = new ModelStoreSessionData(this);
        modelUser = store.getUser();

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                goToAuth();
                finish();
            }
        }, secondsDelayed * _splashTime);
    }

    //Buka halaman auth setelah delay halaman splash sekitar 1,5 detik
    private void goToAuth() {
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
       // GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        ModelStoreSessionData storeSessionData = new ModelStoreSessionData(this);
        ModelUser user = storeSessionData.getUser();

  if (modelUser != null && modelUser.user_id != null && modelUser.token != null) {

          //  if (user.TokoID > 0) {
               // if (Constants.headers.isEmpty()) {
                    Constants.initHeader(modelUser);
                    startActivity(new Intent(this, ActivityMain.class));
                //}
          //  } else if (user.TokoID == 0) {
              //  startActivity(new Intent(this, ActivityRegisterToko.class));
           // }

        } else {


        startActivity(new Intent(this, ActivityLogin.class));


       }
    }
}
