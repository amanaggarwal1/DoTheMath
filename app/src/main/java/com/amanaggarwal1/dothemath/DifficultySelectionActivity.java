package com.amanaggarwal1.dothemath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DifficultySelectionActivity extends AppCompatActivity {

    ListView difficultyLV;

    public void startGameButtonClicked(View view){
        Intent intent = new Intent(this, GameActivity.class);
        finish();
        startActivity(intent);
    }

    private void setDifficultyModes(){


        ArrayList<String> difficultyModes = new ArrayList<>();
        difficultyModes.add("Beginner");
        difficultyModes.add("Easy");
        difficultyModes.add("Medium");
        difficultyModes.add("Hard");
        difficultyModes.add("Very Hard");
        difficultyModes.add("Pro Grade");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, difficultyModes);
        difficultyLV.setAdapter(arrayAdapter);
        difficultyLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,long arg3) {
                view.setSelected(true);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selection);

        difficultyLV = findViewById(R.id.difficultyLV);
    }
}
