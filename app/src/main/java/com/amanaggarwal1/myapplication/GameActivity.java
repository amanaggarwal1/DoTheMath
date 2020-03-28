package com.amanaggarwal1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    //Declaring Views
    private TextView query;

    private int operandUpperBound = 50; //Upper bound of numbers to be added
    private int operatorUpperBound = 2; // Here only 2 operators are used "+" and "-"
    private int numberOfOperations = 1; //Number of operations to be performed in every query

    private void updateQuery(){
        Random random = new Random();

        String queryText = ""; // An empty string to store query

        ArrayList<Integer> operands = new ArrayList<>(); // Array to store operands
        ArrayList<Character> operations = new ArrayList<>(); //Array to store operators

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

        Log.i("LOGCAT", "str = " + queryText);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Linking Views
        query = findViewById(R.id.queryTV);

        updateQuery();
    }
}
