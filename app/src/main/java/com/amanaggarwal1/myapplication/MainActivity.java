package com.amanaggarwal1.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Declaring Views
    Button startGameButton;

    //Function to start Game called on tapping start button
    public void startGame(View view){
        startGameButton.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linking Views
        startGameButton = findViewById(R.id.startGameButton);
    }
}
