package com.example.a247news.util;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.example.a247news.R;

public class Loading extends Dialog {

    public Loading(Context context) {
        super(context);
        this.setContentView(R.layout.dialog_loading);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.setCancelable(false);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(this.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

        this.getWindow().setAttributes(layoutParams);
    }
}
