package com.amanaggarwal1.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //Declaring Views
    private TextView query, scoreText;
    private GridLayout choicesGrid;
    private Button choice0Button, choice1Button, choice2Button, choice3Button;

    private int operandUpperBound = 50; //Upper bound of numbers to be added.
    private int operatorUpperBound = 2; // Here only 2 operators are used "+" and "-".
    private int numberOfOperations = 1; //Number of operations to be performed in every query.
    private int choiceDeviation = 20; // Maximum deviation from correct answer.
    private int locationOfCorrectAnswer; // Integer to store location of correct answer which will be updated with every query
    private int score = 0; // Number of queries answered correctly by user.
    private int numberOfQueries = 0; // Number of queries encountered by user.


    // Function to check if the answer given by user matches the generated answer
    private void verifyAnswer(int chosen, int correct){
        if(chosen == correct) score++;
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
        choice0Button.setText(String.valueOf(choices.get(0)));
        choice1Button.setText(String.valueOf(choices.get(1)));
        choice2Button.setText(String.valueOf(choices.get(2)));
        choice3Button.setText(String.valueOf(choices.get(3)));
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

        numberOfQueries++;
        String queryText = ""; // An empty string to store query

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
            queryText += String.valueOf(operands.get(i)) + " " + operations.get(i) + " ";

        //Adding last operand to query string
        queryText += String.valueOf( operands.get( operands.size() - 1 ) ) ;
        query.setText(queryText); // Display query on screen

        // Call the function to calculate correct answer
        int correctAnswer = calculateCorrectAnswer(operands, operations);
        // Call function to generate and display the choices randomly on screen
        updateChoices(choices, correctAnswer);
    }

    public void answerSelected(View view){

        verifyAnswer(locationOfCorrectAnswer ,Integer.parseInt(view.getTag().toString()));
        scoreText.setText("Score : " + score + " / " + numberOfQueries);
        updateQuery();
        scoreText.setText("Score : " + score + " / " + numberOfQueries);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Linking Views
        query = findViewById(R.id.queryTV);
        scoreText = findViewById(R.id.scoreTV);
        choicesGrid = findViewById(R.id.choicesGridLayout);
        choice0Button = findViewById(R.id.choice0Button);
        choice1Button = findViewById(R.id.choice1Button);
        choice2Button = findViewById(R.id.choice2Button);
        choice3Button = findViewById(R.id.choice3Button);

        updateQuery();
        scoreText.setText("Score : " + score + " / " + numberOfQueries);
    }
}
