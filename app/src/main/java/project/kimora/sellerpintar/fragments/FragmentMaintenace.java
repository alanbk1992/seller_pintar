package project.kimora.sellerpintar.fragments;



import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.extensions.BaseFragment;
import project.kimora.sellerpintar.network.VolleyNetwork;

/**
 * Created by Alan on 15/04/23.
 */

public class FragmentMaintenace extends BaseFragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maintenance, container, false);
        return view;
    }


    @Override
    public void onClick(View v) {

    }
}
