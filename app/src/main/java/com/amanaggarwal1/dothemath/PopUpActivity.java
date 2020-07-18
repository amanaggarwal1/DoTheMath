package com.amanaggarwal1.dothemath;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PopUpActivity extends AppCompatActivity {

    private TextView gameStatusTV, timeLeftTV, scoreTV, accuracyTV;
    private Button continueButton;

    public void continueGame(View view) {
        setResult(1);
        finish();
    }

    public void restartGame(View view){
        setResult(0);
        finish();
    }

    public void goToMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    private void updateViewsForCountdown(){
        int timeLeft = getIntent().getIntExtra("Time", 0);
        int score = getIntent().getIntExtra("Score", 0);
        int numberOfQueries = getIntent().getIntExtra("NumberOfQueries", 0);

        float accuracy = -1;
        if(numberOfQueries > 0)
            accuracy = (float) score * 100 / numberOfQueries;

        timeLeftTV.setText("Time Left : " + timeLeft + " seconds");

        scoreTV.setText("Score : " + score + " / " + numberOfQueries);

        if(accuracy != -1)
            accuracyTV.setText("Accuracy :" + String.format( "%.2f" , accuracy) + "%");
        else
            accuracyTV.setText("Accuracy : Nil");
    }

    private void updateViews(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = (int) ( displayMetrics.widthPixels * 0.9 );
        int height = (int) (displayMetrics.heightPixels * 0.8 );
        getWindow().setLayout( width, height);

        String gameStatus = getIntent().getStringExtra("GameStatus");
        String gameMode = getIntent().getStringExtra("GameMode");

        if(gameStatus.equals("GAME PAUSED")) {
            timeLeftTV.setVisibility(View.VISIBLE);
            continueButton.setVisibility(View.VISIBLE);
        }
        else {
            timeLeftTV.setVisibility(View.INVISIBLE);
            continueButton.setVisibility(View.INVISIBLE);
        }

        gameStatusTV.setText(gameStatus);


        if(gameMode.equals("CLASSIC"))
            updateViewsForClassic();
        else if(gameMode.equals("ARCADE"))
            updateViewsForArcade();
        else if(gameMode.equals("COUNTDOWN"))
            updateViewsForCountdown();



    }

    private void updateViewsForArcade() {
        String gameStatus = getIntent().getStringExtra("GameStatus");
        int time = getIntent().getIntExtra("Time", 0);
        int score = getIntent().getIntExtra("Score", 0);
        int numberOfQueries = getIntent().getIntExtra("NumberOfQueries", 0);

        int avgTime = -1;
        if(numberOfQueries > 0)
            avgTime = time / numberOfQueries;

        timeLeftTV.setText("Total Time : " + time);
        scoreTV.setText("Score : " + score);

        if(avgTime != -1)
            accuracyTV.setText("Average time : " + avgTime + " seconds");
        else
            accuracyTV.setText("Average Time : Nil");

    }

    private void updateViewsForClassic() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        gameStatusTV = findViewById(R.id.gameStatusTV);
        scoreTV = findViewById(R.id.popUpScoreTV);
        timeLeftTV = findViewById(R.id.popUpTimeTV);
        accuracyTV = findViewById(R.id.popUpAcuracyTV);
        continueButton = findViewById(R.id.ContinueButton);

        updateViews();

    }

    @Override
    protected void onPause() {
        super.onPause();
        continueGame(gameStatusTV);
    }


}