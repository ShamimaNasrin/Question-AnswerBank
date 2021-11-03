package com.example.diufinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class viewQuestionPdf extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question_pdf);
        webView = (WebView) findViewById(R.id.webviewID);
        webView.getSettings().setJavaScriptEnabled(true);

        String filename = getIntent().getStringExtra("filename");
        String fileUrl = getIntent().getStringExtra("fileUrl");

        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("File is openning...");

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url = "";
        try {
            url = URLEncoder.encode(fileUrl,"UTF-8");

        }catch (Exception e){

        }

        webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + url);
    }
}
