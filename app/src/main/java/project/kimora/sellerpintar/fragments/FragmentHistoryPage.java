package project.kimora.sellerpintar.fragments;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import project.kimora.sellerpintar.extensions.BaseFragment;
import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.extensions.ViewPagerAdapter;
//import com.twinzahra.twinzahra.ambulance.fragments.HistoryAmbulance;
//import com.twinzahra.twinzahra.doctor.fragments.HistoryDoctor;
//import com.twinzahra.twinzahra.nurse.fragments.HistoryNurse;
//import com.twinzahra.twinzahra.nurse.homecare.fragments.HistoryHomecare;
//import com.twinzahra.twinzahra.pharmacy.fragments.HistoryPharmacy;

/**
 * Created by msinfo on 11/02/23.
 */

public class FragmentHistoryPage extends BaseFragment implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private boolean homecareLoaded, doctorLoaded, pharmacyLoaded , promoLoaded;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        viewPager       = view.findViewById(R.id.viewpager);
        adapter         = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new FragmentHistoryPromo(), "Menunggu Pembayaran");
        adapter.addFragment(new FragmentHistoryPromo(), "Menunggu Konfirmasi");
        adapter.addFragment(new FragmentHistoryPromo(), "Sedang Diproses");
        adapter.addFragment(new FragmentHistoryPromo(), "Dikirim");
        adapter.addFragment(new FragmentHistoryPromo(), "Selesai");
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if(position == 1 && !homecareLoaded) { //Load HistoryHomecare Page
            ((FragmentHistoryPromo)adapter.getItem(1)).loadHistory();
            homecareLoaded = true;
        } else if(position == 2 && !doctorLoaded) { //Load HistoryDoctor Page
            ((FragmentHistoryPromo)adapter.getItem(2)).loadHistory();
            doctorLoaded = true;
        } else if(position == 3 && !pharmacyLoaded) { //Load HistoryPharmacy Page
            ((FragmentHistoryPromo) adapter.getItem(3)).loadHistory();
            pharmacyLoaded = true;
            if (position == 1 && !promoLoaded) { //Load HistoryPromo Page
                ((FragmentHistoryPromo) adapter.getItem(1)).loadHistory();
                promoLoaded = true;
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void loadFirstPage() {
        ((FragmentHistoryPromo)adapter.getItem(0)).loadHistory();
    }
}
