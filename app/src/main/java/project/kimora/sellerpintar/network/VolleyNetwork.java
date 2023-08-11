package project.kimora.sellerpintar.network;

import android.app.Activity;
import android.content.Context;
//import android.support.design.widget.Snackbar;
import com.google.android.material
        .snackbar
        .Snackbar;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.extensions.BaseActivity;
import project.kimora.sellerpintar.utils.Checker;
import project.kimora.sellerpintar.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by msinfo on 09/02/23.
 */

public class VolleyNetwork {
    private BaseActivity activity;
    private Context context;
    private NetworkListener listener;

    public VolleyNetwork(Context context, NetworkListener listener) {
        this.context = context;
        this.listener = listener;
        if(context instanceof Activity)
            this.activity = ((BaseActivity)context);
    }

    public void jsonGET(final String url, final String key) {
        Log.d("jsonGET", url);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        onNetworkResponse(response, key);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    JSONObject jsonCache = getChache(url);
                    onNetworkError(jsonCache, key);
                }catch (NullPointerException e){e.printStackTrace();}
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Constants.headers;
            }
        };
        jsonRequest.setRetryPolicy(new MyRetryPolicyWithoutRetry());
        AppController.getInstance().addToRequestQueue(jsonRequest);
    }

    public void jsonPOST(JSONObject params, final String url, final String key) {
        Log.d("JsonPost", url+ "\n" +params.toString());
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, params,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            onNetworkResponse(response, key);
                        } catch (Exception e){}
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                try {
                    JSONObject jsonCache = getChache(url);
                    try {
                        onNetworkError(jsonCache, key);
                    } catch (Exception e){e.printStackTrace();}
                }catch (NullPointerException e){e.printStackTrace();}
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("Headers", Constants.headers.toString());
                return Constants.headers;
            }
        };
        jsonRequest.setRetryPolicy(new MyRetryPolicyWithoutRetry());
        AppController.getInstance().addToRequestQueue(jsonRequest);
    }

    public void multipartRequest(final String[] dataKeys, final DataPart[] dataParts, final Map<String, String> params, String url, final String key) {
        Log.d("multipartRequest", url);
        MultipartRequest multipartRequest = new MultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    onNetworkResponse(new JSONObject(resultResponse), key);
                } catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onNetworkError(null, key);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Constants.headers;
            }

            @Override
            protected Map<String, String> getParams() {
                if(params == null) {
                    Map<String, String> paramsNull = new HashMap<>();
                    return paramsNull;
                }
                Log.d("Multipart Params", params.toString());
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                for(int i=0; i<dataKeys.length; i++) {
                    params.put(dataKeys[i], dataParts[i]);
                }
//                Log.d(dataKeys[0], new String(dataParts[0].getContent()));

                return params;
            }
        };
        MyRetryPolicyWithoutRetry policy = new MyRetryPolicyWithoutRetry();
        policy.CURRENT_TIME_OUT = 100000;
        multipartRequest.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(multipartRequest);
    }

    private void onNetworkResponse(JSONObject response, String key) {
        try {
            Log.d("onNetworkResponse", response.toString(4));
            if (response.getInt("status") != 200 && activity != null) {
                Checker.checkLoginStatus(activity, response.getString("status"), response.getString("message"));
            }
            listener.onNetworkResponse(response, key);
            activity.dialog.dismiss();
        } catch (Exception e){e.printStackTrace();}
    }

    private void onNetworkError(JSONObject jsonCache, String key) {
        if(jsonCache != null)
            Log.d("onNetworkError", jsonCache.toString());
        try {
            activity.dialog.dismiss();
            if (!Checker.isNetWorkAvailable(context) && activity != null) {
                Snackbar.make(activity.getWindow().getDecorView().getRootView(), "Tidak ada koneksi internet", Snackbar.LENGTH_LONG)
                        .setAction("Coba lagi", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                            }
                        }).show();
            } else {
                activity.dialog.setDialogMessage("Oops", context.getString(R.string.network_problem));
            }
            listener.onNetworkError(jsonCache, key);
        } catch (Exception e){e.printStackTrace();}
    }

    public JSONObject getChache(String url){
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url);
        JSONObject jsonObject = null;
        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                try {
                    jsonObject = new JSONObject(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return jsonObject;
    }

    public interface NetworkListener {
        void onNetworkResponse(JSONObject response, String key) throws JSONException;
        void onNetworkError(JSONObject responseCache, String key) throws JSONException;
    }

}
