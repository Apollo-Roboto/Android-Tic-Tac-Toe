package com.example.tic_tac_toe;

/** Grid management class */
public class Grid {

    /** width of the grid */
    protected int width;
    /** height of the grid */
    protected int height;
    /** internal two dimentional array of characters */
    private char grid[][];
    /** represent the number on items placed on the grid */
    private int numPlaced = 0;

    public Grid(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new char[width][height];
    }

    /** place a character on the grid */
    public void place(int x, int y, char c){
        if(isPosAvailable(x, y))
            numPlaced++;

        this.grid[x][y] = c;
    }

    /** clear a position on the grid */
    public void clear(int x, int y){
        this.grid[x][y] = '\0';
        numPlaced--;
    }

    /** check to know if a position is availlable */
    public boolean isPosAvailable(int x, int y){
        return this.grid[x][y] == '\0';
    }

    /** check to know if the grid is full */
    public boolean isFull(){
        return numPlaced == (width * height);
    }

    /** check to know if the grid is empty */
    public boolean isEmpty(){
        return numPlaced == 0;
    }

    /** get the character of a position on the grid */
    public char get(int x, int y){
        return grid[x][y];
    }

    /** reset and clear the whole grid */
    public void reset() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[x][y] = '\0';
            }
        }
        numPlaced = 0;
    }

}

