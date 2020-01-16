package com.example.a247news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a247news.R;
import com.example.a247news.activity.NewsDetailsActivity;
import com.example.a247news.adapter.HeadlinesAdapter;
import com.example.a247news.interfaces.AdapterOnItemClickListener;
import com.example.a247news.interfaces.AdapterOnItemClickListenerEvent;
import com.example.a247news.object.Article;
import com.example.a247news.ui.main.PageViewModel;
import com.example.a247news.util.Constant;

import java.util.ArrayList;

public class HeadlineNewsFragment extends BaseFragment implements AdapterOnItemClickListener {

    private static final String TAG = HeadlineNewsFragment.class.getName();

    private RecyclerView mRecyclerView;
    private HeadlinesAdapter mAdapter;
    private ArrayList<Article> mArticles;

    private static final String ARG_SECTION_NUMBER = "section_number";

    private PageViewModel pageViewModel;

    public static HeadlineNewsFragment newInstance(int index) {
        HeadlineNewsFragment fragment = new HeadlineNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: HeadlineNewsFragment");
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        mRecyclerView = getView().findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new HeadlinesAdapter(getActivity());
        mAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onItemClick(AdapterOnItemClickListenerEvent event) {
        Article article = (Article) event.getObject();
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra(Constant.IMAGE_URL,article.getUrl());
        startActivity(intent);
    }

    public void setDataToFragment(Object data) {
        mArticles = (ArrayList<Article>) data;
        mAdapter.setDataToAdapter(mArticles);
    }
}