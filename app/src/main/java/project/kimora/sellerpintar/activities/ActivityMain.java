package project.kimora.sellerpintar.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
//import android.support.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.viewpager.widget.ViewPager;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import project.kimora.sellerpintar.extensions.BaseActivity;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.fragments.FragmentHistoryPage;
import project.kimora.sellerpintar.fragments.FragmentHome;
//import project.msinfo.sellerpintarkasir.fragments.Profile;
//import project.msinfo.sellerpintarkasir.fragments.Login;
import project.kimora.sellerpintar.fragments.FragmentMaintenace;
import project.kimora.sellerpintar.fragments.FragmentProfile;
import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.models.ModelStoreSessionData;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.extensions.ViewPagerAdapter;
import project.kimora.sellerpintar.extensions.ViewPagerFixed;
import androidx.annotation.NonNull;
//Activity utama untuk menghandle halaman pager Home, History dan User Setting
public class ActivityMain extends BaseActivity implements ViewPager.OnPageChangeListener {
    public ViewPagerFixed viewPager;
    private ViewPagerAdapter adapter;
    public BottomNavigationView bottomNavigation;

    public ModelUser modelUser;

//    private int guideId;
    private boolean historyLoaded , profileLoaded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        Constants.initDisplaySize(this);

        ModelStoreSessionData store  = new ModelStoreSessionData(this);
        modelUser = store.getUser();

//        guide                   = store.getGuideData();

        initView();
        checkForgotPassword();
    }



    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        //        MenuItem accountMenu = menu.findItem(R.id.)
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }
    */

    //Saat kondisi baru melakukan forgot password, maka muncul halaman ganti password
    private void checkForgotPassword() {
        if(getIntent().getBooleanExtra("forgot", false)) {
         //   startActivity(new Intent(this, ChangePassword.class));
        }
    }

    //Inisiasi view
    private void initView() {
        bottomNavigation = findViewById(R.id.navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initViewPager();

    }

    private void initViewPager() {

        viewPager = findViewById(R.id.viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentHome(), "");
        adapter.addFragment(new FragmentMaintenace(), "");
        adapter.addFragment(new FragmentMaintenace(), "");
        adapter.addFragment(new FragmentMaintenace(), "");
//        adapter.addFragment(new FragmentHistoryPage(), "");
//        adapter.addFragment(new FragmentHistoryPage(), "");
//        adapter.addFragment(new FragmentHistoryPage(), "");
        adapter.addFragment(new FragmentProfile(), "");
        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

    }

    //Handle keadaan saat navigasi bawah di click
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_history:
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.navigation_profile:
                    viewPager.setCurrentItem(4);
                    return true;

            }
            return false;
        }
    };

//    public void Guide() {
//        ShowcaseView showcaseView = null;
//        if(!guide.main) {
//            showcaseView = guide.showGuide(this, bottomNavigation.getChildAt(1), listener, "Beranda", "Kamu dapat memulai semua menu fitur V-Tal dari sini");
//        }
//    }

    //Handle ketika button back di tap, maka munculkan popup peringatan
    @Override
    public void onBackPressed() {
        if(adapter.getItem(viewPager.getCurrentItem()) instanceof FragmentHome) {
            new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogTheme))
                    .setMessage(R.string.sure_quit_app)
                    .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finish();
                        }
                    }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        } else {
            viewPager.setCurrentItem(0);
            bottomNavigation.setSelectedItemId(R.id.navigation_home);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        try {
          /*  if (position == 1 && !historyLoaded) { //Load History Page
                //((FragmentProfile) adapter.getItem(viewPager.getCurrentItem())).refreshSaldo();
                ((FragmentHistoryPage) adapter.getItem(viewPager.getCurrentItem())).loadFirstPage();
                historyLoaded = true;
            } else if(position == 2) {
            ((FragmentProfile) adapter.getItem(viewPager.getCurrentItem())).refreshSaldo();
           // loadNotification();
            //loadChat();
            } else if(position == 3) {
                ((FragmentProfile) adapter.getItem(viewPager.getCurrentItem())).refreshSaldo();
                // loadNotification();
                //loadChat();
            }else if(position == 4) {
                    ((FragmentProfile) adapter.getItem(viewPager.getCurrentItem())).refreshSaldo();
                    // loadNotification();
                    //loadChat();
                }*/
            } catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //Keadaan ketika activity setelah activity ini finish
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == Constants.MY_PERMISSION_REQUEST_CODE) {
            adapter.getItem(viewPager.getCurrentItem()).onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
