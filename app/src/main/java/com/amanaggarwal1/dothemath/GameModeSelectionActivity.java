package com.amanaggarwal1.dothemath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameModeSelectionActivity extends AppCompatActivity {

    private TextView gameModeTV;
    private TextView gameModeDescriptionTV;
    private Button startGameButton;
    private String chosenGameMode;
    Intent intent;

    public void startGameButtonClicked(View view){
        finish();
        startActivity(intent);
    }

    public void goToMainMenu(View view){
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

    private void setClassicMode(){
        gameModeDescriptionTV.setText(R.string.ClassicModeDescription);
    }

    private void setArcadeMode(){
        gameModeDescriptionTV.setText(R.string.ArcadeModeDescription);
    }

    private void setCountdownMode(){
        gameModeDescriptionTV.setText(R.string.CountdownModeDescription);
        intent = new Intent(this, CountdownGameActivity.class);
    }

    private void updateViews(){
        chosenGameMode = getIntent().getStringExtra("GameMode");

        gameModeTV.setText(chosenGameMode);

        if(chosenGameMode.equals("CLASSIC"))
            setClassicMode();
        else if(chosenGameMode.equals("ARCADE"))
            setArcadeMode();
        else if(chosenGameMode.equals("COUNTDOWN"))
            setCountdownMode();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_mode_selection);

        gameModeTV = findViewById(R.id.gameModeTV);
        gameModeDescriptionTV = findViewById(R.id.gameModeDescriptionTV);
        startGameButton = findViewById(R.id.startGameButton);

        updateViews();
    }
}
