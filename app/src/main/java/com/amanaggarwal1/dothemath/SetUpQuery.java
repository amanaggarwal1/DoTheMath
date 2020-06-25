package com.amanaggarwal1.dothemath;

import android.graphics.Color;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import java.util.ArrayList;
import java.util.Random;

public class SetUpQuery {

    //Declaring Views
    private ConstraintLayout gameLayout;
    private TextView queryTV;
    private GridLayout choicesGrid;
    private Button[] choiceGridButton;

    private int operandUpperBound; //Upper bound of numbers to be added.
    private int numberOfOperations; //Number of operations to be performed in every query.

    private int locationOfCorrectAnswer; // Integer to store location of correct answer which will be updated with every query


    //Constructor
    public SetUpQuery(ConstraintLayout gameLayout, TextView queryTV, GridLayout choicesGrid, Button[] choiceGridButton, int operandUpperBound, int numberOfOperations) {
        this.gameLayout = gameLayout;
        this.queryTV = queryTV;
        this.choicesGrid = choicesGrid;
        this.choiceGridButton = choiceGridButton;
        this.operandUpperBound = operandUpperBound;
        this.numberOfOperations = numberOfOperations;
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
        int choiceDeviation = 20; // Maximum deviation from correct answer.

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

    public void updateQuery(){
        Random random = new Random();

        int operatorUpperBound = 2; // Here only 2 operators are used "+" and "-".
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

    // Function to check if the answer given by user matches the generated answer
    public int verifyAnswer(int chosen){
        int result = 0;
        if(chosen == locationOfCorrectAnswer){
            // Set background color to green
            gameLayout.setBackgroundColor(Color.GREEN);
            result = 1;
        }else{
            // Set background color to red
            gameLayout.setBackgroundColor(Color.RED);

        }

        //Set background color back to white after 100 milliseconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gameLayout.setBackgroundColor(Color.WHITE);
            }
        },100);

        return result;
    }

}
