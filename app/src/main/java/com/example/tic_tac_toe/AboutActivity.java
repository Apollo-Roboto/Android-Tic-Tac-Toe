package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AboutActivity extends AppCompatActivity {

    int easterEggCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void back_OnClick(View v){
        finish();
    }

    public void easterEgg_OnClick(View v){
        easterEggCounter++;
        if(easterEggCounter >= 5) {
            Toast.makeText(this, R.string.easterEggUnlocked, Toast.LENGTH_LONG).show();
            PlayerNameSelection.easterEggEnabled = true;
        }
    }

}
