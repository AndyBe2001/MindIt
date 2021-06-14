package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Welcome_Page extends AppCompatActivity {

    SharedPreferences appPreference = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        appPreference = getSharedPreferences("com.example.mindit",MODE_PRIVATE);

        Button beginBtn = (Button) findViewById(R.id.welcome_page_beginBtn);

        setLanguage();

        beginBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(this, New_task.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        finish();
    }

    void setLanguage(){
        TextView welcome_message = (TextView)findViewById(R.id.welcome_message);
        Button welcome_btn = (Button)findViewById(R.id.welcome_page_beginBtn);
        switch(appPreference.getInt("lang",0)){
            case 0:
                welcome_message.setText(getResources().getString(R.string.en_en_welcome_page_textview_message));
                welcome_message.setTextSize(19);
                welcome_btn.setText(getResources().getString(R.string.en_en_welcome_page_button_text));break;
            case 1:
                welcome_message.setText(getResources().getString(R.string.ch_tw_welcome_page_textview_message));
                welcome_message.setTextSize(24);
                welcome_btn.setText(getResources().getString(R.string.ch_tw_welcome_page_button_text));break;
        }
    }
}