package com.amanaggarwal1.dothemath;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int operandUpperBound = 50; //Upper bound of numbers to be added.
        int numberOfOperations = 1; //Number of operations to be performed in every query.
    }
}