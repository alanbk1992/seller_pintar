package project.kimora.sellerpintar.fragments;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import android.util.Base64;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.firestore.auth.User;

import org.json.JSONException;
import org.json.JSONObject;

import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;
import project.kimora.sellerpintar.BuildConfig;
import project.kimora.sellerpintar.R;
//import project.kimora.sellerpintar.activities.ActivityProfileUpdate;
//import project.kimora.sellerpintar.activities.ChangePassword;
import project.kimora.sellerpintar.activities.ActivityMain;
//import project.kimora.sellerpintar.activities.WalletDetail;
//import project.kimora.sellerpintar.activities.WebPage;
import project.kimora.sellerpintar.models.ModelUser;
import project.kimora.sellerpintar.network.VolleyNetwork;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.GSON;
import project.kimora.sellerpintar.utils.ValueFormatter;
//import project.kimora.sellerpintar.activities.EmergencyList;
import project.kimora.sellerpintar.extensions.BaseFragment;

/**
 * Created by Alan on 15/04/23.
 */

public class FragmentProfile extends BaseFragment implements ViewPager.OnPageChangeListener, VolleyNetwork.NetworkListener, View.OnClickListener {
    private TextView tvCountReferral , tvChange, tvName, tvEmail, tvPhone, tv_version, tvReferral , tvReferralBy, tvSaldo , tvEmergency;
    private ModelUser modelUser;
    private RelativeLayout  btnCountReferral  , layoutReferral , wallet, logout, disclaimer, share, rate_us, referral, referralBy , help, emergency;
    private LinearLayout layoutCountReferral , changePassword;
    private VolleyNetwork network;
    private Button btnClose;
    private final int PROFILE_UPDATE_RC = 1212;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        modelUser = ((ActivityMain) getActivity()).modelUser;
        network = new VolleyNetwork(getActivity(), this);
        ((ActivityMain) getActivity()).viewPager.addOnPageChangeListener(this);

        initView(view);
/*        loadUserData();

        getCountReferral();*/
        parseProfile();
        return view;
    }

    private void initView(View view) {
        tvName = view.findViewById(R.id.tvName);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvPhone = view.findViewById(R.id.tvPhone);

   tv_version = view.findViewById(R.id.tv_version);
        logout = view.findViewById(R.id.logout);
        changePassword = view.findViewById(R.id.change_password);
//        disclaimer = view.findViewById(R.id.disclaimer);
//        share = view.findViewById(R.id.share);
//        rate_us = view.findViewById(R.id.rate_us);
//        referral = view.findViewById(R.id.referral);
//        referralBy = view.findViewById(R.id.referralBy);
//        wallet = view.findViewById(R.id.wallet);
//        tvReferral = view.findViewById(R.id.tv_referral);
//        tvReferralBy = view.findViewById(R.id.tv_referralBy);
//        tvCountReferral = view.findViewById(R.id.tv_count_referral);
//        btnCountReferral = view.findViewById(R.id.btn_count_referral);

//        layoutReferral = view.findViewById(R.id.layout_referral);
//        emergency = view.findViewById(R.id.emergency);
//        tvSaldo = view.findViewById(R.id.tv_saldo);
//        help = view.findViewById(R.id.help);

     //   changePassword.setVisibility(user.GoogleUserID == null && !user.Password.isEmpty() ? View.VISIBLE : View.GONE);


      //  tvChange.setOnClickListener(this);
        logout.setOnClickListener(this);

       // if (user.profile_complete.equals("0")) {
           // Toast.makeText(getContext(), "Profile anda belum lengkap , Silahkan update terlebih dahulu", Toast.LENGTH_LONG).show();
       // }else if (user.profile_complete.equals("1")) {
        //    share.setOnClickListener(this);
       // }

//        rate_us.setOnClickListener(this);
//        referral.setOnClickListener(this);
//        disclaimer.setOnClickListener(this);
//        referralBy.setOnClickListener(this);
//        changePassword.setOnClickListener(this);
//        wallet.setOnClickListener(this);
//        help.setOnClickListener(this);
//        emergency.setOnClickListener(this);
//        layoutReferral.setOnClickListener(this);
//        btnCountReferral.setOnClickListener(this);
//        btnClose.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.layout_referral:
//              //  ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//              //  ClipData clip = ClipData.newPlainText("referral", user.ReferralID);
//               // clipboard.setPrimaryClip(clip);
//               // Toast.makeText(getContext(), "Referral ID telah disalin", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.btn_count_referral:
//                getActivity().findViewById(R.id.layout_count_referral).setVisibility(View.VISIBLE);
//                break;
//            case R.id.btn_close:
//                getActivity().findViewById(R.id.layout_count_referral).setVisibility(View.GONE);
//                break;
//            case R.id.referralBy:
//               // ClipboardManager clipboardBy = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
//                //ClipData clipBy = ClipData.newPlainText("referral by", user.ReferralBy);
//                //clipboardBy.setPrimaryClip(clipBy);
//               // Toast.makeText(getContext(), "Referral BY telah disalin", Toast.LENGTH_SHORT).show();
//                break;
         /*   case R.id.tvChange:
                update();
                break;*/
            case R.id.logout:
                logout();
                break;
//            case R.id.share:
//              //  if (user.profile_complete.equals("0")) {
//               //     Toast.makeText(getContext(), "Profile anda belum lengkap , Silahkan update terlebih dahulu", Toast.LENGTH_LONG).show();
//              //  }else if (user.profile_complete.equals("1")) {
//              //      shareApp();
//              //  }
//                break;
//            case R.id.rate_us:
//                goingToPlayStore();
//                break;
            case R.id.change_password:
                //startActivity(new Intent(getActivity(), ChangePassword.class));
               // break;

        }
    }
    private void loadUserData() {
        network.jsonGET(Constants.URL_BASE + "v1/patient.php?request=user_data", "UserData");
    }
    public void refreshSaldo() {
        network.jsonPOST(new JSONObject(), Constants.URL_API + "patient.php?request=get_user_wallet", "wallet");
    }

    private void getCountReferral() {
       // try {
       //     JSONObject postData = new JSONObject();
           // postData.put("UserID", user.UserID);
         //   network.jsonPOST(postData, Constants.URL_API + "patient.php?request=get_referralid_used", "referral");
      //  } catch (JSONException e){e.printStackTrace();}
    }
    public void parseProfile() {
        try {
         tvName.setText(modelUser.first_name + " " + modelUser.last_name);
       tvEmail.setText(modelUser.email);
           tvPhone.setText(modelUser.phone);
           tv_version.setText("Version " +BuildConfig.VERSION_NAME);
            //tvReferral.setText(user.ReferralID);
           // tvReferralBy.setText(user.ReferralBy);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void update() {
       // Intent profile = new Intent(getContext(), ActivityProfileUpdate.class);
      //  startActivityForResult(profile, PROFILE_UPDATE_RC);
    }

    private void shareApp() {
      //  Intent share = new Intent(android.content.Intent.ACTION_SEND);
       // share.setType("text/plain");
       // share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

       // share.putExtra(Intent.EXTRA_SUBJECT, "Yuk Pakai Aplikasi chealth!" );
        //share.putExtra(Intent.EXTRA_TEXT, "Yuk Pakai Aplikasi chealth!\n" + Constants.URL_GOOGLE_PLAY + "&referrer="+ user.ReferralID);

      //  startActivity(Intent.createChooser(share, "Share link!"));
    }

    private void goingToPlayStore() {
        final String appPackageName = getActivity().getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public void logout() {
        new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.AlertDialogTheme))
                .setMessage("Apakah anda ingin keluar?")
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog2, int which) {
                        try {
                            JSONObject param = new JSONObject();
                            param.put("user_id", modelUser.user_id);
                            ((ActivityMain) getActivity()).dialog.showProgressDialog("Tunggu sebentar...");
                          network.jsonPOST(param, Constants.URL_API + "users/logout", "logout");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        dialog2.dismiss();
                    }
                }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        if (position == 2) {
            if (!FormValidator.isLiveValidationRunning(this))
                FormValidator.startLiveValidation(this, getView(), new SimpleErrorPopupCallback(getActivity(), false));
        } else {
            FormValidator.stopLiveValidation(this);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onNetworkResponse(JSONObject response, String key) throws JSONException {
        if (response.getInt("status") == 200) {
            if (key.equals("logout")) {
                if (!modelUser.GoogleUserID.isEmpty()) {
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                    mGoogleSignInClient.signOut();
                }
                ModelUser.forceLogout(getActivity());
            } else if (key.equals("wallet")) {
                JSONObject data = response.getJSONArray("data").getJSONObject(0);
                String saldo = new String(Base64.decode(data.getString("Total"), Base64.DEFAULT)).replace(data.getString("PasswordSalt"), "");
                tvSaldo.setText("Rp " + ValueFormatter.formatPrice(Long.parseLong(saldo)));
            } else if (key.equals("referral")) {
                if (response.getInt("total_rows") != 0) {
                    tvCountReferral.setText(response.getString("total_rows"));
                }

            } else if (key.equals("UserData")) {
                JSONObject datas = response.getJSONArray("data").getJSONObject(0);
                //  JSONObject userData = data.getJSONArray("data").getJSONObject(0);
                tvReferral.setText(datas.getString("ReferralID"));
            }
        }else{
            Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onNetworkError(JSONObject responseCache, String key) throws JSONException {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PROFILE_UPDATE_RC && resultCode == getActivity().RESULT_OK) {
           // user = (User) GSON.modelFromJson(data.getStringExtra("user"), User.class);
          //  ((Main) getActivity()).user = user;
            parseProfile();

        }
    }
}
