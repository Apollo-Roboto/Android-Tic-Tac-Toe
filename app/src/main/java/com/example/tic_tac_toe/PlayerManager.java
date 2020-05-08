package com.example.tic_tac_toe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PlayerManager {

    private TicTacToeActivity context;

    Player playerOne = new Player();
    Player playerTwo = new Player();

    public PlayerManager(TicTacToeActivity context){
        this.context = context;
    }

    public void initPlayer(){

        Intent intent = context.getIntent();

        playerOne.setName(intent.getStringExtra(TicTacToeActivity.EXTRA_PLAYER1_NAME));
        playerTwo.setName(intent.getStringExtra(TicTacToeActivity.EXTRA_PLAYER2_NAME));

        playerOne.isComputer = intent.getBooleanExtra(TicTacToeActivity.EXTRA_PLAYER1_COMPUTER, false);
        playerTwo.isComputer = intent.getBooleanExtra(TicTacToeActivity.EXTRA_PLAYER2_COMPUTER, false);

        playerOne.setPiece('X');
        playerTwo.setPiece('O');

        playerOne.setHeaderResource(R.string.playerOne);
        playerTwo.setHeaderResource(R.string.playerTwo);

        String computerName = context.getResources().getString(R.string.computer);

        if(playerOne.isComputer){
            playerOne.setName(computerName);
        }

        if(playerTwo.isComputer){
            playerTwo.setName(computerName);
        }

        updatePlayerPanel(playerOne);
        updatePlayerPanel(playerTwo);
    }

    public Player getPlayer(char piece){
        if(playerOne.getPiece() == piece)
            return playerOne;

        if(playerTwo.getPiece() == piece)
            return playerTwo;

        return null;
    }

    public void switchPlaces(){
        playerOne.switchPiece();
        playerTwo.switchPiece();

        Player pLeft = getPlayer('X');
        Player pRight = getPlayer('O');

        updatePlayerPanel(pLeft);
        updatePlayerPanel(pRight);
    }

    public Player currentPlayer(){
        return getPlayer(context.ticTacToe.getTurnChar());
    }

    public void updateLeftPanel(Player p){
        context.playerLeftNameHeader_textView.setText(p.getHeaderResource());
        context.playerLeftName_textView.setText(p.getName());
        context.playerLeftScore_textView.setText(String.valueOf(p.getScore()));
    }

    public void updateRightPanel(Player p){
        context.playerRightNameHeader_textView.setText(p.getHeaderResource());
        context.playerRightName_textView.setText(p.getName());
        context.playerRightScore_textView.setText(String.valueOf(p.getScore()));
    }

    public void updatePlayerPanel(Player p){
        if(p.getPiece() == 'X'){
            updateLeftPanel(p);
        } else {
            updateRightPanel(p);
        }
    }

    public void updatePlayerPanel(char piece){
        if(piece == 'X'){
            updateLeftPanel(getPlayer('X'));
        } else {
            updateRightPanel(getPlayer('O'));
        }
    }

    public void updatePlayerHeader(Player p, String header){
        if(p.getPiece() == 'X'){
            context.playerLeftNameHeader_textView.setText(header);
        } else {
            context.playerRightNameHeader_textView.setText(header);
        }
    }

    /** start up the computer player */
    public void computerStart(){
        // get the player object that is playing
        final Player p = currentPlayer();

        // let him think by waiting a second
        // while he think, don't let the user place a new button.
        context.emptyCellsSetEnable(false);

        // random thinking time from 600 to 1200
        int thinkTime = (int)(Math.random()*600)+600;

        // remove the player name header for the three dot thinking animation
        updatePlayerHeader(p, "");

        // CountDownTimer was the only option that didn't pause the whole app
        new CountDownTimer(thinkTime, (thinkTime/3)){
            String txt = "";
            @Override
            public void onTick(long millisUntilFinished) {
                // add a dot, going to do that three time
                txt += ". ";
                updatePlayerHeader(p, txt);
            }

            @Override
            public void onFinish() {
                context.emptyCellsSetEnable(true);
                computerPlay();

                //reset the player name header
                updatePlayerPanel(p);
            }
        }.start();
    }

    /** computer player play logic */
    private void computerPlay(){
        if(context.ticTacToe.isFull())
            return;

        ArrayList<Point> points = null;
        Point chosenPoint = null;

        // winnable move
        if(chosenPoint == null) {
            points = context.ticTacToe.winnableMoves(currentPlayer().getPiece());
            if(points != null)
                chosenPoint = points.get((int) (Math.random() * points.size()));
        }

        // blockable move
        if(chosenPoint == null){
            points = context.ticTacToe.blockableMoves(currentPlayer().getPiece());
            if(points != null)
                chosenPoint = points.get((int) (Math.random() * points.size()));
        }

        // random point
        if(chosenPoint == null) {
            points = context.ticTacToe.possibleMoves();
            if(points != null)
                chosenPoint = points.get((int) (Math.random() * points.size()));
        }

        context.play(chosenPoint);
    }













}
