package com.example.a247news.service;

import com.example.a247news.object.response.FilteredHeadlinesResponseEvent;
import com.example.a247news.object.response.SourcesResponseEvent;
import com.example.a247news.object.response.TopHeadlinesResponseEvent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AppApi {

    @GET("top-headlines")
    Call<TopHeadlinesResponseEvent> getAllNewsHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey);
    @GET("sources")
    Call<SourcesResponseEvent> getSources(
            @Query("apiKey") String apiKey);
    @GET("top-headlines")
    Call<FilteredHeadlinesResponseEvent> getNewsBySource(
            @Query("sources") String sources,
            @Query("apiKey") String apiKey);


}
