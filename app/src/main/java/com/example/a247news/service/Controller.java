package com.example.a247news.service;

import android.content.Context;
import android.util.Log;

import com.example.a247news.object.ApiMessage;
import com.example.a247news.object.News;
import com.example.a247news.object.response.AppResponse;
import com.example.a247news.object.response.FilteredHeadlinesResponseEvent;
import com.example.a247news.object.response.SourcesResponseEvent;
import com.example.a247news.object.response.TopHeadlinesResponseEvent;
import com.example.a247news.util.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

    private static final String TAG = Controller.class.getSimpleName();
    private AppApi mApi;

    //json object
    public Controller(Context context) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        // logs
        HttpLoggingInterceptor interceptorLogger = new HttpLoggingInterceptor();
        interceptorLogger.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptorLogger).build();

        String BASE_URL = Constant.BASE_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mApi = retrofit.create(AppApi.class);

    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onEvent(ApiMessage event) {

        switch (event.getType()) {
            case topHeadLines:
                getTopHeadlines(event.getObject());
                break;
            case sources:
                getSources(event.getObject());
                break;
            case filteredHeadLines:
                getNewsBySource(event.getObject());
                break;
        }

    }

    private void getTopHeadlines(Object object) {
        Log.i(TAG, "getTopHeadlines Request =" + object.toString());
        News news = (News) object;
        mApi.getAllNewsHeadlines(news.getCountry(), news.getApiKey()).enqueue(new Callback<TopHeadlinesResponseEvent>() {
            @Override
            public void onResponse(Call<TopHeadlinesResponseEvent> call, Response<TopHeadlinesResponseEvent> response) {
                TopHeadlinesResponseEvent res = response.body();
                Log.d(TAG, res.toString());

                if (response.isSuccessful()) {
                    Log.i(TAG, "getTopHeadlines -------------> success");
                    onSuccess(res);
                } else {
                    Log.i(TAG, "getTopHeadlines -------------> fail");
                    onError(res, null);
                }
            }

            @Override
            public void onFailure(Call<TopHeadlinesResponseEvent> call, Throwable t) {
                Log.d(TAG, "getTopHeadlines on failure call =" + call.toString());
                onError(new TopHeadlinesResponseEvent(), t);
            }
        });
    }

    private void getSources(Object object) {
        Log.i(TAG, "getSource Request =" + object.toString());
        News news = (News) object;
        mApi.getSources(news.getApiKey()).enqueue(new Callback<SourcesResponseEvent>() {
            @Override
            public void onResponse(Call<SourcesResponseEvent> call, Response<SourcesResponseEvent> response) {
                SourcesResponseEvent res = response.body();
                Log.d(TAG, res.toString());

                if (response.isSuccessful()) {
                    Log.i(TAG, "getSource -------------> success");
                    onSuccess(res);
                } else {
                    Log.i(TAG, "getSource -------------> fail");
                    onError(res, null);
                }
            }

            @Override
            public void onFailure(Call<SourcesResponseEvent> call, Throwable t) {
                Log.d(TAG, "getSource on failure call =" + call.toString());
                onError(new TopHeadlinesResponseEvent(), t);
            }
        });
    }

    private void getNewsBySource(Object object) {
        Log.i(TAG, "getNewsBySource Request =" + object.toString());
        News news = (News) object;
        mApi.getNewsBySource(news.getSource(), news.getApiKey()).enqueue(new Callback<FilteredHeadlinesResponseEvent>() {
            @Override
            public void onResponse(Call<FilteredHeadlinesResponseEvent> call, Response<FilteredHeadlinesResponseEvent> response) {
                FilteredHeadlinesResponseEvent res = response.body();
                Log.d(TAG, res.toString());

                if (response.isSuccessful()) {
                    Log.i(TAG, "getNewsBySource -------------> success");
                    onSuccess(res);
                } else {
                    Log.i(TAG, "getNewsBySource -------------> fail");
                    onError(res, null);
                }
            }

            @Override
            public void onFailure(Call<FilteredHeadlinesResponseEvent> call, Throwable t) {
                Log.d(TAG, "getNewsBySource on failure call =" + call.toString());
                onError(new TopHeadlinesResponseEvent(), t);
            }
        });
    }


    /**
     * @param response Success data object
     */
    private void onSuccess(AppResponse response) {
        EventBus.getDefault().post(response);
    }

    /**
     * @param response Failed data object
     */
    private void onError(AppResponse response, Throwable throwable) {
        response.setError(0, throwable);
        EventBus.getDefault().post(response);
    }

    /**
     * @return is Internet available or not
     */

}
