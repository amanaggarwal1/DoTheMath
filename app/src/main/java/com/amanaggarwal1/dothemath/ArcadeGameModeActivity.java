package com.amanaggarwal1.dothemath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ArcadeGameModeActivity extends AppCompatActivity {

    //Declaring Views
    private ConstraintLayout gameLayout;
    private TextView queryTV, scoreTV, timerTV;
    private GridLayout choicesGrid;
    private Button[] choiceGridButton;
    private Button pauseGame;
    private SetUpQuery setUpQuery;

    private int bufferTime = 400; // Buffer time to reduce lag
    private int timeForOneQuery = 5;
    private CountDownTimer countDownTimer;
    private int totalTime = 0;
    private int timeLeft;

    private int score = 0; // Number of queries answered correctly by user.


    // Function gets called when any of the choice button is tapped by user
    public void answerSelected(View view){
        totalTime += timeForOneQuery - timeLeft;
        if(setUpQuery.verifyAnswer(Integer.parseInt(view.getTag().toString()) ) == 0)
            gameOver();
        else{
            countDownTimer.cancel();
            score++;
            scoreTV.setText("Score : " + score);
            gameResume();
        }
    }

    public void gamePause(View view){
        Intent intent = new Intent(this, PopUpActivity.class);
        intent.putExtra("GameMode", "ARCADE");
        intent.putExtra("GameStatus", "GAME PAUSED");
        intent.putExtra("Score", score);
        intent.putExtra("NumberOfQueries", score);
        intent.putExtra("Time", totalTime);
        countDownTimer.cancel();
        startActivityForResult(intent, 1);
    }

    private void gameResume(){
        setUpQuery.updateQuery();
        startGameCounter();
    }

    private void resetGame(){
        score = 0;
        scoreTV.setText("Nil");
        gameResume();
    }

    private void gameOver(){

        Intent intent = new Intent(this, PopUpActivity.class);
        intent.putExtra("GameMode", "ARCADE");
        intent.putExtra("GameStatus", "GAME OVER");
        intent.putExtra("Score", score);
        intent.putExtra("NumberOfQueries", score + 1);
        intent.putExtra("Time", totalTime);
        startActivityForResult(intent, 0);
    }


    public void startGameCounter(){
        countDownTimer = new CountDownTimer(timeForOneQuery * 1000 + bufferTime, 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = (int) millisUntilFinished / 1000;
                timerTV.setText("Time Left : " + timeLeft + " s");

            }

            @Override
            public void onFinish() {
                pauseGame.setAlpha(1);
                pauseGame.setEnabled(true);

                gameOver();
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

        setUpQuery = new SetUpQuery(gameLayout, queryTV, choicesGrid,
                choiceGridButton, operandUpperBound, numberOfOperations);

        gameResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        gamePause(gameLayout);
    }


}