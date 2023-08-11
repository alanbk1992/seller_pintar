package project.kimora.sellerpintar.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;


import androidx.core.content.ContextCompat;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.models.ModelPromo;
import project.kimora.sellerpintar.network.VolleyNetwork;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.GSON;
import project.kimora.sellerpintar.utils.Progress;

import project.kimora.sellerpintar.extensions.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;
//import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
//import org.sufficientlysecure.htmltextview.HtmlTextView;


public class ActivityPromoDetail extends BaseActivity implements VolleyNetwork.NetworkListener, View.OnClickListener {
    private VolleyNetwork network;
    private Progress progress;
   // private HtmlTextView htvContent;
    private TextView tvTitle, tvOwner, tvKategori , tvHarga ;
    private String promoId, harga  ;
    private RelativeLayout rlZoom;
    private ImageView ivImage , ivImageZoom;
    String Img;
    private Button btnRedeem, btnClose;
    private final int ACTIVITY_RESULT = 2001;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo_detail);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // this.getActionBar().setDisplayHomeAsUpEnabled(true);
        setBackToolbar();
        network     = new VolleyNetwork(this, this);

        initView();
        loadPromo();
    }

    private void initView() {
        progress        = new Progress(this);
        ivImage         = findViewById(R.id.iv_image);
        //htvContent      = findViewById(R.id.htv_content);
        tvTitle         = findViewById(R.id.tv_title);
        tvHarga         = findViewById(R.id.tv_harga);
        tvOwner          = findViewById(R.id.tv_owner);
        tvKategori         = findViewById(R.id.tv_kategori);

        btnRedeem  = findViewById(R.id.btn_reedem);
        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, android.R.color.transparent));
        btnRedeem.setOnClickListener(this);
        progress.setTapListener(this);
        findViewById(R.id.iv_image).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.progress_layout:
                progress.showProgressBar();
                loadPromo();
                break;

            case R.id.btn_reedem:
                postOrder();
                break;
            case R.id.iv_image:
                Intent newIntent = new Intent(this, ActivityImageFull.class);
                newIntent.putExtra("ID", getIntent().getStringExtra("PromoID"));
                newIntent.putExtra("URL", Constants.URL_IMAGE_PROMO);
                startActivity(newIntent);
                break;
        }
    }
    //Load data artikle dari server
    private void loadPromo() {
        try {
            JSONObject param = new JSONObject();
            param.put("PromoID", getIntent().getStringExtra("PromoID"));
            network.jsonPOST(param, Constants.URL_BASE+ "v1/patient.php?request=promo_detail", "promo");
        } catch (JSONException e){e.printStackTrace();}
    }


    private void postOrder() {


      //  Intent intent = new Intent(this, ActivityCheckout.class);
        //intent.putExtra("ProductID", promoId);
      //  intent.putExtra("Harga", harga);
       // intent.putExtra("payment_url", "v1/patient.php?request=confirm_transfer_promo");
        //intent.putExtra("load_hostory_url", "v1/patient.php?request=history_promo_order_detail");
       // intent.putExtra("process_payment_url", "v1/patient.php?request=process_payment_promo");
        //intent.putExtra("pending_url", "v1/patient.php?request=promo_order");
       // startActivityForResult(intent, ACTIVITY_RESULT);

    }

    @Override
    public void onNetworkResponse(JSONObject response, String key) throws JSONException {
        dialog.dismiss();
        findViewById(R.id.container).setVisibility(View.VISIBLE);
        findViewById(R.id.layout_button).setVisibility(View.VISIBLE);
        progress.dismiss(false);
        try {
            if(response.getInt("status") == 200) {
                if(key.equals("promo")) {
                    ModelPromo promo = (ModelPromo) GSON.modelFromJson(response.getJSONArray("data").getJSONObject(0).toString(), ModelPromo.class);
                    setTitle(promo.Title);
                    tvTitle.setText(promo.Title);
                   //harga = promo.Harga;
                   // promoId = promo.PromoID;
                   //tvHarga.setText("Rp "+ ValueFormatter.formatPrice(Long.parseLong(promo.Harga)));
                    //tvOwner.setText(promo.Owner);
                    //tvKategori.setText(promo.OwnerKategori);
                    //htvContent.setHtml(promo.Content, new HtmlHttpImageGetter(htvContent));
                   // ImageProcessor.loadImage(this, (ivImage), Constants.URL_IMAGE_PROMO + promo.PromoID + ".jpg");

                    Img = promo.PromoID;

                }
            }
        } catch (JSONException e){e.printStackTrace();}
    }

    @Override
    public void onNetworkError(JSONObject responseCache, String key) throws JSONException {
        dialog.dismiss();
        progress.dismiss(true);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ACTIVITY_RESULT && resultCode == RESULT_OK) {

            finish();
        }






    }
}




