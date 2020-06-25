package com.amanaggarwal1.dothemath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class CountdownGameActivity extends AppCompatActivity {

    //Declaring Views
    private ConstraintLayout gameLayout;
    private TextView queryTV, scoreTV, timerTV;
    private GridLayout choicesGrid;
    private Button[] choiceGridButton;
    private Button pauseGame;
    SetUpQuery setUpQuery;

    private int operandUpperBound = 50; //Upper bound of numbers to be added.
    private int numberOfOperations = 1; //Number of operations to be performed in every query.
    private int totalGameTime = 30; //Total time for 1 round of game
    private int bufferTime = 400; // Buffer time to reduce lag

    private int score = 0; // Number of queries answered correctly by user.
    private int numberOfQueries = 0; // Number of queries encountered by user.

    // Function gets called when any of the choice button is tapped by user
    public void answerSelected(View view){
        score = setUpQuery.verifyAnswer(Integer.parseInt(view.getTag().toString()));
        numberOfQueries++;
        scoreTV.setText("Score : " + score + " / " + numberOfQueries);
        setUpQuery.updateQuery();
    }

    public void gamePause(View view){
        score = 0;
        numberOfQueries = 0;
        startGameCounter();
        pauseGame.setAlpha(0.4f);
        pauseGame.setEnabled(false);
        scoreTV.setText("Score : Nil");
        setUpQuery = new SetUpQuery(
                gameLayout, queryTV, choicesGrid, choiceGridButton, operandUpperBound, numberOfOperations);
        setUpQuery.updateQuery();
        startGameCounter();

    }

    public void startGameCounter(){
        new CountDownTimer(totalGameTime * 1000 + bufferTime, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTV.setText("Time Left : " + (millisUntilFinished)/1000);
            }

            @Override
            public void onFinish() {
                pauseGame.setAlpha(1);
                pauseGame.setEnabled(true);

                finish();
                Intent intent = new Intent(CountdownGameActivity.this, PopUpActivity.class);
                startActivity(intent);
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Linking Views
        gameLayout = findViewById(R.id.countDownGameModeConstraintLayout);
        queryTV = findViewById(R.id.queryTV);
        scoreTV = findViewById(R.id.scoreTV);
        timerTV = findViewById(R.id.TimeLeftTV);
        choicesGrid = findViewById(R.id.choicesGridLayout);
        pauseGame = findViewById(R.id.endGameButton);

        choiceGridButton = new Button[choicesGrid.getRowCount() * choicesGrid.getColumnCount()];

        choiceGridButton[0] = findViewById(R.id.choice0Button);
        choiceGridButton[1] = findViewById(R.id.choice1Button);
        choiceGridButton[2] = findViewById(R.id.choice2Button);
        choiceGridButton[3] = findViewById(R.id.choice3Button);

        gamePause(queryTV);
    }
}
