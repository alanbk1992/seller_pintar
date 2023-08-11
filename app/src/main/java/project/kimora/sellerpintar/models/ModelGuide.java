package project.kimora.sellerpintar.models;

import android.app.Activity;
import android.view.View;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import project.kimora.sellerpintar.R;

/**
 * Created by msinfo on 09/02/23.
 */


public class ModelGuide {
    public boolean main;

    public ModelGuide() {

    }

    public ShowcaseView showGuide(Activity activity, View view, View.OnClickListener listener, String title, String msg) {
        ShowcaseView showcaseView = new ShowcaseView.Builder(activity)
                .setTarget(new ViewTarget(view))
                .setContentTitle(title)
                .setContentText(msg)
                .setStyle(R.style.ShowcaseTheme)
                .hideOnTouchOutside()
                .setOnClickListener(listener)
                .build();

        return showcaseView;
    }
}
