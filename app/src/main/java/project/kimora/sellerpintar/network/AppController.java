package project.kimora.sellerpintar.network;


import androidx.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.models.ModelRemember;
import project.kimora.sellerpintar.models.ModelStoreSessionData;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.VersionData;

public class AppController extends MultiDexApplication {

	public static final String TAG = AppController.class.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static AppController mInstance;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

		ModelStoreSessionData store = new ModelStoreSessionData(getApplicationContext());
		ModelUser modelUser = store.getUser();
		if(modelUser != null && modelUser.user_id != null && modelUser.token != null) {
			Constants.initHeader(modelUser);
			checkDataVersion(store);
		}
	}

	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	private void checkDataVersion(ModelStoreSessionData store) {
		ModelUser modelUser = store.getUser();
		if(modelUser.version != Constants.userVersion) {
			VersionData versionData = new VersionData(store);
			versionData.checkValidDataUser();
		}
		ModelRemember modelRemember = store.getRememberData();
		if(modelRemember != null) {

		}
	}
}