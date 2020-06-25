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
    private SetUpQuery setUpQuery;

    //Total time for 1 round of game
    int totalGameTime = 30;
    private int bufferTime = 400; // Buffer time to reduce lag
    private int secondsLeft;

    private CountDownTimer countDownTimer;

    private int score = 0; // Number of queries answered correctly by user.
    private int numberOfQueries = 0; // Number of queries encountered by user.

    private void setUpTime(int totalGameTime){
        secondsLeft = totalGameTime;
    }

    // Function gets called when any of the choice button is tapped by user
    public void answerSelected(View view){
        score += setUpQuery.verifyAnswer(Integer.parseInt(view.getTag().toString()));
        numberOfQueries++;
        scoreTV.setText("Score : " + score + " / " + numberOfQueries);
        setUpQuery.updateQuery();
    }

    public void gamePause(View view){
        Intent intent = new Intent(this, PopUpActivity.class);
        intent.putExtra("GameStatus", "GAME PAUSED");
        intent.putExtra("Score", score);
        intent.putExtra("NumberOfQueries", numberOfQueries);
        intent.putExtra("TimeLeft", "Time Left : " + secondsLeft + " seconds");
        countDownTimer.cancel();
        startActivityForResult(intent, 1);
    }

    private void gameResume(){
        setUpQuery.updateQuery();
        startGameCounter();
    }

    private void resetGame(){
        score = 0;
        numberOfQueries = 0;
        scoreTV.setText("Nil");
        secondsLeft = totalGameTime;
        gameResume();
    }

    public void startGameCounter(){
        countDownTimer = new CountDownTimer(secondsLeft * 1000 + bufferTime, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                secondsLeft = (int) millisUntilFinished / 1000 ;
                timerTV.setText("Time Left : " + secondsLeft + " s");

            }

            @Override
            public void onFinish() {
                pauseGame.setAlpha(1);
                pauseGame.setEnabled(true);

                Intent intent = new Intent(CountdownGameActivity.this, PopUpActivity.class);
                intent.putExtra("GameStatus", "GAME OVER");
                intent.putExtra("Score", score);
                intent.putExtra("NumberOfQueries", numberOfQueries);
                startActivityForResult(intent, 0);
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == 0)
            resetGame();
        else if(resultCode == 1)
            gameResume();
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

        int operandUpperBound = 50; //Upper bound of numbers to be added.
        int numberOfOperations = 1; //Number of operations to be performed in every query.

        setUpQuery = new SetUpQuery(
                gameLayout, queryTV, choicesGrid, choiceGridButton, operandUpperBound, numberOfOperations);

        setUpTime(totalGameTime);
        gameResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LOGCAT", "inpause");
        countDownTimer.cancel();
    }

    @Override
    protected void onRestart() {
        Log.d("LOGCAT", "onrestart");
        super.onRestart();
        gamePause(gameLayout);
    }


    @Override
    protected void onStop() {
        Log.d("LOGCAT", "onstop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("LOGCAT" , "onDestroy");
        super.onDestroy();
    }


}
