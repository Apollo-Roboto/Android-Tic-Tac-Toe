package com.example.tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.BlendMode;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class TicTacToeActivity extends AppCompatActivity {

    public static final String EXTRA_PLAYER1_NAME = "player1Name";
    public static final String EXTRA_PLAYER2_NAME = "player2Name";
    public static final String EXTRA_PLAYER1_COMPUTER = "player1Name";
    public static final String EXTRA_PLAYER2_COMPUTER = "player2Name";

    public TextView playerLeftName_textView;
    public TextView playerRightName_textView;
    public TextView playerLeftScore_textView;
    public TextView playerRightScore_textView;
    public TextView playerLeftNameHeader_textView;
    public TextView playerRightNameHeader_textView;

    TextView turnVisual_textView;
    TextView turnText_textView;

    ImageView turnVisual_imageView;

    ImageButton gridButtons[][] = new ImageButton[3][3];

    Button reset_button;

    ///////////////////////////////////////

    PlayerManager playerManager = new PlayerManager(this);

    int numOfRound = 0;

    TicTacToe ticTacToe = new TicTacToe();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);

        playerLeftName_textView = findViewById(R.id.playerLeftName_textView);
        playerRightName_textView = findViewById(R.id.playerRightName_textView);
        playerLeftScore_textView = findViewById(R.id.playerLeftScore_textView);
        playerRightScore_textView = findViewById(R.id.playerRightScore_textView);
        playerLeftNameHeader_textView = findViewById(R.id.playerLeftNameHeader_textView);
        playerRightNameHeader_textView = findViewById(R.id.playerRightNameHeader_textView);


        turnVisual_textView = findViewById(R.id.turnVisual_textView);
        turnText_textView = findViewById(R.id.turnText_textView);

        reset_button = findViewById(R.id.reset_button);

        turnVisual_imageView = findViewById(R.id.turnVisual_imageView);

        gridButtons[0][0] = findViewById(R.id.ticTacToeSlot1_button);
        gridButtons[1][0] = findViewById(R.id.ticTacToeSlot2_button);
        gridButtons[2][0] = findViewById(R.id.ticTacToeSlot3_button);
        gridButtons[0][1] = findViewById(R.id.ticTacToeSlot4_button);
        gridButtons[1][1] = findViewById(R.id.ticTacToeSlot5_button);
        gridButtons[2][1] = findViewById(R.id.ticTacToeSlot6_button);
        gridButtons[0][2] = findViewById(R.id.ticTacToeSlot7_button);
        gridButtons[1][2] = findViewById(R.id.ticTacToeSlot8_button);
        gridButtons[2][2] = findViewById(R.id.ticTacToeSlot9_button);

        //////////////////////////////////////

        // init players
        playerManager.initPlayer();

        turnVisual_textView.setText("");

        // if the computer is supposed to play his turn
        if(playerManager.currentPlayer().isComputer) {
            playerManager.computerStart();
        }

    }

    @Override
    public void onBackPressed(){
        // go back to MainActivity, main activity is set to 'singletask' so
        // it wont create a new activity

        // Are you sure dialog
        PopupDialog.AreYouSureReturnMainMenu(this);
    }

    private void switchTurn(){
        ticTacToe.switchTurn();
        char turn = ticTacToe.getTurnChar();

        switch(turn){
            case 'X':
                turnVisual_imageView.setImageResource(R.drawable.tictactoe_x_line);
                break;
            case 'O':
                turnVisual_imageView.setImageResource(R.drawable.tictactoe_o_line);
                break;
        }

        // if the current player to play is a computer, play
        if(playerManager.getPlayer(turn).isComputer){
            playerManager.computerStart();
        }

    }

    public void switchPlayerPlace(){
        playerManager.switchPlaces();

        Player p1 = playerManager.getPlayer('X');
        Player p2 = playerManager.getPlayer('O');

    }

    public void play(int x, int y){

        // block any action if the game is finished,  full or place is taken
        if(!ticTacToe.isPlayable(x, y))
            return;

        // set image based on turn
        int icon;
        if(ticTacToe.getTurnChar() == 'X')
            icon = R.drawable.tictactoe_x_with_bg;
        else
            icon = R.drawable.tictactoe_o_with_bg;

        gridButtons[x][y].setImageResource(icon);
        gridButtons[x][y].setClickable(false);

        // update the internal grid
        ticTacToe.place(x, y);

        switch(ticTacToe.state){
            case EMPTY:
                switchTurn();
                break;
            case NOT_FINISHED:
                switchTurn();
                break;
            case WINNER_X:
                setWinnerX();
                break;
            case WINNER_O:
                setWinnerO();
                break;
            case TIE:
                setTie();
                break;
        }
    }

    void play(Point p){
        play(p.x, p.y);
    }

    private void setWinnerX(){
        Player player = playerManager.getPlayer('X');

        // set text
        turnText_textView.setText(R.string.winner);
        turnVisual_textView.setText(player.getName());

        // add score
        player.addScore();
        playerManager.updatePlayerPanel('X');

        // make all button on the grid unclickable
        emptyCellsSetEnable(false);

        endGame();

        drawLine('X');
    }

    private void setWinnerO(){
        Player player = playerManager.getPlayer('O');

        // set text
        turnText_textView.setText(R.string.winner);
        turnVisual_textView.setText(player.getName());

        // add score
        player.addScore();
        playerManager.updatePlayerPanel('O');

        // make all button on the grid unclickable
        emptyCellsSetEnable(false);

        endGame();

        drawLine('O');
    }

    private void setTie(){
        turnText_textView.setText(R.string.noWinner);
        turnVisual_textView.setText(R.string.tie);

        endGame();
    }

    private void endGame(){
        // hide the turn image
        turnVisual_imageView.setVisibility(View.INVISIBLE);

        // change text of the reset button to "New Game"
        reset_button.setText(R.string.newGame);

        // if both player are computer, play again
        if(playerManager.getPlayer('X').isComputer &&
           playerManager.getPlayer('O').isComputer){

            // prevent restarting as it will restart by itself
            reset_button.setClickable(false);

            // give a little time before restarting
            new CountDownTimer(800, 800){
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    reset_button.setClickable(true);
                    reset();
                }
            }.start();

        }
    }

    public void emptyCellsSetEnable(boolean bool){
        for(int y = 0; y < ticTacToe.height; y++){
            for(int x = 0; x < ticTacToe.width; x++){
                if(ticTacToe.get(x,y) == '\0'){
                    gridButtons[x][y].setClickable(bool);
                }
            }
        }
    }

    public void drawLine(char piece){
        int tint = Color.argb(60,255,255,255);

        Triplet<Point> points = ticTacToe.getWinningLineCoords();
        if(points == null){
            return;
        }
        int x, y;
        x = points.first.x;
        y = points.first.y;
        gridButtons[x][y].setColorFilter(tint);
        x = points.second.x;
        y = points.second.y;
        gridButtons[x][y].setColorFilter(tint);
        x = points.third.x;
        y = points.third.y;
        gridButtons[x][y].setColorFilter(tint);

    }

    public void reset(){
        for(int y = 0; y < 3; y++){
            for(int x = 0; x < 3; x++){
                gridButtons[x][y].setImageResource(R.drawable.tictactoe_bg);
                gridButtons[x][y].setColorFilter(null);
                gridButtons[x][y].setClickable(true);
            }
        }

        turnVisual_textView.setText(String.valueOf(ticTacToe.getTurnChar()));
        turnText_textView.setText(R.string.turn);

        reset_button.setText(R.string.resetButton);

        turnVisual_textView.setText("");

        turnVisual_imageView.setImageResource(R.drawable.tictactoe_x_line);
        turnVisual_imageView.setVisibility(View.VISIBLE);

        // only switch place if the game ended
        if(ticTacToe.isGameEnded()){
            numOfRound++;
            switchPlayerPlace();
        }

        ticTacToe.reset();

        // if the computer is supposed to play his turn
        if(playerManager.currentPlayer().isComputer){
            playerManager.computerStart();
        }
    }





    //////////////////////// O N _ C L I C K

    public void menu_OnClick(View v){
        // go back to MainActivity, main activity is set to 'singletask' so
        // it wont create a new activity

        // Are you sure dialog
        PopupDialog.AreYouSureReturnMainMenu(this);
    }

    public void reset_OnClick(View v){
        reset();
    }

    public void ticTacToeSlot1_button_OnClick(View v){
        play(0,0);
    }

    public void ticTacToeSlot2_button_OnClick(View v){
        play(1,0);
    }

    public void ticTacToeSlot3_button_OnClick(View v){
        play(2, 0);
    }

    public void ticTacToeSlot4_button_OnClick(View v){
        play(0, 1);
    }

    public void ticTacToeSlot5_button_OnClick(View v){
        play(1,1);
    }

    public void ticTacToeSlot6_button_OnClick(View v){
        play(2,1);
    }

    public void ticTacToeSlot7_button_OnClick(View v){
        play(0,2);
    }

    public void ticTacToeSlot8_button_OnClick(View v){
        play(1,2);
    }

    public void ticTacToeSlot9_button_OnClick(View v){
        play(2,2);
    }

}
