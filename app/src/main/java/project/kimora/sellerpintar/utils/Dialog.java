package project.kimora.sellerpintar.utils;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import project.kimora.sellerpintar.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by msinfo on 09/02/23.
 */


public class Dialog {
    public Context mContext;
    public Button btnOK;
    public ProgressDialog progressDialog;
    public AlertDialog alert;

    public Dialog(Context context) {
        this.mContext = context;
    }



    public void setDialogMessage(String header, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.ProgressDialog);
        LayoutInflater inflater = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
        View view = inflater.inflate(R.layout.dialog_message, null);
        builder.setView(view);
        TextView title = new TextView(mContext);
        title.setText(header);
        title.setPadding(20, 30, 20, 30);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20f);
        title.setTypeface(null, Typeface.BOLD);
        builder.setCustomTitle(title);

        alert = builder.create();
        alert.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alert.setCancelable(false);


        TextView tvMessage = view.findViewById(R.id.tvmessage);
        tvMessage.setText(message);
        btnOK = view.findViewById(R.id.btn_ok);
        btnOK.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        alert.dismiss();

                    }
                });
        alert.show();
    }

    public void showProgressDialog(String message) {
        if(progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void setDialogRating(Context mContext, final RatingListener ratingListener, String mitra) {
        RatingListener listener = ratingListener;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_rating, null);
        builder.setView(view);

        alert = builder.create();
        alert.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alert.show();

        TextView tvDesc             = (TextView)view.findViewById(R.id.tv_desc);
        final TextView tvReview     = (TextView)view.findViewById(R.id.tv_review);
        final EditText etReview     = (EditText)view.findViewById(R.id.et_review);
        View cancel                 = view.findViewById(R.id.cancel);
        View send                   = view.findViewById(R.id.send);
        final RatingBar ratingBar   = (RatingBar)view.findViewById(R.id.ratingBar);
        final List<String> ratingReview = Arrays.asList("Sangat Buruk", "Sangat Buruk", "Buruk", "Cukup bagus", "Bagus", "Sangat bagus");

        tvDesc.setText(tvDesc.getText().toString() + " " + mitra);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ratingListener.onSendRating((int)ratingBar.getRating(), etReview.getText().toString());
                alert.dismiss();
            }
        });
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                tvReview.setText(ratingReview.get((int)rating));
            }
        });
        //Manual Touch Handler
        if(Build.VERSION.SDK_INT == 24) {   //Fixing bug ratingBar in API 24 Nougat (7.0)
            ratingBar.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent event) {
                    float touchPositionX = event.getX();
                    float width = ratingBar.getWidth();
                    float starsf = (touchPositionX / width) * 5.0f;
                    int stars = (int) starsf + 1;
                    ratingBar.setRating(stars);
                    return true;
                }
            });
        }
    }

    public void dismiss() {
        if(progressDialog != null)
            progressDialog.dismiss();

    }

    public void dismissAlert() {
        if(alert != null)
            alert.dismiss();
    }
    public void finishAlert(){
        alert.dismiss();
    }
    public interface RatingListener {
        void onSendRating(int rating, String comment);
    }

}
