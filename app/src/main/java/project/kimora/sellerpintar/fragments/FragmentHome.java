package project.kimora.sellerpintar.fragments;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import project.kimora.sellerpintar.R;


import project.kimora.sellerpintar.birds.activities.ActivityBirds;
import project.kimora.sellerpintar.activities.ActivityMaintenance;
import project.kimora.sellerpintar.adapters.AdapterHome;
import project.kimora.sellerpintar.adapters.AdapterStatisticHome;
import project.kimora.sellerpintar.farms.activities.ActivityFarms;
import project.kimora.sellerpintar.models.ModelHome;
import project.kimora.sellerpintar.models.ModelStatisticHome;
import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.network.VolleyNetwork;
import project.kimora.sellerpintar.services.activities.ActivityServices;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.Progress;

import java.util.ArrayList;
import java.util.List;

import project.kimora.sellerpintar.models.ModelStoreSessionData;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.DefaultItemAnimator;

import android.view.LayoutInflater;
import android.view.ViewGroup;


import project.kimora.sellerpintar.activities.ActivityMain;

//import project.msinfo.sellerpintarkasir.utils.LocationAssistant;
//import project.msinfo.sellerpintarkasir.utils.ValueFormatter;
//import project.msinfo.sellerpintarkasir.adapters.CategoryAdapter;

import android.widget.ImageView;

import project.kimora.sellerpintar.extensions.BaseFragment;

    public class FragmentHome extends BaseFragment implements View.OnClickListener, VolleyNetwork.NetworkListener, SwipeRefreshLayout.OnRefreshListener , AdapterHome.Listener, AdapterStatisticHome.Listener{



    private ImageView   tvBarcode, tvInbox , tvCart;
    private TextView tvNotice , tvFarm , tvFollower , tvMemberStatus;
    private VolleyNetwork network;
    private Progress progress;
    private SwipeRefreshLayout swipeRefresh;
    private int page = 1;
    private ModelUser modelUser;
    public static final int RESULT_OK = -1;
    private boolean pageLoaded;
    public static final String DATA_SAVED_BROADCAST = "com.msinfo.twinzahra.datasaved";

    private BroadcastReceiver broadcastReceiver;

    private final int SEARCH_RC = 134;
    private LinearLayout LayoutSearch , layout_education , layoutFarms   ;
    private NestedScrollView content_layout;
    private List<ModelHome> homeList = new ArrayList<>();
    private AdapterHome mAdapter;
    private AdapterStatisticHome adapterStatisticHome;
    private List<ModelStatisticHome> statisticHomeList = new ArrayList<>();
    private final int RC_FARMS = 1805;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ModelStoreSessionData store = new ModelStoreSessionData(this.getActivity());
        this.modelUser = store.getUser();
        network   = new VolleyNetwork(getActivity(), this);
        ModelStoreSessionData modelStoreSessionData = new ModelStoreSessionData(getContext());
        ModelUser modelUser = modelStoreSessionData.getUser();

        initView(view);

        onRefresh();

        return view;
    }



    private void initView(View view) {

        progress = new Progress(getActivity(), view);
        progress.setTapListener(this);

        swipeRefresh = view.findViewById(R.id.swipe_refresh);
        tvNotice = view.findViewById(R.id.tv_notice);
        tvFarm = view.findViewById(R.id.tv_farm);
        tvFollower = view.findViewById(R.id.tv_follower);
        tvMemberStatus = view.findViewById(R.id.tv_member_status);
        content_layout = view.findViewById(R.id.content_layout);
        layout_education= view.findViewById(R.id.layout_education);
        layoutFarms= view.findViewById(R.id.layout_farms);
        //rvNewProduct = view.findViewById(R.id.rv_product);

        //  initCategory(view);
        // initProduct(view);
        // initNewOrders(view);

        swipeRefresh.setOnRefreshListener(this);


        // progress.dismiss(false);
        swipeRefresh.setRefreshing(false);
        view.findViewById(R.id.layout_farms).setOnClickListener(this);

        initMenuView(view);
        initStatisticHome(view);

    }


    private void loadHome() {
        adapterStatisticHome.showShimmer = true;
        try {
            JSONObject param = new JSONObject();
            Constants.headers.put("Content-Type", "application/json");
            param.put("user_id", modelUser.user_id);
            param.put("token", modelUser.token);
            network.jsonPOST(param, Constants.URL_API + "users/home", "home");
            pageLoaded = true;
        } catch (JSONException e){e.printStackTrace();}
    }





    private void initNewOrders(View view) {
        //RecyclerView homeView = view.findViewById(R.id.rv_new_orders);
        //productNewAdapter        = new ProductNewAdapter(getContext() , this , newProductList , true  );
       // RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 3);
      //  homeView.setLayoutManager(mLayoutManager);
    //   homeView.setItemAnimator(new DefaultItemAnimator());
      // homeView.setAdapter(productNewAdapter);


    }




  //  @Override
    //public void onCategoryDetailClick(CategoryModel menuCategoryModel)    {

        //    ProductModel data;
        //  data = productList.get(position);
        // Intent nurseDetail = new Intent(this, ProductDetail.class);
        // nurseDetail.putExtra("insurance", GSON.toJsonString(insurance));
        //nurseDetail.putExtra("id", data.id);
        //nurseDetail.putExtra("id_master_asuransi", data.id_master_asuransi);
        // startActivity(nurseDetail);
    //}

    private void initCategory(View view) {
       // RecyclerView homeView = view.findViewById(R.id.rv_category);
       // mAdapter        = new CategoryAdapter(getContext() , this , homeList );
        //RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
        //homeView.setLayoutManager(mLayoutManager);
        //homeView.setItemAnimator(new DefaultItemAnimator());
        //homeView.setAdapter(mAdapter);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.progress_layout:
                getActivity().findViewById(R.id.content_layout).setVisibility(View.GONE);
                progress.showProgressBar();
                onRefresh();
                break;
            case R.id.tv_notice:
                //startActivity(new Intent(getActivity(), ActivityProfileUpdate.class));
                //((MainSeller)getActivity()).bottomNavigation.setSelectedItemId(R.id.navigation_profile);
                //((MainSeller)getActivity()).viewPager.setCurrentItem(2);
                break;


            case R.id.btn_ok:
                //((MainSeller) getActivity()).dialog.dismissAlert();

                onRefresh();

                break;

            case R.id.layout_farms:

                Intent intent = new Intent(getContext(), ActivityFarms.class);
                startActivityForResult(new Intent(intent),RC_FARMS);

                break;



        }
    }



   // @Override
   // public void onPromoClick(Promo promo) {
        //Intent promoIntent = new Intent(getActivity(), PromoDetail.class);
       // promoIntent.putExtra("PromoID", promo.PromoID);
       // startActivity(promoIntent);
   // }

    //@Override
    //public void onDetailClick(ProductModel product)    {

       // Intent param = new Intent(getContext(), ProductDetail.class);
       // param.putExtra("ProductID", product.ProductID);
       // startActivity(param);
    //}




    @Override
    public void onRefresh() {

       // productList.clear();
        //newProductList.clear();
        //productAdapter.notifyDataSetChanged();
        //productAdapter.limitNow(false);
        //productNewAdapter.notifyDataSetChanged();
        //((ActivityMain) getActivity()).findViewById(R.id.progress_layout).setVisibility(View.VISIBLE);
        progress.showProgressBar();
       loadHome();
        //content_layout.setVisibility(View.GONE);


    }

    @Override
    public void onNetworkResponse(JSONObject response, String key) throws JSONException {
        ((ActivityMain) getActivity()).dialog.dismissAlert();
        ((ActivityMain) getActivity()).dialog.dismiss();
        progress.dismiss(false);
        swipeRefresh.setRefreshing(false);
        adapterStatisticHome.showShimmer = false;
        try {
            if(response.getInt("status") == 200) {

                if (key.equals("home")) {
                  getActivity().findViewById(R.id.content_layout).setVisibility(View.VISIBLE);

                    JSONObject data = response.getJSONArray("data").getJSONObject(0);

                    JSONArray Farms = data.getJSONArray("farm");

                    if (Farms.length() > 0) {
                        JSONObject Farm = data.getJSONArray("farm").getJSONObject(0);
                        tvFarm.setText(Farm.getString("farm_name"));
                        tvFollower.setText("Follower" + " " + Farm.getString("follower"));
                        //  tvFarm.setText(Farm.getString("farm_name"));
                        getActivity().findViewById(R.id.tv_notice).setVisibility(View.GONE);
                        getActivity().findViewById(R.id.layout_farm).setVisibility(View.VISIBLE);

                    }else {
                        getActivity().findViewById(R.id.tv_notice).setVisibility(View.VISIBLE);
                        tvNotice.setText("Anda belum mempunyai peternakan");
                        getActivity().findViewById(R.id.layout_farm).setVisibility(View.GONE);
                    }

                }


            }
        } catch (JSONException e){e.printStackTrace();}
    }

    private void initMenuView(View view) {
        homeList.clear();
        RecyclerView homeView = view.findViewById(R.id.rv_main_menu);
        mAdapter        = new AdapterHome(homeList , getContext() , (AdapterHome.Listener) this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
        homeView.setLayoutManager(mLayoutManager);
        homeView.setItemAnimator(new DefaultItemAnimator());
        homeView.setAdapter(mAdapter);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                homeList.add(new ModelHome(R.mipmap.ic_menu_bird, "Burung"));
                homeList.add(new ModelHome(R.mipmap.ic_menu_pen, "Kandang"));
                homeList.add(new ModelHome(R.mipmap.ic_menu_matchmaking, "Penjodohan"));
                homeList.add(new ModelHome(R.mipmap.ic_menu_incubator, "Inkubator"));
                homeList.add(new ModelHome(R.mipmap.ic_menu_event, "Agenda"));
              homeList.add(new ModelHome(R.mipmap.ic_menu_market, "Marketplace"));
               homeList.add(new ModelHome(R.mipmap.ic_menu_doctor, "Dokter Hewan"));
               homeList.add(new ModelHome(R.mipmap.ic_menu_more, "Lihat Semua"));
                mAdapter.showShimmer = false;
                mAdapter.notifyDataSetChanged();
            }
        }, 1500);


    }

    private void initStatisticHome(View view) {
        statisticHomeList.clear();
        RecyclerView statisticHome = view.findViewById(R.id.rv_statistic_home);
        adapterStatisticHome        = new AdapterStatisticHome(statisticHomeList , getContext() , (AdapterStatisticHome.Listener) this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 6);
        statisticHome.setLayoutManager(mLayoutManager);
        statisticHome.setItemAnimator(new DefaultItemAnimator());
        statisticHome.setAdapter(adapterStatisticHome);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                statisticHomeList.add(new ModelStatisticHome("25", "Jantan"));
                statisticHomeList.add(new ModelStatisticHome("5", "Betina"));
                statisticHomeList.add(new ModelStatisticHome("30", "Sehat"));
                statisticHomeList.add(new ModelStatisticHome("0", "Sakit"));
                statisticHomeList.add(new ModelStatisticHome("0", "Mati"));
                statisticHomeList.add(new ModelStatisticHome("1", "Siap Jual"));
                adapterStatisticHome.showShimmer = false;
                adapterStatisticHome.notifyDataSetChanged();
            }
        }, 1500);


    }

    @Override
    public void onNetworkError(JSONObject responseCache, String key) throws JSONException {

        progress.dismiss(true);
        getActivity().findViewById(R.id.content_layout).setVisibility(View.GONE);
        swipeRefresh.setRefreshing(false);
    }


    //Parsing data artikel
    private void parseCategory(JSONArray datas) throws JSONException {
        for(int i=0; i<datas.length(); i++) {
            //JSONObject data = datas.getJSONObject(i);
            //CategoryModel category = (CategoryModel) GSON.modelFromJson(data.toString(), CategoryModel.class);
            //homeList.add(category);
        }
        //mAdapter.notifyDataSetChanged();
    }



    @Override
    public void onResume() {
        super.onResume();
        if (pageLoaded == false) {
            onRefresh();
        }

    }

    //Keadaan result saat activity setelah ini, handle hasil login google
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      //  if (requestCode == requestCode) {
            if (resultCode == RESULT_OK) {
                onRefresh();
                //getActivity().finish();
           // }
        }

    }

    @Override
    public void onMenuDetailClick(int position) {
        homeList.get(position);

        if (position == 0) {
          Intent intent = new Intent(getActivity(), ActivityBirds.class);
             getActivity().startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(getActivity(), ActivityMaintenance.class);
            getActivity().startActivity(intent);
        } else if (position == 2) {
            Intent intent = new Intent(getActivity(), ActivityMaintenance.class);
            getActivity().startActivity(intent);
        } else if (position == 3) {
            Intent intent = new Intent(getActivity(), ActivityMaintenance.class);
            getActivity().startActivity(intent);

        }else if (position == 4) {
            Intent intent = new Intent(getActivity(), ActivityMaintenance.class);
            getActivity().startActivity(intent);

        }else if (position == 5) {
           Intent intent = new Intent(getActivity(), ActivityMaintenance.class);
           getActivity().startActivity(intent);

        }else if (position == 6) {
            Intent intent = new Intent(getActivity(), ActivityMaintenance.class);
            getActivity().startActivity(intent);

        }else if (position == 7) {
            Intent intent = new Intent(getActivity(), ActivityServices.class);
            getActivity().startActivity(intent);

        }



        tvNotice.setVisibility(View.GONE);
    }

    @Override
    public void onStatisticDetailClick(int position) {

    }
}
