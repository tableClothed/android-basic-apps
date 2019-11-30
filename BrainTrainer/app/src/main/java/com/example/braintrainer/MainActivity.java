package com.example.braintrainer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ArrayList<Integer> answers = new ArrayList<Integer>();
    Random rand;
    int rightResult;
    int correctAnswerLocation;
    int score = 0;
    int rounds = 0;
    TextView calcText;
    TextView resultText;
    TextView scoreView;
    TextView timerText;
    Button A, B, C, D;
    Button startButton;
    ConstraintLayout gameLayout;
    Button playAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calcText = findViewById(R.id.calculationText);

        A = findViewById(R.id.buttonA);
        B = findViewById(R.id.buttonB);
        C = findViewById(R.id.buttonC);
        D = findViewById(R.id.buttonD);

        startButton = findViewById(R.id.goButton);
        startButton.setVisibility(View.VISIBLE);

        gameLayout = findViewById(R.id.gameLayout);
        gameLayout.setVisibility(View.INVISIBLE);

    }

    public void playAgain() {
        score = 0;
        rounds = 0;
        correctAnswerLocation = 0;
        rightResult = 0;

        nextQuestion();
        playAgainButton.setVisibility(View.INVISIBLE);
        resultText.setVisibility(View.INVISIBLE);

    }

    private void nextQuestion() {
        rand = new Random();
        randomizeCalculation();

        correctAnswerLocation = rand.nextInt(4);

        answers.clear();

        for (int i = 0; i < 4; i++) {
            if (i == correctAnswerLocation)
                answers.add(rightResult);
            else {
                int wrngAnswer = rand.nextInt(41);

                while (wrngAnswer == rightResult)
                    wrngAnswer = rand.nextInt(21);

                answers.add(wrngAnswer);
            }
        }

        A.setText(Integer.toString(answers.get(0)));
        B.setText(Integer.toString(answers.get(1)));
        C.setText(Integer.toString(answers.get(2)));
        D.setText(Integer.toString(answers.get(3)));
    }

    private void randomizeCalculation() {
        int a = rand.nextInt(21);
        int b = rand.nextInt(21);

        calcText.setText(Integer.toString(a) + " + " + Integer.toString(b));

        rightResult = a + b;
    }

    public void chooseAnswer(View view) {
        resultText = findViewById(R.id.resultTextView);
        scoreView = findViewById(R.id.scoreText);
        resultText.setVisibility(View.INVISIBLE);

        if (Integer.toString(correctAnswerLocation).equals(view.getTag().toString())) {
            resultText.setText("DOBRA ODPOWIEDŹ");
            score++;
            nextQuestion();
        } else {
            resultText.setText("ZLA ODPOWIEDŹ");
        }

        rounds++;

        scoreView.setText(Integer.toString(score) + "/" + Integer.toString(rounds));
    }

    public void start(View view) {

        startButton.setVisibility(View.INVISIBLE);
        gameLayout.setVisibility(View.VISIBLE);
        timerText = findViewById(R.id.secondsText);
        resultText = findViewById(R.id.resultTextView);
        playAgainButton = findViewById(R.id.playAgainButton);

        nextQuestion();
        createTimer();
    }

    public  void createTimer() {
        new CountDownTimer(30100, 1000) {
            @Override
            public void onTick(long l) {
                timerText.setText(String.valueOf(l / 1000) + "s");
            }

            @Override
            public void onFinish() {

                resultText.setText("KONIEC CZASU!");
//                findViewById(R.id.gridLayout).setVisibility(View.INVISIBLE);
//                calcText.setVisibility(View.INVISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        };
    }
}
