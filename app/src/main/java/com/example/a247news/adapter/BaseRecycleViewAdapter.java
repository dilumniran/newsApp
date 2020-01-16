package com.example.a247news.adapter;

import android.support.v7.widget.RecyclerView;


public abstract class BaseRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public abstract void setDataToAdapter(Object data);
}
