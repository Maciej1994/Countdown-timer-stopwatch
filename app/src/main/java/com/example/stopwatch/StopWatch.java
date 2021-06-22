package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StopWatch extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;

    private Button  button_start,
            button_reset,
            button_pause,
            button_timer;


    private TextView textView;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);

        if(savedInstanceState != null){
            seconds = savedInstanceState.getInt("second");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimes();

        button_start = findViewById(R.id.button_start);
        button_reset = findViewById(R.id.button_reset);
        button_pause = findViewById(R.id.button_pause);
        button_timer = findViewById(R.id.button_timer);
        textView = findViewById(R.id.time_view);


        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = true;
            }
        });


        button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
                seconds = 0;
            }
        });

        button_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                running = false;
            }
        });

        button_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StopWatch.this, CountdownTimer.class);
                startActivity(intent);
            }
        });


    }


    private void runTimes(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format("%d:%02d:%02d", hours, minutes, secs);
                textView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

   public void onSaveInstanceState(Bundle savedInstanceState) {

       super.onSaveInstanceState(savedInstanceState);
       savedInstanceState.putInt("second", seconds);
       savedInstanceState.putBoolean("running", running);
       savedInstanceState.putBoolean("wasRunning", wasRunning);
   }

    @Override
    protected void onStop() {
        super.onStop();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(wasRunning){
            running = true;
        }
    }

}