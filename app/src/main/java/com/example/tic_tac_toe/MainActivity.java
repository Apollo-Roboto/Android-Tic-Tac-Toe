package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void twoPlayer_OnClick(View v){
        Intent i = new Intent(getApplicationContext(), PlayerNameSelection.class);
        startActivity(i);
    }

    public void onePlayer_OnClick(View v){
        Intent i = new Intent(getApplicationContext(), TicTacToeActivity.class);
        i.putExtra(TicTacToeActivity.EXTRA_PLAYER1_NAME, getResources().getString(R.string.player));
        i.putExtra(TicTacToeActivity.EXTRA_PLAYER2_COMPUTER, true);
        startActivity(i);
    }

    public void about_OnClick(View v){
        Intent i = new Intent(getApplicationContext(), AboutActivity.class);
        startActivity(i);
    }

    public void quit_OnClick(View v){
        finish();
        System.exit(0);
    }


}
