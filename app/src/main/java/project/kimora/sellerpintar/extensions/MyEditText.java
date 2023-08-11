package project.kimora.sellerpintar.extensions;

import android.content.Context;

import androidx.appcompat.widget.AppCompatEditText;
import android.util.AttributeSet;

import project.kimora.sellerpintar.utils.TypeFaceProvider;

/**
 * Created by msinfo on 09/02/23.
 */

public class MyEditText extends AppCompatEditText {

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditText(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTypeface(TypeFaceProvider.getTypeFace(getContext(), "HelveticaNeue.ttf"));
    }

}