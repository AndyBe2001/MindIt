package com.example.mindit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class New_task extends AppCompatActivity {
    //Initialize Main Variable
    SharedPreferences appPreference = null;
    SharedPreferences userContent = null;
    SharedPreferences taskContent = null;
    Button backBtn;
    Button addListBtn;
    Button chooseListBtn;
    Button doneBtn;
    EditText setDate;
    LinearLayout listLayout;
    LinearLayout classLayout;
    Boolean dateCorrect = false;

    private final static int MY_REQUEST_CODE = 1;

    ArrayList<String> listContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        //Get all Shared Preference
        appPreference = getSharedPreferences("com.example.mindit",MODE_PRIVATE);
        userContent = getSharedPreferences("com.example.mindit.user"+appPreference.getInt("isUser",0),MODE_PRIVATE);
        taskContent = getSharedPreferences("com.example.mindit.user"+appPreference.getInt("isUser",0)+".task"+(userContent.getInt("numTask",0)+1),MODE_PRIVATE);
        taskContent.edit()
                .putString("title","")
                .putInt("numList",0)
                .putString("date","")
                .putInt("numClass",0)
                .apply();
        //Initialize Button
        backBtn = (Button)findViewById(R.id.newTask_backBtn);
        addListBtn = (Button)findViewById(R.id.newTask_list_addListBtn);
        chooseListBtn = (Button)findViewById(R.id.newTask_class_chooseClassBtn);
        doneBtn = (Button)findViewById(R.id.newTask_doneBtn);
        //Initialize EditText
        setDate = (EditText)findViewById(R.id.newTask_setup_timeDate);
        //Initialize Dynamic Layout
        this.listLayout = findViewById(R.id.newTask_listContent);
        this.classLayout = findViewById(R.id.newTask_classContent);
        //Back Button
        backBtn.setOnClickListener((v) -> finish());
        //Add New List
        addListBtn.setOnClickListener((v)->{
            View cricketerView = getLayoutInflater().inflate(R.layout.edit_box,listLayout,false);
            EditText editText = (EditText)cricketerView.findViewById(R.id.editBox_title);
            switch(appPreference.getInt("lang",0)){
                case 0:editText.setHint(getResources().getString(R.string.en_en_editBox_Text));break;
                case 1:editText.setHint(getResources().getString(R.string.ch_tw_editBox_Text));break;
                default:editText.setHint("Error");
            }
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    listContent.remove(editText.getText().toString());
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    listContent.add(editText.getText().toString());
                }
            });
            Button deleteBtn = (Button)cricketerView.findViewById(R.id.editBox_delete);
            deleteBtn.setOnClickListener((w)->{
                listContent.remove(editText.getText().toString());
                listLayout.removeView(cricketerView);
            });
            listLayout.addView(cricketerView);
        });
        //Go To Choose Class Activity
        chooseListBtn.setOnClickListener((v)->{
            Intent intent = new Intent(this,Category_Selection.class);
            Bundle bundle = new Bundle();
            bundle.putInt("isTask",userContent.getInt("numTask",0)+1);
            intent.putExtras(bundle);
            startActivityForResult(intent,MY_REQUEST_CODE);
        });
        //When date is changed
        setDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(checkDate(setDate.getText().toString())){
                    setDate.setTextColor(getResources().getColor(R.color.black));
                    dateCorrect = true;
                }
                else{
                    setDate.setTextColor(getResources().getColor(R.color.red));
                    dateCorrect = false;
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK){
            if(resultCode == MY_REQUEST_CODE){
                if(data!=null){

                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setLanguage();
    }

    boolean checkDate(String date){
        if(date.length()==10){
            try{
                int day = Integer.parseInt(date.substring(8,10));
                int month = Integer.parseInt(date.substring(5,7));
                int year = Integer.parseInt(date.substring(0,4));
                boolean isLeap;
                if(year/10000>0){return false;}
                if(month>12){return false;}
                if(year % 4 == 0) {
                    if( year % 100 == 0) {
                        isLeap = year % 400 == 0;
                    }
                    else {isLeap = true;}
                }
                else {isLeap = false;}
                if(isLeap){
                    if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
                        return day <= 31;
                    }
                    else if(month==4||month==6||month==9||month==11){
                        return day <= 30;
                    }
                    else return day <= 29;
                }
                else{
                    if(month==1||month==3||month==5||month==7||month==8||month==10||month==12){
                        return day <= 31;
                    }
                    else if(month==4||month==6||month==9||month==11){
                        return day <= 30;
                    }
                    else return day <= 28;
                }
            }catch(Exception e){
                return false;
            }
        }
        else{return false;}
    }

    void setLanguage(){
        TextView backText = (TextView)findViewById(R.id.newTask_backBtn);
        TextView headerText = (TextView)findViewById(R.id.newTask_header);
        EditText taskTitle = (EditText)findViewById(R.id.newTask_title);
        TextView listText = (TextView)findViewById(R.id.newTask_list_Head);
        Button listAddText = (Button)findViewById(R.id.newTask_list_addListBtn);
        TextView setupText = (TextView)findViewById(R.id.newTask_setup_head);
        EditText setupTimeText = (EditText)findViewById(R.id.newTask_setup_timeDate);
        TextView classText = (TextView)findViewById(R.id.newTask_class_head);
        Button classChooseText = (Button)findViewById(R.id.newTask_class_chooseClassBtn);
        Button doneText = (Button)findViewById(R.id.newTask_doneBtn);
        switch (appPreference.getInt("lang",0)){
            case 0:
                backText.setText(getResources().getString(R.string.en_en_cancelBtn));
                headerText.setText(getResources().getString(R.string.en_en_newTask_header));
                taskTitle.setHint(getResources().getString(R.string.en_en_newTask_title));
                listText.setText(getResources().getString(R.string.en_en_newTask_list_head));
                listAddText.setHint(getResources().getString(R.string.en_en_newTask_list_newList));
                setupText.setText(getResources().getString(R.string.en_en_newTask_startSetting));
                setupTimeText.setHint(getResources().getString(R.string.en_en_newTask_startSetting_timeDate));
                classText.setText(getResources().getString(R.string.en_en_newTask_classSetting));
                classChooseText.setHint(getResources().getString(R.string.en_en_newTask_classSetting_add));
                doneText.setText(getResources().getString(R.string.en_en_newTask_confirmTask));break;
            case 1:
                backText.setText(getResources().getString(R.string.ch_tw_cancelBtn));
                headerText.setText(getResources().getString(R.string.ch_tw_newTask_header));
                taskTitle.setHint(getResources().getString(R.string.ch_tw_newTask_title));
                listText.setText(getResources().getString(R.string.ch_tw_newTask_list_head));
                listAddText.setHint(getResources().getString(R.string.ch_tw_newTask_list_newList));
                setupText.setText(getResources().getString(R.string.ch_tw_newTask_startSetting));
                setupTimeText.setHint(getResources().getString(R.string.ch_tw_newTask_startSetting_timeDate));
                classText.setText(getResources().getString(R.string.ch_tw_newTask_classSetting));
                classChooseText.setHint(getResources().getString(R.string.ch_tw_newTask_classSetting_add));
                doneText.setText(getResources().getString(R.string.ch_tw_newTask_confirmTask));break;
        }
    }
}