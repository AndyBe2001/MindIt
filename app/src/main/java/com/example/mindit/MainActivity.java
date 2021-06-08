package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("com.example.mindit",MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(prefs.getBoolean("firstRun",true)){
            Intent myIntent = new Intent(this,Welcome_Page.class);
            startActivity(myIntent);
            prefs.edit().putBoolean("firstRun",false).commit();
        }
    }
}