package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerText;
    SeekBar seekBar;
    boolean counterIsActive = false;
    Button goButton;
    CountDownTimer countDownTimer


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerText = findViewById(R.id.timeLeft);
        seekBar = findViewById(R.id.timerSeekBar);
        goButton = findViewById(R.id.startButton);

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

    private void resetTimer() {
        timerText.setText("0:30");
        seekBar.setProgress(30);
        seekBar.setEnabled(true);
        countDownTimer.cancel();
        goButton.setText("START");
        counterIsActive = false;
    }

    public void startCounting(View view) {
        if (counterIsActive) {
            resetTimer();
        } else {
            counterIsActive = true;
            seekBar.setEnabled(false);
            goButton.setText("STOP");

            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer media = MediaPlayer.create(getApplicationContext(), R.raw.bitchwtf);
                    media.start();
                }
            }.start();
        }
    }

    public  void updateTimer(int secondsLeft) {
        int minutes = secondsLeft/60;
        int seconds = secondsLeft % 60;

        String secondString = "";

        if (seconds < 10) {
            secondString = "0" + secondsLeft;
        } else {
            secondString = Integer.toString(seconds);
        }

        if (secondString.equals("0")) {
            secondString = "00";
        }

        timerText.setText(Integer.toString(minutes) + ":" + secondString);
    }

}
