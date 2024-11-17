package com.example.stopwatchapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button setTimer = findViewById(R.id.setTimer);
        Button countTimer = findViewById(R.id.countTime);



        setTimer.setOnClickListener(v -> {
          Intent  intent = new Intent(MainActivity.this,SetTime.class);
          startActivity(intent);
        });

        countTimer.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this,CountTime.class);
            startActivity(intent);
        });

    }


}