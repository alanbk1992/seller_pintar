package project.kimora.sellerpintar.utils;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.kimora.sellerpintar.R;

/**
 * Created by msinfo on 11/02/23.
 */

public class Progress {
    private Activity activity;
    private ProgressBar progressBar;
    private TextView tvMessage;
    private ImageView ivOffline;
    private RelativeLayout progressLayout;

    public Progress(Activity activity) {
        this.activity = activity;
        progressBar     = activity.findViewById(R.id.progressbar);
        tvMessage       = activity.findViewById(R.id.tv_message);
        ivOffline       = activity.findViewById(R.id.img_offline);
        progressLayout  = activity.findViewById(R.id.progress_layout);
    }

    public Progress(Activity activity, View view) {
        this.activity = activity;
        progressBar     = view.findViewById(R.id.progressbar);
        tvMessage       = view.findViewById(R.id.tv_message);
        ivOffline       = view.findViewById(R.id.img_offline);
        progressLayout  = view.findViewById(R.id.progress_layout);
    }

    public void setTapListener(View.OnClickListener listener) {
        progressLayout.setOnClickListener(listener);
    }

    public void showProgressBar() {
        dismiss(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void dismiss(boolean error) {
        progressBar.setVisibility(View.GONE);
        if (error) {
            ivOffline.setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(activity.getString(R.string.error_connection));
        } else {
            ivOffline.setVisibility(View.GONE);
            tvMessage.setVisibility(View.GONE);
        }
    }

    public void showNoData(int size, String message) {
        if(size == 0) {
            tvMessage.setVisibility(View.VISIBLE);
            tvMessage.setText(message);
        } else {
            tvMessage.setVisibility(View.GONE);
        }
    }

}
