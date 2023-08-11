package project.kimora.sellerpintar.services.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.birds.activities.ActivityBirds;
import project.kimora.sellerpintar.activities.ActivityMaintenance;
import project.kimora.sellerpintar.services.adapters.ServiceAdapter;
import project.kimora.sellerpintar.extensions.BaseActivity;
import project.kimora.sellerpintar.services.models.ServiceModel;

public class ActivityServices extends BaseActivity implements ServiceAdapter.Listener, View.OnClickListener {


    private List<ServiceModel> serviceList = new ArrayList<>();
    private ServiceAdapter serviceAdapter;

    private final int ACTIVITY_RESULT = 2001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        setBackToolbar();
        initView();
    }

    private void initView() {

        serviceList.clear();
        RecyclerView serviceRecycle = findViewById(R.id.recyclerview);
        serviceAdapter        = new ServiceAdapter(serviceList , getApplication() , this );
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplication(), 1);
        serviceRecycle.setLayoutManager(mLayoutManager);
        serviceRecycle.setItemAnimator(new DefaultItemAnimator());
        serviceRecycle.setAdapter(serviceAdapter);

        showServices();
    }

    private void showServices() {

        new Handler().postDelayed(new Runnable() {
            public void run() {
                serviceList.add(new ServiceModel(R.mipmap.ic_menu_bird, "Hewan"));
                serviceList.add(new ServiceModel(R.mipmap.ic_menu_pen, "Kandang"));
                serviceList.add(new ServiceModel(R.mipmap.ic_menu_matchmaking, "Penjodohan"));
                serviceList.add(new ServiceModel(R.mipmap.ic_menu_incubator, "Inkubator"));
                serviceList.add(new ServiceModel(R.mipmap.ic_menu_event, "Agenda"));
                serviceList.add(new ServiceModel(R.mipmap.ic_menu_market, "Pasar"));
                serviceList.add(new ServiceModel(R.mipmap.ic_menu_doctor, "Dokter Hewan"));
                serviceList.add(new ServiceModel(R.mipmap.ic_menu_animal_care, "Penitipan Hewan"));
                serviceAdapter.showShimmer = false;
                serviceAdapter.notifyDataSetChanged();

            }
        }, 1500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public void onMenuDetailClick(int position) {

        serviceList.get(position);

        if (position == 0) {
            Intent intent = new Intent(this, ActivityBirds.class);
            this.startActivity(intent);
        } else if (position == 1) {
            Intent intent = new Intent(this, ActivityMaintenance.class);
            this.startActivity(intent);
        } else if (position == 2) {
            Intent intent = new Intent(this, ActivityMaintenance.class);
            this.startActivity(intent);
        } else if (position == 3) {
            Intent intent = new Intent(this, ActivityMaintenance.class);
            this.startActivity(intent);

        }else if (position == 4) {
            Intent intent = new Intent(this, ActivityMaintenance.class);
            this.startActivity(intent);

        }else if (position == 5) {
            Intent intent = new Intent(this, ActivityMaintenance.class);
            this.startActivity(intent);

        }else if (position == 6) {
            Intent intent = new Intent(this, ActivityMaintenance.class);
            this.startActivity(intent);

        }else if (position == 7) {
            Intent intent = new Intent(this, ActivityMaintenance.class);
            this.startActivity(intent);

        }else if (position == 8) {
            Intent intent = new Intent(this, ActivityMaintenance.class);
            this.startActivity(intent);

        }else if (position == 9) {
            Intent intent = new Intent(this, ActivityMaintenance.class);
            this.startActivity(intent);

        }






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



