package com.example.mindit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Welcome_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        Button beginBtn = (Button) findViewById(R.id.welcome_page_beginBtn);

        beginBtn.setOnClickListener((v) -> {
            Intent intent = new Intent(this, New_task.class);
            startActivity(intent);
        });
    }

}