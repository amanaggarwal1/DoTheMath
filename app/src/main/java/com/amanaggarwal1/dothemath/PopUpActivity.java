package com.amanaggarwal1.dothemath;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

public class PopUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = (int) ( displayMetrics.widthPixels * 0.8 );
        int height = (int) (displayMetrics.heightPixels * 0.7 );
        getWindow().setLayout( width, height);

    }

    public void continueGame(View view) {
        finish();
    }

    public void goToMainMenu(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        finish();
        startActivity(intent);
    }

}