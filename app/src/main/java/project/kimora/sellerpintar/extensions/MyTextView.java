package project.kimora.sellerpintar.extensions;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

import project.kimora.sellerpintar.utils.TypeFaceProvider;

/**
 * Created by msinfo on 09/02/23.
 */

public class MyTextView extends AppCompatTextView {

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTypeface(TypeFaceProvider.getTypeFace(getContext(), "OpenSans-Regular.ttf"));
    }

}