package com.example.stopwatchapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetTime extends AppCompatActivity {

    private TextView timerText;
    private EditText inputMinutes, inputSeconds;
    private CountDownTimer countDownTimer;
    private boolean isTimeRunning = false;

    private long timeLeftMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_timer);

        timerText = findViewById(R.id.timer_text);
        inputMinutes = findViewById(R.id.input_minutes);
        inputSeconds = findViewById(R.id.input_seconds);
        Button startButton = findViewById(R.id.start_button);
        Button stopButton = findViewById(R.id.stop_button);
        Button setTimeButton = findViewById(R.id.set_time_button);

        updateTimerText();

        setTimeButton.setOnClickListener(v -> setTimer());

        startButton.setOnClickListener(v -> {
            if (!isTimeRunning) {
                startTimer();
            }
        });

        stopButton.setOnClickListener(v -> {
            if (isTimeRunning) {
                stopTimer();
            }
        });
    }

    private void setTimer() {
        if (!isTimeRunning) {
            String minutesText = inputMinutes.getText().toString();
            String secondsText = inputSeconds.getText().toString();

            int minutes = minutesText.isEmpty() ? 0 : Integer.parseInt(minutesText);
            int seconds = secondsText.isEmpty() ? 0 : Integer.parseInt(secondsText);

            if (seconds < 0 || seconds > 59) {
                Toast.makeText(this, "Please enter seconds between 0 and 59", Toast.LENGTH_SHORT).show();
                return;
            }

            timeLeftMillis = (minutes * 60L + seconds) * 1000;
            updateTimerText();

            inputMinutes.setText("");
            inputSeconds.setText("");
        } else {
            Toast.makeText(this, "You can't set the timer while it is running", Toast.LENGTH_SHORT).show();
        }
    }


    private void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isTimeRunning = false;
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMillis = millisUntilFinished;
                updateTimerText();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFinish() {
                isTimeRunning = false;
                timerText.setText("00:00");
                Toast.makeText(SetTime.this, "Completed", Toast.LENGTH_SHORT).show();
            }
        }.start();
        isTimeRunning = true;
    }

    private void updateTimerText() {
        int minutes = (int) (timeLeftMillis / 1000) / 60;
        int seconds = (int) (timeLeftMillis / 1000) % 60;

        @SuppressLint("DefaultLocale") String timeFormatted = String.format("%02d:%02d", minutes, seconds);
        timerText.setText(timeFormatted);
    }
}
