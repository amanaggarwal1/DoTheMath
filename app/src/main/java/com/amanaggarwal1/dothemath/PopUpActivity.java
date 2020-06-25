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

    private void updateViews(){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = (int) ( displayMetrics.widthPixels * 0.8 );
        int height = (int) (displayMetrics.heightPixels * 0.7 );
        getWindow().setLayout( width, height);

        String gameStatus = getIntent().getStringExtra("GameStatus");
        String time = getIntent().getStringExtra("TimeLeft");
        int score = getIntent().getIntExtra("Score", 0);
        int numberOfQueries = getIntent().getIntExtra("NumberOfQueries", 0);

        float accuracy = -1;
        if(numberOfQueries > 0)
            accuracy = (float) score * 100 / numberOfQueries;

        if(gameStatus.equals("GAME PAUSED")) {
            timeLeftTV.setVisibility(View.VISIBLE);
            continueButton.setVisibility(View.VISIBLE);
        }
        else {
            timeLeftTV.setVisibility(View.INVISIBLE);
            continueButton.setVisibility(View.INVISIBLE);
        }

        gameStatusTV.setText(gameStatus);
        timeLeftTV.setText(time);
        scoreTV.setText("Score : " + score + " / " + numberOfQueries);

        if(accuracy != -1)
            accuracyTV.setText("Accuracy :" + String.format( "%.2f" , accuracy) + "%");
        else
            accuracyTV.setText("Accuracy : Nil");
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