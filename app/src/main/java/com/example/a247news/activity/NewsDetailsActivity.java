package com.example.a247news.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Spinner;

import com.example.a247news.R;
import com.example.a247news.util.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.newsWV)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        if (getIntent() != null && getIntent().getStringExtra(Constant.IMAGE_URL) != null){
            mWebView.loadUrl(getIntent().getStringExtra(Constant.IMAGE_URL));
        }
//        mWebView.loadUrl("https://www.foxnews.com/world/irans-rouhani-tehran-enriching-more-uranium-before-nuclear-deal-obama");
    }
}
