package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar = findViewById(R.id.timerSeekBar);
    TextView timerText = findViewById(R.id.timeLeft);

    public void startCounting(View view) {
//        CountDownTimer countDownTimer = new CountDownTimer(1000, 1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                updateTimer((int) millisUntilFinished / 1000);
//            }
//
//            @Override
//            public void onFinish() {
//                timerText.setText("KONIEC ODLICZANIA");
//                MediaPlayer media = new MediaPlayer();
//            }
//        }.start();
    }

    public  void updateTimer(int secondsLeft) {
        int minutes = secondsLeft/60;
        int seconds = secondsLeft % 60;

        String secondString = Integer.toString(seconds);

        if (seconds < 10) {
            secondString = "0" + secondsLeft;
        }
        if (secondString.equals("0")) {
            secondString = "00";
        }

        timerText.setText(Integer.toString(minutes) + ":" + secondString);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
