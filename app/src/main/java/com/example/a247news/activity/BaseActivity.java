package com.example.a247news.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.a247news.R;
import com.example.a247news.object.ApiMessage;
import com.example.a247news.service.RequestType;
import com.example.a247news.util.ContextManager;
import com.example.a247news.util.Loading;

import org.greenrobot.eventbus.EventBus;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getName();
    private Loading loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loading = new Loading(this);
    }


    /**
     * @param type = RequestType
     * @param object = data object
     */
    protected void apiCall(RequestType type, Object object){
        if (isNetworkConnected()) {
            showLoadingSpinner(true);
            ApiMessage event = new ApiMessage(type, object);
            EventBus.getDefault().post(event);
        }else {
            showLoadingSpinner(false);
            showSnackBar(getString(R.string.no_internet));
        }
    }

    /**
     * @param b = true => show Spinner
     *          b = false => hide Spinner
     */
    public void showLoadingSpinner(boolean b){
        if(b){
            loading.show();
        }else {
            if (loading != null) {
                loading.dismiss();
            }
        }
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) ContextManager.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
}

    public void showSnackBar(String message) {
        View parentLayout = findViewById(android.R.id.content);
        Snackbar snackbar = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    protected void activityToActivity(Class c){
        Intent intent = new Intent(ContextManager.getInstance(), c);
        startActivity(intent);
    }


}
