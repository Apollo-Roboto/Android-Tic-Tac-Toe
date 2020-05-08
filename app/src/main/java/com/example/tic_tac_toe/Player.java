package com.example.tic_tac_toe;

public class Player {
    private int score = 0;
    private String name;
    public boolean isComputer = false;
    private char piece = 'X';
    private int headerResource;

    public Player(){

    }

    public Player(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public String getScoreString() {
        return String.valueOf(score);
    }

    public void addScore() {
        score++;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null){
            name = "...";
        }
        this.name = name;
    }

    public char getPiece(){
        return piece;
    }

    public void setPiece(char piece){
        this.piece = piece;
    }

    public void switchPiece(){
        if(getPiece() == 'X')
            setPiece('O');
        else
            setPiece('X');
    }

    public int getHeaderResource() {
        return headerResource;
    }

    public void setHeaderResource(int headerResource) {
        this.headerResource = headerResource;
    }
}
