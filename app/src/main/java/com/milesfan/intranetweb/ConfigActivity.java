package com.milesfan.intranetweb;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConfigActivity extends AppCompatActivity {
    private String homeURL;
    private Integer autoRefreshInterval;

    private TextView textHomeURL;
    private TextView textAutoRefreshInterval;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);


        sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        homeURL = sharedPreferences.getString("HomeURL", "");
        autoRefreshInterval = sharedPreferences.getInt("AutoRefreshInterval", 0);

        textHomeURL = findViewById(R.id.homeurl);
        textAutoRefreshInterval = findViewById(R.id.autorefreshinterval);

        textHomeURL.setText(homeURL);
        textAutoRefreshInterval.setText(Integer.toString(autoRefreshInterval));

    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        homeURL = textHomeURL.getText().toString();
        String _autoRefreshInterval = textAutoRefreshInterval.getText().toString();
        autoRefreshInterval = _autoRefreshInterval.isEmpty()? 0: Integer.valueOf(_autoRefreshInterval);
        sharedPreferences.edit()
                .putString("HomeURL", homeURL)
                .putInt("AutoRefreshInterval", autoRefreshInterval)
                .apply();
        Intent intent=new Intent();
        setResult(1,intent);
        finish();//finishing activity

        //startActivity(new Intent(ConfigActivity.this, MainActivity.class));
    }
}
