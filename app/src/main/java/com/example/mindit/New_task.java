package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

public class New_task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);

        TextView backText = (TextView) findViewById(R.id.backText);
        ImageButton backBtn = (ImageButton) findViewById(R.id.backBtn);

        backText.setOnClickListener((v) -> {finish();});
        backBtn.setOnClickListener((v) ->{finish();});
    }
}