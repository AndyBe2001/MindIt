package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    SharedPreferences appPreference = null;
    SharedPreferences userContent = null;
    SharedPreferences taskContent = null;
    Button newTaskBtn;
    Button sortDate;
    Button sortClass;
    LinearLayout contentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appPreference = getSharedPreferences("com.example.mindit",MODE_PRIVATE);
        this.contentLayout = findViewById(R.id.mainPage_content);
        newTaskBtn = (Button)findViewById(R.id.main_newtaskBtn);
        sortDate = (Button)findViewById(R.id.main_sort_date);
        sortClass = (Button)findViewById(R.id.main_sort_class);

        newTaskBtn.setOnClickListener((v)->{
            Intent intent = new Intent(this,New_task.class);
            startActivity(intent);
        });
        sortDate.setOnClickListener((v)->{
            appPreference.edit().putInt("sortType",0).apply();
            contentLayout.removeAllViews();
            displayTask();
        });
        sortClass.setOnClickListener((v)->{
            appPreference.edit().putInt("sortType",1).apply();
            contentLayout.removeAllViews();
            displayTask();
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        //Initialization again
        if(appPreference.getBoolean("firstRun",true)){ //First Time of Launch
            appPreference.edit()
                    .putBoolean("firstRun",false)
                    .putInt("sortType",0)
                    .putInt("isUser",0)
                    .putInt("numUser",0)
                    .putInt("lang",0)
                    .apply();
            userContent = getSharedPreferences("com.example.mindit.user0",MODE_PRIVATE);
            userContent.edit()
                    .putInt("numTask",0)
                    .putInt("numClass",0)
                    .apply();
            Intent myIntent = new Intent(this,Welcome_Page.class);
            startActivity(myIntent);
        }
        displayTask();
    }

    @Override
    protected void onPause() {
        super.onPause();

        contentLayout.removeAllViews();
    }

    void displayTask(){
        if(appPreference.getInt("sortType",0)==0){
            sortDate.setBackground(getResources().getDrawable(R.drawable.rounded_button));
            sortClass.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
        }
        else{
            sortClass.setBackground(getResources().getDrawable(R.drawable.rounded_button));
            sortDate.setBackground(getResources().getDrawable(R.drawable.rounded_button_unselected));
        }
        String userInfo = "com.example.mindit.user"+appPreference.getInt("isUser",0);
        userContent = getSharedPreferences(userInfo,MODE_PRIVATE);
        setLanguage();
        //Display content
        String taskID = "com.example.mindit.user"+appPreference.getInt("isUser",0)+".task";
        String bigText, buttonText;
        if(appPreference.getInt("sortType",0)==0) {
            //Display Today
            switch (appPreference.getInt("lang", 0)) {
                case 0:
                    bigText = getResources().getString(R.string.en_en_main_date_today);
                    break;
                case 1:
                    bigText = getResources().getString(R.string.ch_tw_main_date_today);
                    break;
                default:
                    bigText = "Error";
            }
            addbigText(bigText);
            //Display Today task
            String today = getCurrentDate();
            for (int i = 1; i <= userContent.getInt("numTask", 0); i++) {
                taskContent = getSharedPreferences(taskID + i, MODE_PRIVATE);
                if (today.equals(taskContent.getString("date", ""))) {
                    buttonText = taskContent.getString("taskName", "");
                    addbuttonText(buttonText);
                }
            }
            //Display Tomorrow
            switch (appPreference.getInt("lang", 0)) {
                case 0:
                    bigText = getResources().getString(R.string.en_en_main_date_tomorrow);
                    break;
                case 1:
                    bigText = getResources().getString(R.string.ch_tw_main_date_tomorrow);
                    break;
                default:
                    bigText = "Error";
            }
            addbigText(bigText);
            //Display Tomorrow task
            String tomorrow = getTomorrowDate();
            for (int i = 1; i <= userContent.getInt("numTask", 0); i++) {
                taskContent = getSharedPreferences(taskID + i, MODE_PRIVATE);
                if (tomorrow.equals(taskContent.getString("date", ""))) {
                    buttonText = taskContent.getString("taskName", "");
                    addbuttonText(buttonText);
                }
            }
            //Display Later
            switch (appPreference.getInt("lang", 0)) {
                case 0:
                    bigText = getResources().getString(R.string.en_en_main_date_later);
                    break;
                case 1:
                    bigText = getResources().getString(R.string.ch_tw_main_date_later);
                    break;
                default:
                    bigText = "Error";
            }
            addbigText(bigText);
            //Display Later task
            for (int i = 1; i <= userContent.getInt("numTask", 0); i++) {
                taskContent = getSharedPreferences(taskID + i, MODE_PRIVATE);
                String taskDate = taskContent.getString("date", "");
                if ((!taskDate.equals(today)) && (!taskDate.equals(tomorrow)) && (!taskDate.equals(""))) {
                    buttonText = taskContent.getString("taskName", "");
                    addbuttonText(buttonText);
                }
            }
            //Display Not Set
            switch(appPreference.getInt("lang",0)){
                case 0:
                    bigText = getResources().getString(R.string.en_en_main_date_notSet);
                    break;
                case 1:
                    bigText = getResources().getString(R.string.ch_tw_main_date_notSet);
                    break;
                default:
                    bigText = "Error";
            }
            addbigText(bigText);
            //Display Not Set Task
            for (int i = 1; i <= userContent.getInt("numTask", 0); i++) {
                taskContent = getSharedPreferences(taskID+i,MODE_PRIVATE);
                String taskDate = taskContent.getString("date","");
                if(!taskDate.equals("")){
                    buttonText = taskContent.getString("taskName","");
                    addbuttonText(buttonText);
                }
            }
        }
        else{
            //Display Class and its task
            for(int i = 1; i<=userContent.getInt("numClass",0);i++){
                //Display Class Name
                bigText = userContent.getString("class"+i,"");
                addbigText(bigText);
                for(int j = 1; i <= userContent.getInt("numTask",0); j++){
                    taskContent = getSharedPreferences(taskID+i,MODE_PRIVATE);
                    for(int k = 1; k<= taskContent.getInt("numClass",0); i++) {
                        if (taskContent.getString("class" + k, "").equals(userContent.getString("class" + j, ""))) {
                            buttonText = taskContent.getString("taskName", "");
                            addbuttonText(buttonText);
                        }
                    }
                }
            }
        }
    }
    void setLanguage(){
        TextView main_header = findViewById(R.id.main_header);
        TextView main_sort_date = findViewById(R.id.main_sort_date);
        TextView main_sort_class = findViewById(R.id.main_sort_class);
        switch(appPreference.getInt("lang",0)){
            case 0:
                main_header.setText(getResources().getString(R.string.en_en_main_header));
                main_sort_date.setText(getResources().getString(R.string.en_en_main_sort_date));
                main_sort_class.setText(getResources().getString(R.string.en_en_main_sort_class));break;
            case 1:
                main_header.setText(getResources().getString(R.string.ch_tw_main_header));
                main_sort_date.setText(getResources().getString(R.string.ch_tw_main_sort_date));
                main_sort_class.setText(getResources().getString(R.string.ch_tw_main_sort_class));break;
        }
    }
    String getCurrentDate(){
        Date c = Calendar.getInstance().getTime();
        String date;
        date = Integer.toString(1900+c.getYear());
        switch(c.getMonth()){
            case 0:date=date+" Jan";break;
            case 1:date=date+" Feb";break;
            case 2:date=date+" Mar";break;
            case 3:date=date+" Apr";break;
            case 4:date=date+" May";break;
            case 5:date=date+" Jun";break;
            case 6:date=date+" Jul";break;
            case 7:date=date+" Aug";break;
            case 8:date=date+" Sep";break;
            case 9:date=date+" Oct";break;
            case 10:date=date+" Nov";break;
            case 11:date=date+" Dec";break;
        }
        date=date + c.getDate();
        return date;
    }
    String getTomorrowDate(){
        Date c = Calendar.getInstance().getTime();
        String date;

        int year=1900+c.getYear();
        int month=c.getMonth();
        int day=c.getDate()+1;

        if(month==0 || month==2 || month==4 || month==6 || month==7 || month==9 || month==11){
            if(day>31){month++;day=1;}
        }
        else if(month==3 || month==5 || month==8 || month==10){
            if(day>30){month++;day=1;}
        }
        else{
            boolean isLeap;
            if(year % 4 == 0) {
                if( year % 100 == 0) {
                    isLeap = year % 400 == 0;
                }
                else {isLeap = true;}
            }
            else {isLeap = false;}
            if(isLeap){if(day>29){month++;day=1;}}
            else{if(day>28){month++;day=1;}}
        }
        if(month>11){year++;month=1;}

        date=Integer.toString(year);
        switch(month){
            case 0:date=date+" Jan";break;
            case 1:date=date+" Feb";break;
            case 2:date=date+" Mar";break;
            case 3:date=date+" Apr";break;
            case 4:date=date+" May";break;
            case 5:date=date+" Jun";break;
            case 6:date=date+" Jul";break;
            case 7:date=date+" Aug";break;
            case 8:date=date+" Sep";break;
            case 9:date=date+" Oct";break;
            case 10:date=date+" Nov";break;
            case 11:date=date+" Dec";break;
        }
        date=date + " " + (day);
        return date;
    }
    void addbigText(String title){
        View cricketerView = getLayoutInflater().inflate(R.layout.big_text,contentLayout,false);
        TextView bigText = (TextView)cricketerView.findViewById(R.id.bigText);
        bigText.setText(title);
        contentLayout.addView(cricketerView);
    }
    void addbuttonText(String text){
        View cricketerView = getLayoutInflater().inflate(R.layout.task_display,contentLayout,false);
        Button buttonText = (Button)cricketerView.findViewById(R.id.taskText);
        buttonText.setText(text);
        contentLayout.addView(cricketerView);
    }
}