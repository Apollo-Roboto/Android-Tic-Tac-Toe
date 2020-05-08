package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PlayerNameSelection extends AppCompatActivity {

    public static boolean easterEggEnabled = false;

    EditText player1Name_editText;
    EditText player2Name_editText;

    Button easterEgg_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name_selection);

        player1Name_editText = findViewById(R.id.player1Name_editText);
        player2Name_editText = findViewById(R.id.player2Name_editText);

        easterEgg_button = findViewById(R.id.easterEgg_button);

        // show easter egg button if enabled
        if(easterEggEnabled){
            easterEgg_button.setVisibility(View.VISIBLE);
        }else{
            easterEgg_button.setVisibility(View.INVISIBLE);
        }

    }

    public void play_OnClick(View v){
        String p1Name = player1Name_editText.getText().toString();
        String p2Name = player2Name_editText.getText().toString();

        if(p1Name.equals(""))
            p1Name = "X";
        if(p2Name.equals(""))
            p2Name = "O";

        Intent i = new Intent(getApplicationContext(), TicTacToeActivity.class);
        i.putExtra(TicTacToeActivity.EXTRA_PLAYER1_NAME, p1Name);
        i.putExtra(TicTacToeActivity.EXTRA_PLAYER2_NAME, p2Name);

        startActivity(i);
    }

    public void computerVsComputer_OnClick(View v){
        Intent i = new Intent(getApplicationContext(), TicTacToeActivity.class);

        i.putExtra(TicTacToeActivity.EXTRA_PLAYER1_COMPUTER, true);
        i.putExtra(TicTacToeActivity.EXTRA_PLAYER2_COMPUTER, true);

        startActivity(i);
    }
}
