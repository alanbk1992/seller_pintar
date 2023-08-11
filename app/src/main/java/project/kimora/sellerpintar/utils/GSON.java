package project.kimora.sellerpintar.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by msinfo on 09/02/23.
 */

public class GSON {

    public static void Log(String name, Object obj) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Log.d(name, gson.toJson(obj));
    }

    public static Object getObject(String data, Class<?> cls) {
        Gson gson = new Gson();
        Object object = gson.fromJson(data, cls);
        return object;
    }

    public static String toJsonString(Object src) {
        Gson gson = new Gson();
        return gson.toJson(src);
    }

    public static Object modelFromJson(String json, Class<?> cls) {
        Gson gson = new Gson();
        return gson.fromJson(json, cls);
    }

    public static Map<String, String> toMapString(String jsonString) {
        Map<String, String> retMap = new Gson().fromJson(
                jsonString, new TypeToken<HashMap<String, String>>() {}.getType()
        );
        return retMap;
    }
}
