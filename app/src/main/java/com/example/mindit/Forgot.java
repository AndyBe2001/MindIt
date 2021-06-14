package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Forgot extends AppCompatActivity {

    SharedPreferences appPreference;
    SharedPreferences userContent;

    Button backBtn;
    EditText userID;
    TextView questionField;
    EditText answerField;
    TextView passwordField;
    boolean userID_complete = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        appPreference = getSharedPreferences("com.example.mindit",MODE_PRIVATE);

        backBtn = (Button)findViewById(R.id.forgot_back);
        userID = (EditText)findViewById(R.id.forgot_id);
        questionField = (TextView)findViewById(R.id.forgot_question_field);
        answerField = (EditText)findViewById(R.id.forgot_answer_field);
        passwordField = (TextView)findViewById(R.id.forgot_password_field);

        backBtn.setOnClickListener((v)->finish());
        userID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(appPreference.getInt("numUser",0)>0){
                    for(int i=1;i<=appPreference.getInt("numUser",0);i++){
                        userContent = getSharedPreferences("com.example.mindit.user"+i,MODE_PRIVATE);
                        if(userID.getText().toString().equals(userContent.getString("ID",""))){
                            questionField.setText(userContent.getString("Question",""));
                            switch(appPreference.getInt("lang",0)){
                                case 0:answerField.setHint(getResources().getString(R.string.en_en_forgot_answer_field_2));break;
                                case 1:answerField.setHint(getResources().getString(R.string.ch_tw_forgot_answer_field_2));break;
                            }
                            userID_complete = true;
                            break;
                        }
                        else{
                            questionField.setText("");
                            switch(appPreference.getInt("lang",0)){
                                case 0:answerField.setHint(getResources().getString(R.string.en_en_forgot_answer_field));break;
                                case 1:answerField.setHint(getResources().getString(R.string.ch_tw_forgot_answer_field));break;
                            }
                            userID_complete = false;
                        }
                    }
                }
            }
        });
        answerField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(userID_complete && answerField.getText().toString().equals(userContent.getString("Answer",""))){
                    passwordField.setText(userContent.getString("PW",""));
                }
                else{
                    passwordField.setText("");
                }
            }
        });
        setLanguage();
    }

    void setLanguage(){
        TextView header = (TextView)findViewById(R.id.forgot_header);
        TextView question_header=(TextView)findViewById(R.id.forgot_question_header);
        TextView answer_header=(TextView)findViewById(R.id.forgot_answer_header);
        TextView password_header=(TextView)findViewById(R.id.forgot_password_header);
        switch(appPreference.getInt("lang",0)){
            case 0:
                backBtn.setText(getResources().getString(R.string.en_en_cancelBtn));
                header.setText(getResources().getString(R.string.en_en_forgot_header));
                userID.setHint(getResources().getString(R.string.en_en_forgot_userID));
                question_header.setText(getResources().getString(R.string.en_en_forgot_question_header));
                questionField.setHint(getResources().getString(R.string.en_en_forgot_question_field));
                answer_header.setText(getResources().getString(R.string.en_en_forgot_answer_header));
                answerField.setHint(getResources().getString(R.string.en_en_forgot_answer_field));
                password_header.setText(getResources().getString(R.string.en_en_forgot_password_header));
                passwordField.setHint(getResources().getString(R.string.en_en_forgot_password_field));break;
            case 1:
                backBtn.setText(getResources().getString(R.string.ch_tw_cancelBtn));
                header.setText(getResources().getString(R.string.ch_tw_forgot_header));
                userID.setHint(getResources().getString(R.string.ch_tw_forgot_userID));
                question_header.setText(getResources().getString(R.string.ch_tw_forgot_question_header));
                questionField.setHint(getResources().getString(R.string.ch_tw_forgot_question_field));
                answer_header.setText(getResources().getString(R.string.ch_tw_forgot_answer_header));
                answerField.setHint(getResources().getString(R.string.ch_tw_forgot_answer_field));
                password_header.setText(getResources().getString(R.string.ch_tw_forgot_password_header));
                passwordField.setHint(getResources().getString(R.string.ch_tw_forgot_password_field));break;
        }
    }
}