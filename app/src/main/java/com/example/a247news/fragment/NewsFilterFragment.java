package com.example.a247news.fragment;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.a247news.R;
import com.example.a247news.activity.NewsDetailsActivity;
import com.example.a247news.adapter.HeadlinesAdapter;
import com.example.a247news.interfaces.AdapterOnItemClickListener;
import com.example.a247news.interfaces.AdapterOnItemClickListenerEvent;
import com.example.a247news.object.Article;
import com.example.a247news.object.Source;
import com.example.a247news.ui.main.PageViewModel;
import com.example.a247news.util.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFilterFragment extends BaseFragment implements AdapterOnItemClickListener {

    private static final String TAG = NewsFilterFragment.class.getName();

    @BindView(R.id.sp_location)
    Spinner mSpinnerTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private HeadlinesAdapter mAdapter;
    private ArrayList<Source> mSource;
    private ArrayList<Article> mArticles;
    private OnNewsFilterInteractionListener mListener;

    private static final String ARG_SECTION_NUMBER = "section_number";

    public static NewsFilterFragment newInstance(int index) {
        NewsFilterFragment fragment = new NewsFilterFragment();
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
        View root = inflater.inflate(R.layout.fragment_news_filter, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        init();
        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new HeadlinesAdapter(getActivity());
        mAdapter.setOnClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

        mSpinnerTitle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Log.d(TAG, "onItemSelected: position"+position);
                if (mListener != null){
                    mListener.onSourceClicked(mSource.get(position).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnNewsFilterInteractionListener) {
            mListener = (OnNewsFilterInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnNewsFilterInteractionListener {
        void onSourceClicked(String sourceName);
    }

    @Override
    public void onItemClick(AdapterOnItemClickListenerEvent event) {
        Article article = (Article) event.getObject();
        Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
        intent.putExtra(Constant.IMAGE_URL,article.getUrl());
        startActivity(intent);
    }

    public void setSourcesToFragment(Object data) {
        mSource = (ArrayList<Source>) data;

        if (mSource == null) return;
        ArrayList<String> sourceList = new ArrayList<>();

        for (Source source : mSource) {
           sourceList.add(source.getName());
        }

        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, sourceList);
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTitle.setAdapter(modelAdapter);

        if (mListener != null){
            mListener.onSourceClicked(mSource.get(0).getId());
        }
    }

    public void setDataToFragment(Object data) {
        mArticles = (ArrayList<Article>) data;
        mAdapter.setDataToAdapter(mArticles);
    }

}