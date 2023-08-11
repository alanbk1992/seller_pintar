package project.kimora.sellerpintar.activities;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.models.ModelStoreSessionData;
import project.kimora.sellerpintar.network.VolleyNetwork;
import project.kimora.sellerpintar.utils.ImageProcessor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import project.kimora.sellerpintar.extensions.BaseActivity;

/**
 * Created by msinfo 13/02/23.
 */
public class ActivityImageFull extends BaseActivity implements View.OnClickListener, VolleyNetwork.NetworkListener {
    private ImageView ivImage;
    boolean isImageFitToScreen;
    // private Progress progress;
    private ProgressDialog mProgressDialog;
    private VolleyNetwork network;
    private ModelUser modelUser;

    String URL ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full);
        setBackToolbar();

        network                 = new VolleyNetwork(this, this);
        ModelStoreSessionData store  = new ModelStoreSessionData(this);
        modelUser = store.getUser();

        initView();
        // loadPatient();

    }




   // @Override
   // public boolean onCreateOptionsMenu(Menu menu) {
      //  getMenuInflater().inflate(R.menu.menu_download, menu);
       // return true;
    //}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          //  case R.id.download:
                // Execute DownloadImage AsyncTask
             //   new DownloadImage().execute(URL);
              //  break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {

        ivImage         = findViewById(R.id.iv_image);


        if (getIntent().getStringExtra("URL") == null) {
            ImageProcessor.loadImage(this, (ivImage), getIntent().getStringExtra("urlFull"));
            URL = getIntent().getStringExtra("urlFull");

        }else{
            ImageProcessor.loadImage(this, (ivImage), getIntent().getStringExtra("URL")+ getIntent().getStringExtra("ID") + ".jpg");
            URL = getIntent().getStringExtra("URL")+ getIntent().getStringExtra("ID") + ".jpg";
        }

    }


    // DownloadImage AsyncTask
    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ActivityImageFull.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Download Image");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            ivImage.setImageBitmap(result);
            // Close progressdialog
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }









    @Override
    public void onNetworkResponse(JSONObject response, String key) throws JSONException {
        // progress.dismiss(false);
        try {
            if(response.getInt("status") == 200) {
                // findViewById(R.id.iv_image.setVisibility(View.VISIBLE);
                if(key.equals("patient")) {

                }
            }
        } catch (JSONException e){e.printStackTrace();}
    }

    @Override
    public void onNetworkError(JSONObject responseCache, String key) throws JSONException {
        // progress.dismiss(true);
    }





   // @Override
   // protected void onActivityResult(int requestCode, int resultCode, Intent data) {


    //}
}

