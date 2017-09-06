package com.example.christospaspalieris.educationprogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webview = new WebView(this);
        setContentView(webview);

        webview.loadUrl("http://ebooks.edu.gr/modules/ebook/show.php/DSGYM-A200/293/2066,7178/");
    }
}
