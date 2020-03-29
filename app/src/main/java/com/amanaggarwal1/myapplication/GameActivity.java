package com.amanaggarwal1.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //Declaring Views
    private TextView queryTV, scoreTV, timerTV;
    private GridLayout choicesGrid;
    private Button[] choiceGridButton;
    private Button pauseGame;

    private int operandUpperBound = 50; //Upper bound of numbers to be added.
    private int operatorUpperBound = 2; // Here only 2 operators are used "+" and "-".
    private int numberOfOperations = 1; //Number of operations to be performed in every query.
    private int choiceDeviation = 20; // Maximum deviation from correct answer.
    private int totalGameTime = 30; //Total time for 1 round of game
    private int bufferTime = 400; // Buffer time to reduce lag

    private int locationOfCorrectAnswer; // Integer to store location of correct answer which will be updated with every query
    private int score = 0; // Number of queries answered correctly by user.
    private int numberOfQueries = 0; // Number of queries encountered by user.


    // Function to check if the answer given by user matches the generated answer
    private void verifyAnswer(int chosen, int correct){
        if(chosen == correct) score++;
        numberOfQueries++;
    }

    // Function to calculate correct answer of randomly generated query
    private int calculateCorrectAnswer(ArrayList<Integer> operands, ArrayList<Character> operations){

        int answer = operands.get( 0 ); // Initialising the answer with first value in operands array

        //Loop through operations and operands array to find the answer
        for(int i = 0; i < operations.size(); i++){
            if(operations.get(i) == '-') answer -= operands.get(i + 1);
            else if(operations.get(i) == '+') answer += operands.get(i + 1);
        }

        return answer; // Returns the answer back to updateQuery function
    }

    // Function to set the options to buttons on screen
    private void updateChoicesViews(ArrayList<Integer> choices){

        for(int i = 0; i < choices.size(); i++)
            choiceGridButton[i].setText(String.valueOf(choices.get(i)));
       }

    // Function to generate choices for a given query
    private void updateChoices(ArrayList<Integer> choices, int correctAnswer){
        Random random = new Random();

        // Get the number of buttons on screen (here there are 4 buttons)
        int numberOfChoices = choicesGrid.getRowCount() * choicesGrid.getColumnCount();

        // Randomly choose the position of correct answer button among various other buttons
        locationOfCorrectAnswer = random.nextInt(numberOfChoices);

        for (int i = 0; i < numberOfChoices; i++)
            if(i == locationOfCorrectAnswer) choices.add(correctAnswer);
            else{
                int wrongAnswer;

                    wrongAnswer = random.nextInt((choiceDeviation * 2) + 1) + correctAnswer - choiceDeviation;

                if(wrongAnswer == correctAnswer)
                    wrongAnswer = random.nextInt((choiceDeviation * 2) + 1) + correctAnswer - choiceDeviation;

                choices.add(wrongAnswer);
            }

        updateChoicesViews(choices);  // Call function to display randomly generated choices on screen
    }

    private void updateQuery(){
        Random random = new Random();

        String query = ""; // An empty string to store query

        ArrayList<Integer> operands = new ArrayList<>(); // Array to store operands
        ArrayList<Character> operations = new ArrayList<>(); //Array to store operators
        ArrayList<Integer> choices = new ArrayList<>(); //Array to store different choices

        //Fills the operator array with random values
        for(int i = 0; i < numberOfOperations + 1; i++)
            operands.add(random.nextInt(operandUpperBound + 1));

        //Fills the operator array with random symbols
        for(int i = 0; i < numberOfOperations; i++) {
            int operator;
            operator = random.nextInt(operatorUpperBound);

            if(operator == 0) operations.add('-');
            else if(operator == 1) operations.add('+');
        }

        //Update the query string with values from 2 arrays
        for(int i = 0; i < operations.size(); i++)
            query += String.valueOf(operands.get(i)) + " " + operations.get(i) + " ";

        //Adding last operand to query string
        query += String.valueOf( operands.get( operands.size() - 1 ) ) ;
        queryTV.setText(query); // Display query on screen

        // Call the function to calculate correct answer
        int correctAnswer = calculateCorrectAnswer(operands, operations);
        // Call function to generate and display the choices randomly on screen
        updateChoices(choices, correctAnswer);
    }

    // Function gets called when any of the choice button is tapped by user
    public void answerSelected(View view){
        verifyAnswer(locationOfCorrectAnswer ,Integer.parseInt(view.getTag().toString()));
        scoreTV.setText("Score : " + score + " / " + numberOfQueries);
        updateQuery();
    }

    public void gamePause(View view){
        score = 0;
        numberOfQueries = 0;
        startGameCounter();
        pauseGame.setAlpha(0.4f);
        pauseGame.setEnabled(false);
        scoreTV.setText("Score : Nil");
        updateQuery();
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
            }
        }.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Linking Views
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
