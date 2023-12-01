// MainActivity.java
package com.example.musicassistant;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    WebView myWeb;
    boolean errorShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve input values from Intent
        String ip = getIntent().getStringExtra("IP");
        String port = getIntent().getStringExtra("Port");
        boolean isHttps = getIntent().getBooleanExtra("IsHttps", false);

        String protocol = (isHttps ? "https://" : "http://");
        String url = protocol + ip + ":" + port;

        myWeb = findViewById(R.id.myWeb);
        myWeb.getSettings().setJavaScriptEnabled(true);
        myWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // Check if the HTML title contains the desired text
                if (view.getTitle().equalsIgnoreCase("Music Assistant")) {
                    // Title matches, proceed with loading the URL
                    super.onPageFinished(view, url);
                } else {
                    // Title doesn't match, show a Toast and navigate back to WelcomeActivity
                    if (!errorShown) {
                        view.loadData("<html><body><h1>Invalid Page</h1></body></html>", "text/html", "UTF-8");
                        Toast.makeText(MainActivity.this, "There was an error with your page.", Toast.LENGTH_SHORT).show();
                        errorShown = true;

                        // Navigate back to WelcomeActivity (you can customize this based on your app's flow)
                        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });

        myWeb.loadUrl(url);

    }
}