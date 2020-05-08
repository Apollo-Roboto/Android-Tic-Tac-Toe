package com.example.tic_tac_toe;

public class Point {
    public int x;
    public int y;
    Point(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x +
                ", " + y +
                ')';
    }
}
