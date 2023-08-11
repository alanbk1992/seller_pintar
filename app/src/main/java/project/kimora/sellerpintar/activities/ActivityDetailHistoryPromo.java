package project.kimora.sellerpintar.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.adapters.AdapterOrderPromo;
import project.kimora.sellerpintar.extensions.BaseActivity;
import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.models.ModelStoreSessionData;
import project.kimora.sellerpintar.network.VolleyNetwork;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.Progress;
import project.kimora.sellerpintar.utils.ValueFormatter;
import project.kimora.sellerpintar.utils.WrapContentLinearLayoutManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ActivityDetailHistoryPromo extends BaseActivity implements VolleyNetwork.NetworkListener, View.OnClickListener{
    private TextView  tvUniqCode , tvSubTotal,  tvOrderNum, tvStatus, tvName, tvLocation, tvNotes, tvTransport , tvJasaPengiriman, tvTotalPayment, tvNoResi,tvCekResi;
    private TextView btn_rating, tvPaymentType;
    private View VPaymentType;
    private int uniqueCode;
    private LinearLayout LLPaymentType;
    private RelativeLayout RLUniqueCode;
    private ImageView ivProfile;
    private Button btnAccept, btnReject;
    private RatingBar ratingBar;
    private VolleyNetwork network;
    private Progress progress;
    private ModelUser modelUser;
    private CardView cvDetailPengiriman;
    private String promoOrderId, pharmacyId, aptUserId , orderNum;
    private int orderStatusId, kodeUnik;
    private ArrayList<JSONObject> orderList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdapterOrderPromo mAdapter;
    private final int PAYMENT_CONFIRM_RC = 134;
    private final int ORDER_CONFIRM_RC = 134;
    private final int ACTIVITY_PROMO_RESULT = 2001;
    private long totalPrice , BidPrice, Transport, UniqueCode;
    private LinearLayout layoutButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_history_promo);
        setBackToolbar();

        ModelStoreSessionData store = new ModelStoreSessionData(this);
        modelUser = store.getUser();
        network = new VolleyNetwork(this, this);

        try {
            promoOrderId = getIntent().getStringExtra("PromoOrderID");
            String firebaseData = getIntent().getStringExtra("firebase_data");
            if (firebaseData != null) {
                JSONObject data = new JSONObject(firebaseData);
                promoOrderId = data.getString("PromoOrderID");
            }
        } catch (JSONException e){e.printStackTrace();}

        handleIncomingLink();
        initView();
        refresh();



    }

    private void initView() {
        progress            = new Progress(this);
        //ivProfile           = findViewById(R.id.iv_profile);
      //  tvOrderNum          = findViewById(R.id.tv_order_num);
        tvName             = findViewById(R.id.tv_name);

        tvSubTotal             = findViewById(R.id.tv_subTotal);
      //  tvLocation          = findViewById(R.id.tv_location);
        //tvNotes             = findViewById(R.id.tv_notes);
        btnReject          = findViewById(R.id.btn_reject);
        btnAccept          = findViewById(R.id.btn_accept);
      //  ivProfile           = findViewById(R.id.iv_profile);
       // ratingBar           = findViewById(R.id.rating);
        tvStatus            = findViewById(R.id.tv_status);
        tvTransport         = findViewById(R.id.tv_transport);
        tvTotalPayment      = findViewById(R.id.tv_total_payment);
      //  btn_rating          = findViewById(R.id.btn_rating);
        tvPaymentType       = findViewById(R.id.tv_payment_type);
       // tvOrderNum          = findViewById(R.id.tv_order_num);
        tvCekResi         = findViewById(R.id.tv_CekResi);
        tvNoResi         = findViewById(R.id.tv_noresi);
        cvDetailPengiriman          = findViewById(R.id.cvDetailPengiriman);
        tvJasaPengiriman         = findViewById(R.id.tv_JasaPengiriman);
        tvStatus          = findViewById(R.id.tv_status);
        LLPaymentType = findViewById(R.id.LL_payment_type);
        tvUniqCode  =  findViewById(R.id.tv_unique_code) ;
        VPaymentType = findViewById(R.id.V_payment_type);
        RLUniqueCode =  findViewById(R.id.layout_unique_code);
        btnAccept =   findViewById(R.id.btn_accept);
        btnReject =   findViewById(R.id.btn_reject);
        progress.setTapListener(this);
        btnAccept.setOnClickListener(this);
        btnReject.setOnClickListener(this);
        tvCekResi.setOnClickListener(this);

        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new AdapterOrderPromo(this, orderList);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        layoutButton = findViewById(R.id.layout_button);


    }

    private void refresh() {
        orderList.clear();
        mAdapter.notifyDataSetChanged();
        progress.showProgressBar();
        loadDetailHistory();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.progress_layout:
                progress.showProgressBar();
                refresh();
                break;
            case R.id.btn_accept:
                if(orderStatusId == 2) {

                 //   Intent paymentConfirm = new Intent(this, BankTransferDisclaimer.class);
                  ///  paymentConfirm.putExtra("OrderID",promoOrderId);
                   // paymentConfirm.putExtra("UserID", user.UserID);
                   // paymentConfirm.putExtra("UniqueCode", String.valueOf(UniqueCode));
                   // paymentConfirm.putExtra("total_payment",tvTotalPayment.getText().toString());
                   // paymentConfirm.putExtra("payment_url", "v1/patient.php?request=confirm_transfer_promo");
                    //startActivityForResult(paymentConfirm, PAYMENT_CONFIRM_RC);
                }else if (orderStatusId == 1) {
                   // Intent intent = new Intent(this, ActivityPembayaran.class);
                   // intent.putExtra("UserID", user.UserID);
                   // intent.putExtra("AlamatPengiriman" , tvName.getText()) ;
                   // startActivityForResult(intent, PAYMENT_CONFIRM_RC);
                }else if(orderStatusId == 4){
                    new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogTheme))
                            .setMessage("Anda yakin ingin konfirmasi transaksi?")
                            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    konfirmasi();
                                    dialog.dismiss();

                                }
                            }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }else {
                    finish();
                }
                break;
            case R.id.btn_reject:
                if(orderStatusId == 2 || orderStatusId == 1) {

                    new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogTheme))
                            .setMessage("Anda yakin ingin membatalkan transaksi?")
                            .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    notifyCancelOrder();
                                    dialog.dismiss();

                                }
                            }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                }else {
                    finish();
                }
                break;
            case R.id.tv_CekResi:
                if (tvNoResi.getText() == "0" ) {
                    Toast.makeText(this, "Mohon maaf, No resi anda tidak falid", Toast.LENGTH_LONG).show();
                }else {


                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://cekresi.com/?v=w3&noresi=" + tvNoResi.getText())));
                }
                break;
        }
    }
    private void konfirmasi(){
        try {
            JSONObject param = new JSONObject();
            param.put("PromoOrderID", promoOrderId);
            dialog.showProgressDialog("Konfirmasi transaksi ...");
            network.jsonPOST(param, Constants.URL_BASE + "v1/patient.php?request=terima_barang", "konfirmasi");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void handleIncomingLink() {
        Intent appLinkIntent = getIntent();
        Uri appLinkData = appLinkIntent.getData();
        if(appLinkData != null) {
            String[] prefixs    = appLinkData.toString().split("/");
            byte[] data         = Base64.decode(prefixs[prefixs.length-1], Base64.DEFAULT);
            try {
                promoOrderId             = new String(data, "UTF-8");
            } catch (UnsupportedEncodingException e) {e.printStackTrace();}
        }
        if(modelUser == null)
            ModelUser.forceLogout(this);
    }

    private void loadDetailHistory() {
        try {
            JSONObject param = new JSONObject();
            param.put("UserID", modelUser.user_id);
            param.put("PromoOrderID", promoOrderId);
            network.jsonPOST(param, Constants.URL_BASE+ "v1/patient.php?request=history_promo_detail", "history");
        } catch (JSONException e){e.printStackTrace();}
    }

    @Override
    public void onNetworkResponse(JSONObject response, String key) throws JSONException {
        dialog.dismiss();
        findViewById(R.id.container).setVisibility(View.VISIBLE);
        tvTotalPayment.setVisibility(View.VISIBLE);
        layoutButton.setVisibility(View.VISIBLE);
        progress.dismiss(false);
        try {
            if(response.getInt("status") == 200) {
                if(key.equals("history")) {
                    parseOrderData(response);
                    recyclerView.setVisibility(View.VISIBLE);

                    JSONArray items = response.getJSONArray("data");
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject action = items.getJSONObject(i);
                        //action.put("PriceMedicine", "0");
                        orderList.add(action);
                    }
                    mAdapter.notifyDataSetChanged();


                } else if (key.equals("cancel")) {
                    finish();
                } else if (key.equals("konfirmasi")) {
                    finish();
                }
            }
        } catch (JSONException e){e.printStackTrace();}
    }

    @Override
    public void onNetworkError(JSONObject responseCache, String key) throws JSONException {
        dialog.dismiss();
        progress.dismiss(true);
    }


    private void parseOrderData(JSONObject orderData) {
        try {

            JSONObject pharmacyData = orderData.getJSONArray("data").getJSONObject(0);
            promoOrderId = (pharmacyData.getString("PromoOrderID"));
            orderNum = (pharmacyData.getString("PromoOrderNo"));
            kodeUnik= (pharmacyData.getInt("KodeUnik"));
            tvName.setText(pharmacyData.getString("AlamatPengiriman"));
            tvStatus.setText (pharmacyData.getString("StatusName"));
            tvPaymentType.setText(pharmacyData.getString("PaymentType"));
            tvSubTotal.setText("Rp. " + ValueFormatter.formatPrice(pharmacyData.getLong("Harga")));
            tvTransport.setText("Rp. " + ValueFormatter.formatPrice(pharmacyData.getLong("Transport")));
            Transport = pharmacyData.getLong("Transport") ;
            BidPrice = pharmacyData.getLong("Harga") ;
            UniqueCode = pharmacyData.getLong("KodeUnik") ;
            totalPrice = BidPrice + Transport + UniqueCode;
            tvNoResi.setText(pharmacyData.getString("NoResi"));
            tvJasaPengiriman.setText(pharmacyData.getString("JasaPengiriman"));
            tvTotalPayment.setText("Rp. " + ValueFormatter.formatPrice(totalPrice));
            tvUniqCode.setText(pharmacyData.getString("KodeUnik"));

//            tvOrderNum.setText("#" + pharmacyData.getString("PromoOrderNo"));

            orderStatusId = pharmacyData.getInt("PromoOrderStatusID");

            btnAccept.setVisibility(orderStatusId >= 8 ? View.GONE : View.VISIBLE);
            if (orderStatusId == 2) {
                btnReject.setVisibility(View.VISIBLE);
                btnAccept.setVisibility(View.VISIBLE);
                btnAccept.setText("Pembayaran");
                btnReject.setText("Batal");
            }else if (orderStatusId == 1) {
                btnAccept.setVisibility(View.VISIBLE);
                btnAccept.setText("Lanjutkan");
            }else if (orderStatusId == 4) {
                btnAccept.setVisibility(View.VISIBLE);
                btnReject.setVisibility(View.GONE);
                cvDetailPengiriman.setVisibility(View.VISIBLE);
                btnAccept.setText("Konfirmasi");
            } else if (orderStatusId == 5) {
                btnAccept.setVisibility(View.VISIBLE);
                btnReject.setVisibility(View.GONE);
                cvDetailPengiriman.setVisibility(View.VISIBLE);
                btnAccept.setText("Close");
            }else {
                btnReject.setVisibility(View.VISIBLE);
                btnAccept.setVisibility(View.GONE);
                btnReject.setText("Close");
                cvDetailPengiriman.setVisibility(View.GONE);
            }

            if (kodeUnik == 0 ) {
                RLUniqueCode.setVisibility(View.GONE);
            }else {
//                tvOrderNum.setVisibility(View.VISIBLE);
                RLUniqueCode.setVisibility(View.VISIBLE);

            }

            if(orderStatusId == 5 || orderStatusId == 3){
                tvStatus.setText(pharmacyData.getString("StatusName"));
                tvStatus.setTextColor(ContextCompat.getColor(this, R.color.colorGreen));
            } else if(orderStatusId == 6 || orderStatusId == 8){
                tvStatus.setText(pharmacyData.getString("StatusName"));
                tvStatus.setTextColor(ContextCompat.getColor(this, R.color.colorRed));
            } else{
                tvStatus.setText(pharmacyData.getString("StatusName"));
                tvStatus.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            }




        } catch (JSONException e){e.printStackTrace();}
    }
    private void notifyCancelOrder() {
        try {
            ModelStoreSessionData store = new ModelStoreSessionData(this);
            ModelUser modelUser = store.getUser();
            JSONObject param = new JSONObject();
            param.put("PromoOrderID", promoOrderId);
            dialog.showProgressDialog("Membatalkan transaksi ...");
            network.jsonPOST(param, Constants.URL_BASE + "v1/patient.php?request=cancel_order_promo", "cancel");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PAYMENT_CONFIRM_RC && resultCode == RESULT_OK && resultCode == ORDER_CONFIRM_RC) {
            findViewById(R.id.container).setVisibility(View.GONE);
            progress.showProgressBar();
            btnAccept.setVisibility(View.GONE);
            btnReject.setVisibility(View.GONE);
            refresh();
        }
        // if (requestCode == ACTIVITY_PHARMACY_RESULT) {
        if (resultCode == RESULT_OK) { // Activity.RESULT_OK

            finish();
            // }
        }

    }
}


