package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Category_Selection extends AppCompatActivity {

    SharedPreferences appPreference = null;
    SharedPreferences userContent = null;
    SharedPreferences taskContent = null;

    LinearLayout categoryList;

    Button backBtn;
    Button addClassBtn;
    Button confirmBtn;

    ArrayList<String> temporaryClass = new ArrayList<>();
    ArrayList<String> temporaryCheckedClass = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_selection);

        int taskID;
        Bundle bundle = this.getIntent().getExtras();
        if(bundle != null){
            taskID = bundle.getInt("isTask");
        }
        else{
            taskID = 1;
        }

        appPreference = getSharedPreferences("com.example.mindit",MODE_PRIVATE);
        userContent = getSharedPreferences("com.example.mindit.user"+appPreference.getInt("isUser",0),MODE_PRIVATE);
        taskContent = getSharedPreferences("com.example.mindit.user"+appPreference.getInt("isUser",0)+".task"+taskID,MODE_PRIVATE);

        this.categoryList = findViewById(R.id.categorySelection_listCategory);

        backBtn = (Button)findViewById(R.id.categorySelection_back);
        addClassBtn = (Button)findViewById(R.id.categorySelection_addCategoryBtn);
        confirmBtn = (Button)findViewById(R.id.categorySelection_confirmBtn);

        backBtn.setOnClickListener((v)-> finish());

        addClassBtn.setOnClickListener((v)->{
            View cricketerView = getLayoutInflater().inflate(R.layout.check_box,categoryList,false);
            EditText checkBox_title = (EditText)cricketerView.findViewById(R.id.checkBox_title);
            switch (appPreference.getInt("lang",0)){
                case 0:checkBox_title.setHint(getResources().getString(R.string.en_en_editBox_Text));break;
                case 1:checkBox_title.setHint(getResources().getString(R.string.ch_tw_editBox_Text));break;
                default:checkBox_title.setHint("Error");
            }

            categoryList.addView(cricketerView);
        });

        confirmBtn.setOnClickListener((v)->{
            Intent intent = new Intent();
            Bundle bundle2 = new Bundle();
            bundle2.putStringArrayList("tempoCheckClass",temporaryCheckedClass);
            bundle2.putStringArrayList("tempoClass",temporaryClass);
            intent.putExtras(bundle2);
            setResult(RESULT_OK,intent);
            finish();
        });
    }
}