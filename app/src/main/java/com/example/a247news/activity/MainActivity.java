package com.example.a247news.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.a247news.R;
import com.example.a247news.adapter.SectionsPagerAdapter;
import com.example.a247news.fragment.HeadlineNewsFragment;
import com.example.a247news.fragment.NewsFilterFragment;
import com.example.a247news.fragment.ProfileFragment;
import com.example.a247news.object.News;
import com.example.a247news.object.response.FilteredHeadlinesResponseEvent;
import com.example.a247news.object.response.SourcesResponseEvent;
import com.example.a247news.object.response.TopHeadlinesResponseEvent;
import com.example.a247news.service.RequestType;
import com.example.a247news.util.Constant;
import com.example.a247news.util.SessionManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements NewsFilterFragment.OnNewsFilterInteractionListener{

    private static final String TAG = MainActivity.class.getSimpleName() ;
    private ArrayList<Fragment> mFragments;
    private HeadlineNewsFragment mHeadlineNewsFragment;
    private NewsFilterFragment mNewsFilterFragment;
    private ProfileFragment mProfileFragment;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private SessionManager mSessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        mFragments = new ArrayList<>();
        mSessionManager = SessionManager.getInstance();

        mFragments.add(HeadlineNewsFragment.newInstance(0));
        mFragments.add(NewsFilterFragment.newInstance(0));
        mFragments.add(ProfileFragment.newInstance(0));

        mSectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), mFragments);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        getSelectedFragment(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                getSelectedFragment(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


    /**
     * @param event get top headlines
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(TopHeadlinesResponseEvent event) {
        Log.d(TAG, "onEvent: TopHeadlinesResponseEvent "+event.getArticles().get(0).toString());

        if (mHeadlineNewsFragment != null){
            mHeadlineNewsFragment.setDataToFragment(event.getArticles());
        }
        showLoadingSpinner(false);
    }

    /**
     * @param event get sources
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SourcesResponseEvent event) {
        Log.d(TAG, "onEvent: SourcesResponseEvent "+event.getSources().get(0).toString());

        showLoadingSpinner(false);
        if (mNewsFilterFragment != null){
            mNewsFilterFragment.setSourcesToFragment(event.getSources());
        }

    }

    /**
     * @param event get top headlines
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(FilteredHeadlinesResponseEvent event) {
        Log.d(TAG, "onEvent: TopHeadlinesResponseEvent "+event.getArticles().get(0).toString());

        if (mNewsFilterFragment != null){
            mNewsFilterFragment.setDataToFragment(event.getArticles());
        }
        showLoadingSpinner(false);
    }



    private void getSelectedFragment(int i){

        News news = new News();
        news.setApiKey(Constant.API_KEY);

        if(mSectionsPagerAdapter.getItem(i) instanceof HeadlineNewsFragment){
            news.setCountry("us");
            apiCall(RequestType.topHeadLines, news);
            mHeadlineNewsFragment = (HeadlineNewsFragment) mSectionsPagerAdapter.getItem(i);
        }

        if(mSectionsPagerAdapter.getItem(i) instanceof NewsFilterFragment){
            apiCall(RequestType.sources, news);
            mNewsFilterFragment = (NewsFilterFragment) mSectionsPagerAdapter.getItem(i);
        }

        if(mSectionsPagerAdapter.getItem(i) instanceof ProfileFragment){
            mProfileFragment = (ProfileFragment) mSectionsPagerAdapter.getItem(i);
            mProfileFragment.setDataToFragment(mSessionManager.getUerInfo());
        }


    }

    @Override
    public void onSourceClicked(String sourceName) {
        News news = new News();
        news.setSource(sourceName);
        news.setApiKey(Constant.API_KEY);
        apiCall(RequestType.filteredHeadLines, news);
    }
}