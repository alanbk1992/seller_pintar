package project.kimora.sellerpintar.birds.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.extensions.BaseActivity;
import project.kimora.sellerpintar.extensions.WrapContentLinearLayoutManager;
import project.kimora.sellerpintar.models.ModelStoreSessionData;
import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.network.VolleyNetwork;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.GSON;

import project.kimora.sellerpintar.utils.Progress;
import project.kimora.sellerpintar.birds.adapters.AdapterBirds;
import project.kimora.sellerpintar.birds.models.ModelBirds;

public class ActivityBirds extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener  , AdapterBirds.Listener, VolleyNetwork.NetworkListener, View.OnClickListener {
    private AdapterBirds adapterBirds;
    private MenuItem menuNewRecord;
    private VolleyNetwork network;
    private ModelUser user;
    private Progress progress;
    private RelativeLayout Delete , SyncEcommerce;
    private SwipeRefreshLayout swipeRefresh;
    private ArrayList<ModelBirds> birdList = new ArrayList<>();
    private ArrayList<ModelBirds> productSearchList = new ArrayList<>();
    private ArrayList<ModelBirds> syncList = new ArrayList<>();
    private String skuId ,productId , productName , stock;
    private boolean searchProduct = false;
    private final int NEW_PRODUCT = 134;
    private BottomSheetBehavior bottomSheetBehavior;
    private boolean enableBottomBehaviour;
    private EditText etSearch;

    private int page = 1;
    private boolean pageLoaded;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birds);
        setBackToolbar();

        network     = new VolleyNetwork(this, this);
        ModelStoreSessionData store = new ModelStoreSessionData(this);
        user    = store.getUser();
        initView();
        getBirds();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           // case R.id.new_record:
               // Intent newproduct = new Intent(this, NewProduct.class);
               // startActivityForResult(newproduct , NEW_PRODUCT);
             //   break;
           // case R.id.sync_ecommerce:
               // SyncEcommerce();
               // break;

        }
        return super.onOptionsItemSelected(item);
    }
    private void initView() {
        progress    = new Progress(this);
        progress.setTapListener(this);
        swipeRefresh    = findViewById(R.id.swipe_refresh);
        Delete    = findViewById(R.id.delete);
        SyncEcommerce = findViewById(R.id.SyncEcommerce);
        etSearch    = findViewById(R.id.etSearch);
        RelativeLayout llBottomSheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        adapterBirds = new AdapterBirds(this, this, birdList );
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterBirds);

        Delete.setOnClickListener(this);
        SyncEcommerce.setOnClickListener(this);
        swipeRefresh.setOnRefreshListener(this);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    // findViewById(R.id.container).setVisibility(View.GONE);
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    // findViewById(R.id.container).setVisibility(View.VISIBLE);
                } else if (newState == BottomSheetBehavior.STATE_DRAGGING && !enableBottomBehaviour) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });

        //Listens for certain keys to be pushed, particular the dpad center key or enter key
        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    if((keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_DPAD_CENTER))
                    {
                        searchProduct = true;
                        birdList.clear();
                     //   loadProductSearchList();


                        return true;
                    }
                    return false;
                }
                return false;
            }
        });


    }



    @Override
    public void onRefresh() {

        page = 1;
        birdList.clear();
        adapterBirds.notifyDataSetChanged();
        adapterBirds.limitNow(false);
        progress.showProgressBar();
        getBirds();


    }



    private void getBirds() {

        try {
            JSONObject param = new JSONObject();
            Constants.headers.put("Content-Type", "application/json");
            Constants.headers.put("Authorization", "Bearer " + user.token);
            param.put("farm_id", user.user_id);
            param.put("user_id", user.user_id);
            param.put("page", page);
            network.jsonPOST(param, Constants.URL_API + "birds/get", "getBirds");
            pageLoaded = true;
        } catch (JSONException e){e.printStackTrace();}
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.progress_layout:
                progress.showProgressBar();
                onRefresh();
                break;
            case R.id.delete:
                new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogTheme))
                        .setMessage("Anda yakin ingin menghapus " + productName +" ?")
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

                break;

            case R.id.SyncEcommerce:
                new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogTheme))
                        .setMessage("Anda yakin ingin Sync " + productName +" ?")
                        .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SyncItemEcommerce();
                                dialog.dismiss();

                            }
                        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                break;

            case R.id.btn_ok:
                dialog.dismiss();
                onRefresh();
                finish();
                break ;

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (pageLoaded) {
            onRefresh();
        }
    }
    @Override
    public void onDetailClick(ModelBirds product)    {

        //    ProductModel data;
        //  data = productList.get(position);
        // Intent nurseDetail = new Intent(this, ProductDetail.class);
        // nurseDetail.putExtra("insurance", GSON.toJsonString(insurance));
        //nurseDetail.putExtra("id", data.id);
        //nurseDetail.putExtra("id_master_asuransi", data.id_master_asuransi);
        // startActivity(nurseDetail);
    }

    private void notifyCancelOrder() {
        try {
            ModelStoreSessionData store = new ModelStoreSessionData(this);
            ModelUser user = store.getUser();
            JSONObject param = new JSONObject();
            param.put("ProductID", productId);
            dialog.showProgressDialog("Tunggu Sebentar ...");
            network.jsonPOST(param, Constants.URL_BASE + "v1/api.php?request=delete_product", "delete");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void SyncItemEcommerce () {
        try {
            JSONObject param = new JSONObject();
            param.put("SkuID", skuId);
            param.put("Stock", stock);
            dialog.showProgressDialog("Tunggu Sebentar ...");
            network.jsonPOST(param, Constants.URL_BASE + "v1/lazada.php?request=update_product", "lazada");
        } catch (JSONException e){e.printStackTrace();}
    }

    private void SyncEcommerce () {
        syncList.clear();
        try {
            JSONObject param = new JSONObject();
            param.put("user_id",user.user_id);
            network.jsonPOST(param, Constants.URL_API + "products.php?request=get_products", "SyncList");
            dialog.showProgressDialog("Tunggu Sebentar ...");
        } catch (JSONException e){e.printStackTrace();}




    }

    @Override
    public void onActionClick(ModelBirds product)    {
//        productId = product.product_id;
//        productName = product.product_name;
//        skuId = product.SkuID;
//        stock = product.Stock;
        enableBottomBehaviour   = true;
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        findViewById(R.id.container).setVisibility(View.VISIBLE);

        //   Toast.makeText(this, "Tes" + product.ProductName , Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLoadMore() {
        page += 1;
        getBirds();
    }




    @Override
    public void onNetworkResponse(JSONObject response, String key) throws JSONException {
        progress.dismiss(false);
        swipeRefresh.setRefreshing(false);
        try {
            if(response.getInt("status") == 200) {
                if (key.equals("getBirds")) {

                    JSONArray datas = response.getJSONArray("data");
                    for (int i = 0; i < datas.length(); i++) {
                        ModelBirds birds = (ModelBirds) GSON.modelFromJson(datas.getJSONObject(i).toString(), ModelBirds.class);
                        birdList.add(birds);
                        adapterBirds.notifyDataSetChanged();

                    }
                    adapterBirds.limitNow(
                            datas.length() == 0);

                    if (page == 1) {
                        progress.showNoData(response.getInt("total_rows"), response.getString("message"));
                    }


                }
                else if (key.equals("delete")) {
                    Toast.makeText(this, response.getString("message"), Toast.LENGTH_LONG).show();
                    // dialog.setDialogMessage(getString(R.string.success), response.getString("message"));
                    // dialog.btnOK.setOnClickListener(this);
                    onRefresh();

                } else if (key.equals("lazada")) {
                    Toast.makeText(this ,  response.getString("message") , Toast.LENGTH_LONG).show();
                    // dialog.setDialogMessage(getString(R.string.success), response.getString("message"));
                    // dialog.btnOK.setOnClickListener(this);
                    onRefresh();

                } else if (key.equals("postSyncEcommerce")) {

                    Toast.makeText(this ,  response.getString("message") , Toast.LENGTH_LONG).show();
                    onRefresh();



                } else if (key.equals("UpdateSync")) {



                    Toast.makeText(this ,  response.getString("message") , Toast.LENGTH_LONG).show();

                    // onRefresh();



                } else if (key.equals("SyncList")) {


                    JSONArray datas = response.getJSONArray("data");
                    for (int i = 0; i < datas.length(); i++) {
                        ModelBirds product = (ModelBirds) GSON.modelFromJson(datas.getJSONObject(i).toString(), ModelBirds.class);
                        syncList.add(product);
                    }

                    for (int i = 0; i < syncList.size(); i++) {
                        try {
                            JSONObject param = new JSONObject();
                            param.put("user_id", user.user_id);
                           // param.put("ProductID", syncList.get(i).product_id);
                            network.jsonPOST(param, Constants.URL_API + "products.php?request=sync_ecommerce", "postSyncEcommerce");
                            dialog.showProgressDialog("Tunggu Sebentar ...");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }


                }
            }
        } catch (JSONException e){e.printStackTrace();}
    }

    @Override
    public void onNetworkError(JSONObject responseCache, String key) throws JSONException {
        progress.dismiss(true);
        swipeRefresh.setRefreshing(false);
    }




    @Override
    public void onBackPressed() {
        if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else if(bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            super.onBackPressed();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_PRODUCT && resultCode == RESULT_OK ) {
            onRefresh();
        }
    }



}

