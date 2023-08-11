package project.kimora.sellerpintar.models;

import android.app.Activity;
import android.content.Intent;

import project.kimora.sellerpintar.activities.ActivityLogin;
import project.kimora.sellerpintar.utils.Constants;

/**
 * Created by msinfo on 09/02/23.
 */

public class ModelUser {

    public String user_id, role_id , first_name, last_name, username,  email , phone, birth_date, gender, address   ;
    public String BirthPlace, NIK, FirebaseID, token, Active, profile_complete;
    public String Height, Weight, ReferralID, Password , ReferralBy;
    public String farm_id;
    public String GooglePhoto, GoogleUserID;
    public String imageProfileSignature = "";
    public int version , shop_id;

    public ModelUser() {

    }

    public static void forceLogout(Activity activity) {
        ModelStoreSessionData store = new ModelStoreSessionData(activity);
        Intent out = new Intent(activity, ActivityLogin.class);
        activity.startActivity(out);
        store.clearUser();
        Constants.REQUEST_LOGOUT = true;
        activity.finish();
    }
}
