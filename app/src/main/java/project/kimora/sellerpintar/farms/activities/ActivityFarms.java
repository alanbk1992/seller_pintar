package project.kimora.sellerpintar.farms.activities;


import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.farms.adapters.AdapterFarms;
import project.kimora.sellerpintar.farms.models.ModelFarms;
import project.kimora.sellerpintar.models.ModelStoreSessionData;
import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.extensions.BaseActivity;
import project.kimora.sellerpintar.network.VolleyNetwork;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.GSON;

public class ActivityFarms extends BaseActivity implements AdapterFarms.Listener, View.OnClickListener, VolleyNetwork.NetworkListener, SwipeRefreshLayout.OnRefreshListener  {


    //private List<ModelFarms> farmList = new ArrayList<>();
    private ArrayList<ModelFarms> farmList = new ArrayList<>();
    private AdapterFarms adapterFarms;

    private int page = 1;
    private boolean pageLoaded;
    private VolleyNetwork network;
    private final int ACTIVITY_RESULT = 2001;
    private ModelUser modelUser;

    private SwipeRefreshLayout swipeRefresh;
    private NestedScrollView content_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farms);
        setBackToolbar();
        network   = new VolleyNetwork(this, this);
        ModelStoreSessionData modelStoreSessionData = new ModelStoreSessionData(getApplication());
        modelUser = modelStoreSessionData.getUser();
        initView();

        getFarms();
    }

    private void initView() {
        swipeRefresh = findViewById(R.id.swipe_refresh);
        swipeRefresh.setOnRefreshListener(this);
        swipeRefresh.setRefreshing(false);

        RecyclerView serviceRecycle = findViewById(R.id.recyclerview);
        adapterFarms        = new AdapterFarms(farmList , this , this );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(), 1);
        serviceRecycle.setLayoutManager(mLayoutManager);
        serviceRecycle.setItemAnimator(new DefaultItemAnimator());
        serviceRecycle.setAdapter(adapterFarms);


    }




    public void onLoadMore() {
        page += 1;
        getFarms();
    }

    public void getFarms() {
        try {
            JSONObject param = new JSONObject();
            param.put("user_id", (modelUser.user_id));
            param.put("page", page);
            network.jsonPOST(param, Constants.URL_API + "farms/get", "getFarms");
            pageLoaded = true;
        } catch (Exception e){e.printStackTrace();}
    }



    @Override
    public void onRefresh() {
        farmList.clear();
        page = 1;
        adapterFarms.showShimmer = true;
       getFarms();



    }

    @Override
    public void onNetworkResponse(JSONObject response, String key) throws JSONException {
       // recyclerView.setVisibility(View.VISIBLE);
       // progress.dismiss(false);
        swipeRefresh.setRefreshing(false);
        try {
            if(response.getInt("status") == 200) {
                if(key.equals("getFarms")) {
                 //   progress.showNoData(response.getInt("total_rows"), response.getString("message"));

                    JSONArray datas = response.getJSONArray("data");
                    for (int i = 0; i < datas.length(); i++) {
                     ModelFarms farms = (ModelFarms) GSON.modelFromJson(datas.getJSONObject(i).toString(), ModelFarms.class);
                      farmList.add(farms);
                        adapterFarms.showShimmer = false;
                        adapterFarms.notifyDataSetChanged();
                    }



                } else  if(key.equals("setDefaultFarm")) {

                    setResult(RESULT_OK);
                    finish();

                }
            }else {


            }
        } catch (JSONException e){e.printStackTrace();}
    }

    @Override
    public void onNetworkError(JSONObject responseCache, String key) throws JSONException {
     //   recyclerView.setVisibility(View.GONE);
      //  progress.dismiss(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(pageLoaded) {
            // refreshData();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public void onDetailFarmClick(int position) {

        final ModelFarms modelFarms = farmList.get(position);


        try {
            JSONObject param = new JSONObject();
            param.put("user_id", (modelUser.user_id));
            param.put("farm_id", (modelFarms.farm_id));
            dialog.showProgressDialog("Tunggu Sebentar ...");
            network.jsonPOST(param, Constants.URL_API + "farms/set_default", "setDefaultFarm");
            pageLoaded = true;
        } catch (Exception e){e.printStackTrace();}



    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ACTIVITY_RESULT) {
            setResult(RESULT_OK);
            finish();
        }
    }
}



