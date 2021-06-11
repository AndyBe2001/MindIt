package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Menu extends AppCompatActivity {

    SharedPreferences appPreference = null;
    SharedPreferences userContent = null;

    Button backBtn;
    Button loginBtn;
    Button langBtn;
    Button appearanceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        appPreference = getSharedPreferences("com.example.mindit",MODE_PRIVATE);

        backBtn = (Button)findViewById(R.id.menu_back);
        loginBtn = (Button)findViewById(R.id.menu_userName);
        langBtn = (Button)findViewById(R.id.menu_language);
        appearanceBtn = (Button)findViewById(R.id.menu_appearance);

        backBtn.setOnClickListener((v)->finish());
        if(appPreference.getInt("isUser",0)!=0){
            loginBtn.setText(userContent.getString("name",""));
        }
        loginBtn.setOnClickListener((v)->{
            Intent intent = new Intent(this,Login.class);
            startActivity(intent);
        });
        langBtn.setOnClickListener((v)->{
            Intent intent = new Intent(this,Language.class);
            startActivity(intent);
        });
        appearanceBtn.setOnClickListener((v)-> Toast.makeText(getApplicationContext(),"Coming Soon",Toast.LENGTH_SHORT).show());
    }

    @Override
    protected void onResume() {
        super.onResume();

        userContent = getSharedPreferences("com.example.mindit.user"+appPreference.getInt("isUser",0),MODE_PRIVATE);
        setLanguage();
    }

    void setLanguage(){
        TextView header = (TextView)findViewById(R.id.menu_header);
        TextView setting = (TextView)findViewById(R.id.menu_setting);
        TextView about = (TextView)findViewById(R.id.menu_about);
        TextView version = (TextView)findViewById(R.id.menu_version);
        switch(appPreference.getInt("lang",0)){
            case 0:
                backBtn.setText(getResources().getString(R.string.en_en_returnBtn));
                header.setText(getResources().getString(R.string.en_en_menu_header));
                if(appPreference.getInt("isUser",0)==0){loginBtn.setText(getResources().getString(R.string.en_en_menu_account_disconnect));}
                else{loginBtn.setText(userContent.getString("ID",""));}
                setting.setText(getResources().getString(R.string.en_en_menu_setting_head));
                langBtn.setText(getResources().getString(R.string.en_en_menu_setting_language));
                appearanceBtn.setText(getResources().getString(R.string.en_en_menu_setting_appearance));
                about.setText(getResources().getString(R.string.en_en_menu_about_head));
                version.setText(getResources().getString(R.string.en_en_menu_about_version));break;
            case 1:
                backBtn.setText(getResources().getString(R.string.ch_tw_returnBtn));
                header.setText(getResources().getString(R.string.ch_tw_menu_header));
                if(appPreference.getInt("isUser",0)==0){loginBtn.setText(getResources().getString(R.string.ch_tw_menu_account_disconnect));}
                else{loginBtn.setText(userContent.getString("ID",""));}
                setting.setText(getResources().getString(R.string.ch_tw_menu_setting_head));
                langBtn.setText(getResources().getString(R.string.ch_tw_menu_setting_language));
                appearanceBtn.setText(getResources().getString(R.string.ch_tw_menu_setting_appearance));
                about.setText(getResources().getString(R.string.ch_tw_menu_about_head));
                version.setText(getResources().getString(R.string.ch_tw_menu_about_version));break;
        }
    }
}