package com.amanaggarwal1.dothemath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    //Function called when any game mode is chosen
    //It directs app to choose difficulty screen
    public void chooseGameMode(View view){
        Intent intent = new Intent(this, GameModeSelectionActivity.class);
        intent.putExtra("GameMode", view.getTag().toString());
        finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
