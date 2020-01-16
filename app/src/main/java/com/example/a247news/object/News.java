package com.example.a247news.object;

public class News {
    private String country;
    private String apiKey;
    private String source;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "News{" +
                "country='" + country + '\'' +
                ", apiKey='" + apiKey + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
