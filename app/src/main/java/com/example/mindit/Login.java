package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    SharedPreferences appPreference = null;
    SharedPreferences userContent = null;

    Button backBtn;
    EditText loginID;
    EditText loginPW;
    Button forgotBtn;
    Button registerBtn;
    Button login;

    boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appPreference = getSharedPreferences("com.example.mindit",MODE_PRIVATE);

        backBtn = (Button)findViewById(R.id.login_back);
        loginID = (EditText)findViewById(R.id.login_loginID);
        loginPW = (EditText)findViewById(R.id.login_loginPW);
        forgotBtn = (Button)findViewById(R.id.login_forgot);
        registerBtn = (Button)findViewById(R.id.login_register);
        login = (Button)findViewById(R.id.login_login);

        backBtn.setOnClickListener((v)->finish());
        forgotBtn.setOnClickListener((v)->{
            Intent intent = new Intent(this,Forgot.class);
            startActivity(intent);
        });
        registerBtn.setOnClickListener((v)->{
            Intent intent = new Intent(this,Register.class);
            startActivity(intent);
        });
        login.setOnClickListener((v)->{
            if(appPreference.getInt("numUser",0)>0){
                String inputID = loginID.getText().toString();
                String inputPW = loginPW.getText().toString();
                for(int i=1;i<=appPreference.getInt("numUser",0);i++){
                    userContent = getSharedPreferences("com.example.mindit.user"+i,MODE_PRIVATE);
                    if(inputID.equals(userContent.getString("ID","")) && inputPW.equals(userContent.getString("Password",""))){
                        appPreference.edit().putInt("isUser",0).apply();
                        loggedIn = true;
                    }
                }
                if(loggedIn){
                    finish();
                }
                else{
                    switch (appPreference.getInt("lang",0)){
                        case 0:Toast.makeText(getApplicationContext(),"UserID or Password Incorrect",Toast.LENGTH_SHORT).show();break;
                        case 1:Toast.makeText(getApplicationContext(),"帳號ID或密碼錯誤",Toast.LENGTH_SHORT).show();break;
                    }
                }
            }
            else{
                switch(appPreference.getInt("lang",0)){
                    case 0:Toast.makeText(getApplicationContext(),"No User Registered",Toast.LENGTH_SHORT).show();break;
                    case 1:Toast.makeText(getApplicationContext(),"本機無使用者",Toast.LENGTH_SHORT).show();break;
                }
            }
        });
    }
}