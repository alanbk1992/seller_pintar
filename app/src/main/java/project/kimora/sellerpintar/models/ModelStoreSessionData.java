package project.kimora.sellerpintar.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.utils.Constants;

/**
 * Created by msinfo on 09/02/23.
 */

public class ModelStoreSessionData {
    Context context;

    public ModelStoreSessionData(Context context) {
        this.context = context;
    }

    public void storeUser(String data) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("user", data);
        editor.commit();
    }

    public ModelUser getUser() {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        Gson gson = new Gson();
        ModelUser modelUser = gson.fromJson(sharedPref.getString("user", ""), ModelUser.class);
        return modelUser;
    }

    public void clearUser() {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("user");
        editor.commit();
        Constants.headers.clear();
    }



    public void storeRememberData(String data) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("remember", data);
        editor.commit();
    }

    public ModelRemember getRememberData() {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        ModelRemember modelRemember = gson.fromJson(sharedPref.getString("remember", ""), ModelRemember.class);
        return modelRemember;
    }

    public void storeGuideData(String data) {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("guide", data);
        editor.commit();
    }

    public ModelGuide getGuideData() {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        ModelGuide modelGuide = gson.fromJson(sharedPref.getString("guide", ""), ModelGuide.class);
        if(modelGuide == null)
            modelGuide = new ModelGuide();
        return modelGuide;
    }
}
