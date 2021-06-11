package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Language extends AppCompatActivity {

    SharedPreferences appPreference = null;

    Button backBtn;
    Button english;
    Button chineseTw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        appPreference = getSharedPreferences("com.example.mindit",MODE_PRIVATE);

        backBtn = (Button)findViewById(R.id.language_back);
        english = (Button)findViewById(R.id.language_english_english);
        chineseTw = (Button)findViewById(R.id.language_chinese_taiwan);

        switch(appPreference.getInt("lang",0)){
            case 0:
                english.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                english.setTextColor(getResources().getColor(R.color.white));
                chineseTw.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                chineseTw.setTextColor(getResources().getColor(R.color.black));break;
            case 1:
                english.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
                english.setTextColor(getResources().getColor(R.color.black));
                chineseTw.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
                chineseTw.setTextColor(getResources().getColor(R.color.white));break;
        }
        setLanguage();

        backBtn.setOnClickListener((v)->finish());
        english.setOnClickListener((v)->{
            english.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            english.setTextColor(getResources().getColor(R.color.white));
            chineseTw.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            chineseTw.setTextColor(getResources().getColor(R.color.black));
            appPreference.edit().putInt("lang",0).apply();
            setLanguage();
        });
        chineseTw.setOnClickListener((v)->{
            english.setBackground(getResources().getDrawable(R.drawable.rounded_edittext));
            english.setTextColor(getResources().getColor(R.color.black));
            chineseTw.setBackground(getResources().getDrawable(R.drawable.rounded_edittext_blue));
            chineseTw.setTextColor(getResources().getColor(R.color.white));
            appPreference.edit().putInt("lang",1).apply();
            setLanguage();
        });
    }

    void setLanguage(){
        TextView header = (TextView)findViewById(R.id.language_header);
        switch (appPreference.getInt("lang",0)){
            case 0:
                backBtn.setText(getResources().getString(R.string.en_en_returnBtn));
                header.setText(getResources().getString(R.string.en_en_language_header));break;
            case 1:
                backBtn.setText(getResources().getString(R.string.ch_tw_returnBtn));
                header.setText(getResources().getString(R.string.ch_tw_language_header));break;
        }
    }
}