package com.example.stopwatchapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CountTime extends AppCompatActivity {
    private TextView timerText;

    private final Handler handler = new Handler();//timer update
    private long startTime = 0L;
    private long elapsedTime = 0L;
    private boolean isRunning = false;

    private final Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            elapsedTime = currentTime - startTime;
//            int milliseconds = (int)(elapsedTime%1000);
            int seconds = (int) (elapsedTime / 1000);
            int minutes = seconds / 60;
            int hours = minutes / 60;
            seconds = seconds % 60;
            minutes = minutes % 60;

            @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, (int) (elapsedTime % 1000));
            timerText.setText(time);
            if (isRunning) {
                handler.postDelayed(this, 1);
            }
        }
    };


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.count_time);

        timerText = findViewById(R.id.timer_text);
        Button start = findViewById(R.id.start);
        Button stop = findViewById(R.id.stop);
        Button reset = findViewById(R.id.reset);

        start.setOnClickListener(v -> {
            if (!isRunning) {
                isRunning = true;
                startTime = System.currentTimeMillis() - elapsedTime;
                handler.post(timeRunnable);
            }
        });

        stop.setOnClickListener(v -> {
            if (isRunning) {
                isRunning = false;
                handler.removeCallbacksAndMessages(timeRunnable);
            }
        });

        reset.setOnClickListener(v -> {
            isRunning = false;
            handler.removeCallbacks(timeRunnable);
            elapsedTime = 0L;
            timerText.setText("00:00:00:00");
        });


    }
}
