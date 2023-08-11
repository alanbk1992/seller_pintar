package project.kimora.sellerpintar.extensions;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by msinfo on 14/02/23.
 */

public class ResizeAnimation extends Animation {
    int targetHeight;
    View view;
    int startHeight;

    public ResizeAnimation(View view, int targetHeight, int startHeight) {
        this.view = view;
        this.targetHeight = targetHeight;
        this.startHeight = startHeight;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        int newHeight = (int) (view.getLayoutParams().height+(targetHeight - view.getLayoutParams().height) * interpolatedTime);
        view.getLayoutParams().height = newHeight;
        view.requestLayout();
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }

    public static void resizeHideAnimation(final View view, int myHeight) {
        if(view.getVisibility() == View.VISIBLE) {
            int ANIMATION_TRANSITION_TIME = 300;
            ResizeAnimation resizeAnimation = new ResizeAnimation(view, 0, myHeight);
            resizeAnimation.setDuration(ANIMATION_TRANSITION_TIME);
            view.startAnimation(resizeAnimation);
            resizeAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {}
                @Override
                public void onAnimationEnd(Animation animation) {
                    view.setVisibility(View.INVISIBLE);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {}
            });
        }
    }
}
