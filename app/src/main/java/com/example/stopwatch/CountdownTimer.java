package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class CountdownTimer extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 600000;
    private TextView textView;
    private Button button_start_pause,
                   button_reset,
                   button_back_to_stopwatch;

    private CountDownTimer countDownTimer;
    private boolean TimeRunning;
    private long TimeLeftInMillis = START_TIME_IN_MILLIS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_timer);

        textView = findViewById(R.id.text_view_countdown);
        button_start_pause = findViewById(R.id.button_start_pause);
        button_reset = findViewById(R.id.button_reset);
        button_back_to_stopwatch = findViewById(R.id.button_back_to_stopwatch);


        button_start_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TimeRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
        });

        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetTimer();
            }
        });

        button_back_to_stopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CountdownTimer.this, StopWatch.class);
                startActivity(intent);

            }
        });

      updateCountDownText();

    }

private void startTimer(){
        countDownTimer = new CountDownTimer(TimeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                TimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                TimeRunning = false;
                button_start_pause.setText("Start");
                button_start_pause.setVisibility(View.INVISIBLE);
                button_reset.setVisibility(View.VISIBLE);

            }
        }.start();

        TimeRunning = true;
        button_start_pause.setText("pause");
        button_reset.setVisibility(View.INVISIBLE);
}
private void pauseTimer(){
     countDownTimer.cancel();
     TimeRunning = false;
     button_start_pause.setText("Start");
     button_reset.setVisibility(View.VISIBLE);
}

private void resetTimer(){
        TimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        button_reset.setVisibility(View.INVISIBLE);
        button_start_pause.setVisibility(View.VISIBLE);

}

private void updateCountDownText(){
        int minutes = (int) (TimeLeftInMillis/1000) / 60;
        int second = (int) (TimeLeftInMillis/1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d",minutes,second);
        textView.setText(timeLeftFormatted);
}

    }
