package com.milesfan.intranetweb;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    boolean doubleBackToExitPressedOnce = false;
    private String homeURL="";
    private Integer autoRefreshInterval=0;

    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            //ShowHomeURLSetting(this, homeURL);
            //startActivity(new Intent(MainActivity.this, ConfigActivity.class));

            Intent intent=new Intent(MainActivity.this,ConfigActivity.class);
            startActivityForResult(intent, 1);// Activity is started with requestCode 2

            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWebView();
    }
    private void initWebView(){
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        homeURL = sharedPreferences.getString("HomeURL", getString(R.string.url_index));
        if (homeURL.isEmpty()) homeURL=getString(R.string.url_index);

        autoRefreshInterval = sharedPreferences.getInt("AutoRefreshInterval", 0);

        mWebView = (WebView) findViewById(R.id.webView1);
        mWebView.setWebViewClient(new MyAppWebViewClient(this, autoRefreshInterval));
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        String originalUserAgent = webSettings.getUserAgentString();
        webSettings.setUserAgentString(originalUserAgent + " MicroMessenger/6.3.22");
        mWebView.loadUrl(homeURL);
    }
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            doubleBackToExitPressedOnce = false;
            mWebView.goBack();
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            //return;
        }else {
            this.doubleBackToExitPressedOnce = true;


/*            Toast toast = Toast.makeText(this, R.string.pressbackagaintoexit, Toast.LENGTH_SHORT);
            View view = toast.getView();

//Gets the actual oval background of the Toast then sets the colour filter
            view.getBackground().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);

//Gets the TextView from the Toast so it can be editted
            TextView text = view.findViewById(android.R.id.message);
            text.setTextColor(Color.WHITE);
            //text.setText(R.string.pressbackagaintoexit);
            toast.show();*/


            Toast.makeText(this, R.string.pressbackagaintoexit, Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        //if(requestCode==1)
        //{
            initWebView();
        //}
    }
}
