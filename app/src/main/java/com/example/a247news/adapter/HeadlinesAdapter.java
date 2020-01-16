package com.example.a247news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a247news.R;
import com.example.a247news.interfaces.AdapterOnItemClickListener;
import com.example.a247news.interfaces.AdapterOnItemClickListenerEvent;
import com.example.a247news.object.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HeadlinesAdapter extends BaseRecycleViewAdapter {

    private Context mContext;
    private ArrayList<Article> mArticles;
    private static final String TAG = HeadlinesAdapter.class.getSimpleName();
    private AdapterOnItemClickListener clickListener;


    public HeadlinesAdapter(Context context) {
        super();
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.headlines_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ViewHolder viewHolder = (ViewHolder) holder;
        final Article article = mArticles.get(position);

        viewHolder.mTitle.setText(article.getTitle());

        Picasso.get()
                .load(article.getUrlToImage())
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.mImageView);

        viewHolder.mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        viewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdapterOnItemClickListenerEvent adapterOnItemClickListenerEvent = new AdapterOnItemClickListenerEvent();
                adapterOnItemClickListenerEvent.setAdapter(HeadlinesAdapter.this);
                adapterOnItemClickListenerEvent.setObject(article);
                clickListener.onItemClick(adapterOnItemClickListenerEvent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mArticles != null ? mArticles.size() : 0;
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void setDataToAdapter(Object data) {
        this.mArticles = (ArrayList<Article>) data;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mTitle;
        ImageView mImageView;

        public ViewHolder(View myView) {
            super(myView);
            mTitle = myView.findViewById(R.id.newsTV);
            mImageView = myView.findViewById(R.id.imageIV);

        }
    }

    public void setOnClickListener(AdapterOnItemClickListener listener) {
        this.clickListener = listener;
    }

}

