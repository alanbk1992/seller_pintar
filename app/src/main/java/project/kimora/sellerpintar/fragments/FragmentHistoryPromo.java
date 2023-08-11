package project.kimora.sellerpintar.fragments;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.activities.ActivityDetailHistoryPromo;
import project.kimora.sellerpintar.activities.ActivityMain;
import project.kimora.sellerpintar.adapters.AdapterHistoryPromo;
import project.kimora.sellerpintar.extensions.WrapContentLinearLayoutManager;
import project.kimora.sellerpintar.models.ModelPromo;
import project.kimora.sellerpintar.network.VolleyNetwork;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.GSON;
import project.kimora.sellerpintar.utils.Progress;
import project.kimora.sellerpintar.extensions.BaseFragment;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by msinfo 12/04/23.
 */
public class FragmentHistoryPromo extends BaseFragment implements AdapterHistoryPromo.Listener, VolleyNetwork.NetworkListener, View.OnClickListener {
    private AdapterHistoryPromo mAdapter;

    private Progress progress;
    private RecyclerView recyclerView;
    private VolleyNetwork network;
    private ArrayList<ModelPromo> orderList = new ArrayList<>();
    private int page = 1;
    private boolean pageLoaded;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_history_promo, container, false);
        initView(view);

        network     = new VolleyNetwork(getActivity(), this);

        return view;
    }

    private void initView(View view) {
        progress = new Progress(getActivity(), view);
        progress.setTapListener(this);

        recyclerView = view.findViewById(R.id.recycler_view);
        mAdapter = new AdapterHistoryPromo(getActivity(), this, orderList);
        recyclerView.setLayoutManager(new WrapContentLinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDetailClick(ModelPromo order) {
        Intent newIntent = new Intent(getActivity(), ActivityDetailHistoryPromo.class);
        newIntent.putExtra("PromoOrderID", order.PromoOrderID);
        startActivity(newIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.progress_layout:
               // refreshData();
                break;
        }
    }

    public void onLoadMore() {
        page += 1;
        loadHistory();
    }
    public void refreshData() {
        page = 1;
        orderList.clear();
        mAdapter.notifyDataSetChanged();
        progress.showProgressBar();
       // loadHistory();
    }


    public void loadHistory() {
        try {
            JSONObject param = new JSONObject();
            param.put("UserID", ((ActivityMain)getActivity()).modelUser.user_id);
            param.put("Page", page);
            network.jsonPOST(param, Constants.URL_BASE + "v1/patient.php?request=history_promo", "promo_history");
            pageLoaded = true;
        } catch (Exception e){e.printStackTrace();}
    }


    @Override
    public void onNetworkResponse(JSONObject response, String key) throws JSONException {
        recyclerView.setVisibility(View.VISIBLE);
        progress.dismiss(false);
        try {
            if(response.getInt("status") == 200) {
                if(key.equals("promo_history")) {
                    progress.showNoData(response.getInt("total_rows"), response.getString("message"));

                    JSONArray datas = response.getJSONArray("data");
                    for (int i = 0; i < datas.length(); i++) {
                        ModelPromo order = (ModelPromo) GSON.modelFromJson(datas.getJSONObject(i).toString(), ModelPromo.class);
                        orderList.add(order);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        } catch (JSONException e){e.printStackTrace();}
    }

    @Override
    public void onNetworkError(JSONObject responseCache, String key) throws JSONException {
        recyclerView.setVisibility(View.GONE);
        progress.dismiss(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(pageLoaded) {
           // refreshData();
        }
    }



}
