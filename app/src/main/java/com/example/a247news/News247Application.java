package com.example.a247news;

import android.app.Application;


import com.example.a247news.service.Controller;

import org.greenrobot.eventbus.EventBus;

public class News247Application extends Application {
    private static News247Application mInstance;
    private Controller mController;

    public static News247Application getInstance() {
        if(mInstance == null){
            mInstance = new News247Application();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if(mController == null){
            mController = new Controller(this);
        }
        EventBus.getDefault().register(mController);

    }

}
