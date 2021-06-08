package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;
    LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("com.example.mindit",MODE_PRIVATE);
    }

    @Override
    protected void onResume(){
        super.onResume();

        if(prefs.getBoolean("firstRun",true)){
            Intent myIntent = new Intent(this,Welcome_Page.class);
            startActivity(myIntent);
            prefs.edit()
                    .putBoolean("firstRun",false)
                    .putInt("sortType",0)
                    .putInt("isUser",0)
                    .putInt("numTask",0)
                    .putInt("lang",0)
                    .apply();
        }
        else{
            setLanguage();

        }
    }

    void setLanguage(){
        TextView main_header = findViewById(R.id.main_header);
        TextView main_sort_date = findViewById(R.id.main_sort_date);
        TextView main_sort_class = findViewById(R.id.main_sort_class);
        switch(prefs.getInt("lang",0)){
            case 0:
                main_header.setText(R.string.en_en_main_header);
                main_sort_date.setText(R.string.en_en_main_sort_date);
                main_sort_class.setText(R.string.en_en_main_sort_class);break;
            case 1:
                main_header.setText(R.string.ch_tw_main_header);
                main_sort_date.setText(R.string.ch_tw_main_sort_date);
                main_sort_class.setText(R.string.ch_tw_main_sort_class);break;
        }
    }
}