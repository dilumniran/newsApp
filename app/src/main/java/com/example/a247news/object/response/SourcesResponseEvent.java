package com.example.a247news.object.response;

import com.example.a247news.object.Source;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class SourcesResponseEvent extends AppResponse {

    @SerializedName("sources")
    private ArrayList<Source> sources;

    public ArrayList<Source> getSources() {
        return sources;
    }

    public void setSources(ArrayList<Source> sources) {
        this.sources = sources;
    }

    @Override
    public String toString() {
        return "SourcesResponseEvent{" +
                "sources=" + sources +
                '}';
    }
}