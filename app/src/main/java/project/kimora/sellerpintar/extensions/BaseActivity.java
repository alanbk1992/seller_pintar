package project.kimora.sellerpintar.extensions;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.Dialog;

/**
 * Created by msinfo on 09/02/23.
 */


//Activity basic/dasar untuk activity extend
public class BaseActivity extends AppCompatActivity {
    public Dialog dialog;
    public boolean enableTouchDissmissKeyboard = true;
    protected boolean autoFinishActivity = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new Dialog(this);
    }

    //Setup toolbar
    protected Toolbar setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    //Setup backtoolbar
    protected Toolbar setBackToolbar() {
        Toolbar toolbar = setToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        return toolbar;
    }


    //Handle click view button toolbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //Handle click layar untuk dissmis keyboard yang sedang aktif
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //Set dismiss keyboard when touch outside
        View view = getCurrentFocus();
        Boolean ret = super.dispatchTouchEvent(event);

        if (view instanceof EditText && enableTouchDissmissKeyboard) {
            view = getCurrentFocus();
            int[] scrcoords = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = event.getRawX() + view.getLeft() - scrcoords[0];
            float y = event.getRawY() + view.getTop() - scrcoords[1];

            if (event.getAction() == MotionEvent.ACTION_UP && (x < view.getLeft() || x >= view.getRight()
                    || y < view.getTop() || y > view.getBottom())) {
                InputMethodManager imm = ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE));
                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        }
        return ret;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Constants.REQUEST_LOGOUT == true && autoFinishActivity) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dialog.dismiss();
        dialog.dismissAlert();
    }
}
