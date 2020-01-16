package com.example.a247news.object.response;

import com.example.a247news.object.Article;
import com.example.a247news.object.News;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class TopHeadlinesResponseEvent extends AppResponse {

    @SerializedName("articles")
    private ArrayList<Article> articles;

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
    }

    @Override
    public String toString() {
        return "TopHeadlinesResponseEvent{" +
                "articles=" + articles +
                '}';
    }
}