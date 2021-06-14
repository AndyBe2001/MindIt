package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    SharedPreferences appPreference;
    SharedPreferences userContent;

    Button backBtn;
    EditText registerID;
    EditText registerPW;
    EditText registerPW_confirm;
    EditText register_question;
    EditText register_answer;
    Button register;
    boolean password_ok = false;
    boolean register_complete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        appPreference = getSharedPreferences("com.example.mindit",MODE_PRIVATE);

        backBtn = (Button)findViewById(R.id.register_back);
        registerID = (EditText) findViewById(R.id.register_userID);
        registerPW = (EditText) findViewById(R.id.register_userPW);
        registerPW_confirm = (EditText)findViewById(R.id.register_userPW_confirm);
        register_question = (EditText)findViewById(R.id.register_forgot_question);
        register_answer = (EditText)findViewById(R.id.register_forgot_answer);
        register = (Button)findViewById(R.id.register_register);

        backBtn.setOnClickListener((v)->finish());
        registerPW_confirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(registerPW.getText().toString().equals(registerPW_confirm.getText().toString())){
                    registerPW.setTextColor(getResources().getColor(R.color.black));
                    registerPW_confirm.setTextColor(getResources().getColor(R.color.black));
                    password_ok = true;
                }
                else{
                    registerPW.setTextColor(getResources().getColor(R.color.red));
                    registerPW_confirm.setTextColor(getResources().getColor(R.color.red));
                    password_ok = false;
                }
            }
        });
        register.setOnClickListener((v)->{
            register_complete = true;
            if(registerID.getText().toString().matches("")){
                switch (appPreference.getInt("lang",0)) {
                    case 0:Toast.makeText(getApplicationContext(), "User ID unset", Toast.LENGTH_SHORT).show();break;
                    case 1:Toast.makeText(getApplicationContext(),"帳號ID未設",Toast.LENGTH_SHORT).show();break;
                }
                register_complete = false;
            }
            if(registerPW.getText().toString().matches("")||(!password_ok)){
                switch (appPreference.getInt("lang",0)) {
                    case 0:Toast.makeText(getApplicationContext(), "Password unset well", Toast.LENGTH_SHORT).show();break;
                    case 1:Toast.makeText(getApplicationContext(),"密碼未設好",Toast.LENGTH_SHORT).show();break;
                }
                register_complete = false;
            }
            if(registerPW_confirm.getText().toString().matches("")||(!password_ok)){
                switch (appPreference.getInt("lang",0)) {
                    case 0:Toast.makeText(getApplicationContext(), "Password Nonidentical", Toast.LENGTH_SHORT).show();break;
                    case 1:Toast.makeText(getApplicationContext(),"密碼未同",Toast.LENGTH_SHORT).show();break;
                }
                register_complete = false;
            }
            if(register_question.getText().toString().matches("")){
                switch (appPreference.getInt("lang",0)) {
                    case 0:Toast.makeText(getApplicationContext(), "Question Unset", Toast.LENGTH_SHORT).show();break;
                    case 1:Toast.makeText(getApplicationContext(),"問題未設",Toast.LENGTH_SHORT).show();break;
                }
                register_complete = false;
            }
            if(register_answer.getText().toString().matches("")){
                switch (appPreference.getInt("lang",0)) {
                    case 0:Toast.makeText(getApplicationContext(), "Answer Unset", Toast.LENGTH_SHORT).show();break;
                    case 1:Toast.makeText(getApplicationContext(),"問題答案未設",Toast.LENGTH_SHORT).show();break;
                }
                register_complete = false;
            }
            if(register_complete){
                appPreference.edit().putInt("numUser",appPreference.getInt("numUser",0)+1).apply();
                userContent = getSharedPreferences("com.example.mindit.user"+appPreference.getInt("numUser",0),MODE_PRIVATE);
                userContent.edit()
                        .putString("ID",registerID.getText().toString())
                        .putString("PW",registerPW.getText().toString())
                        .putString("Question",register_question.getText().toString())
                        .putString("Answer",register_answer.getText().toString())
                        .apply();
                finish();
            }
        });

        setLanguage();
    }

    void setLanguage(){
        TextView header = (TextView)findViewById(R.id.register_header);
        switch (appPreference.getInt("lang",0)){
            case 0:
                backBtn.setText(getResources().getString(R.string.en_en_cancelBtn));
                header.setText(getResources().getString(R.string.en_en_register_header));
                registerID.setHint(getResources().getString(R.string.en_en_register_userID));
                registerPW.setHint(getResources().getString(R.string.en_en_register_password));
                registerPW_confirm.setHint(getResources().getString(R.string.en_en_register_password_confirm));
                register_question.setHint(getResources().getString(R.string.en_en_register_forgot_question));
                register_answer.setHint(getResources().getString(R.string.en_en_register_forgot_answer));
                register.setText(getResources().getString(R.string.en_en_register_register));break;
            case 1:
                backBtn.setText(getResources().getString(R.string.ch_tw_cancelBtn));
                header.setText(getResources().getString(R.string.ch_tw_register_header));
                registerID.setHint(getResources().getString(R.string.ch_tw_register_userID));
                registerPW.setHint(getResources().getString(R.string.ch_tw_register_password));
                registerPW_confirm.setHint(getResources().getString(R.string.ch_tw_register_password_confirm));
                register_question.setHint(getResources().getString(R.string.ch_tw_register_forgot_question));
                register_answer.setHint(getResources().getString(R.string.ch_tw_register_forgot_answer));
                register.setText(getResources().getString(R.string.ch_tw_register_register));break;
        }
    }
}