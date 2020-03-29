package com.amanaggarwal1.dothemath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DifficultySelectionActivity extends AppCompatActivity {

    public void startGameButtonClicked(View view){
        Intent intent = new Intent(this, GameActivity.class);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selection);

    }
}
