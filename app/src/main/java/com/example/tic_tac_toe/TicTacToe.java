package com.example.tic_tac_toe;

import android.util.Log;

import java.util.ArrayList;
import java.util.function.Predicate;

public class TicTacToe extends Grid {

    static enum State{
        EMPTY,
        NOT_FINISHED,
        TIE,
        WINNER_X,
        WINNER_O
    }

    /** Represent the playable characters */
    private final char PLAYABLES[] = {'X', 'O'};

    private char turn = PLAYABLES[0];

    private Triplet<Point> winningLineCoords = null;

    public State state = State.EMPTY;

    TicTacToe(){
        super(3,3);
    }

    /** Switch turns (X -> O)  (O -> X) */
    public void switchTurn(){
        turn = (turn == PLAYABLES[0] ? PLAYABLES[1] : PLAYABLES[0]);
    }

    /**
     * little algorithm that check the state of the game.
     * Can be either EMPTY, NOT_FINISHED, TIE, WINNER_X, WINNER_O.
     * Also set the winning line coordinates.
     * @return enum TicTacToe.State representing the state of the game.
     */
    private State check() {

        if(isEmpty())
            return State.EMPTY;

        for(char c : PLAYABLES){

            // checking verticals
            for(int x = 0; x < width; x++){
                if(get(x, 0) == c && get(x, 1) == c && get(x, 2) == c) {
                    setWinningLineCoords(x, 0, x, 1, x, 2);
                    return (c == 'X') ? State.WINNER_X : State.WINNER_O;
                }
            }

            // checking horizontals
            for(int y = 0; y < height; y++){
                if(get(0, y) == c && get(1, y) == c && get(2, y) == c) {
                    setWinningLineCoords(0, y, 1, y, 2, y);
                    return (c == 'X') ? State.WINNER_X : State.WINNER_O;
                }
            }

            // checking diagonals \
            if(get(0, 0) == c && get(1, 1) == c && get(2, 2) == c) {
                setWinningLineCoords(0, 0, 1, 1, 2, 2);
                return (c == 'X') ? State.WINNER_X : State.WINNER_O;
            }

            // checking diagonals /
            if(get(2, 0) == c && get(1, 1) == c && get(0, 2) == c) {
                setWinningLineCoords(2, 0, 1, 1, 0, 2);
                return (c == 'X') ? State.WINNER_X : State.WINNER_O;
            }
        }

        if(isFull())
            return State.TIE;

        return State.NOT_FINISHED;
    }

    private void setWinningLineCoords(int x1, int y1, int x2, int y2, int x3, int y3){
        Point p1 = new Point(x1, y1);
        Point p2 = new Point(x2, y2);
        Point p3 = new Point(x3, y3);
        winningLineCoords = new Triplet<>(p1, p2, p3);
    }

    public Triplet<Point> getWinningLineCoords() {
        return winningLineCoords;
    }

    public char getTurnChar(){
        return turn;
    }

    public boolean isGameEnded(){
        return !(state == State.EMPTY || state == State.NOT_FINISHED);
    }

    public void place(int x, int y) {
        super.place(x, y, turn);
        state = check();
    }

    public ArrayList<Point> winnableMoves(char p){

        ArrayList<Point> points = new ArrayList<>();

        // check verticals
        for (int x = 0; x < width; x++) {
            if ((get(x, 1) == p) && (get(x, 2) == p)) {
                points.add(new Point(x, 0));
            }
            if ((get(x, 0) == p) && (get(x, 2) == p)) {
                points.add(new Point(x, 1));
            }
            if ((get(x, 0) == p) && (get(x, 1) == p)) {
                points.add(new Point(x, 2));
            }
        }

        // check horizontal
        for (int y = 0; y < height; y++) {
            if ((get(1, y) == p) && (get(2, y) == p)) {
                points.add(new Point(0, y));
            }
            if ((get(0, y) == p) && (get(2, y) == p)) {
                points.add(new Point(1, y));
            }
            if ((get(0, y) == p) && (get(1, y) == p)) {
                points.add(new Point(2, y));
            }
        }

        // check diagonal '\'
        if ((get(1, 1) == p) && (get(2, 2) == p)) {
            points.add(new Point(0, 0));
        }
        if ((get(0, 0) == p) && (get(2, 2) == p)) {
            points.add(new Point(1, 1));
        }
        if ((get(0, 0) == p) && (get(1, 1) == p)) {
            points.add(new Point(2, 2));
        }

        // check diagonal '/'
        if ((get(1, 1) == p) && (get(0, 2) == p)) {
            points.add(new Point(2, 0));
        }
        if ((get(2, 0) == p) && (get(0, 2) == p)) {
            points.add(new Point(1, 1));
        }
        if ((get(2, 0) == p) && (get(1, 1) == p)) {
            points.add(new Point(0, 2));
        }

        // remove if point is unplayable
        points.removeIf(new Predicate<Point>() {
            @Override
            public boolean test(Point point) {
                if(isPosAvailable(point.x, point.y))
                    return false;
                return true;
            }
        });

        if(points.size() <= 0)
            return null;

        return points;
    }

    public ArrayList<Point> blockableMoves(char p){
        if(p == 'X'){
            return winnableMoves('O');
        }
        return winnableMoves('X');
    }

    public ArrayList<Point> possibleMoves(){

        ArrayList<Point> points = new ArrayList<>();

        for(int y = 0; y < height; y++){
            for(int x = 0; x < width; x++){
                if(isPosAvailable(x, y)){
                    points.add(new Point(x, y));
                }
            }
        }

        return points;
    }

    public boolean isPlayable(int x, int y){
        return (this.state == State.EMPTY || this.state == State.NOT_FINISHED) &&
                this.isPosAvailable(x, y);
    }


    @Override
    public void reset() {
        turn = PLAYABLES[0];
        super.reset();
        state = State.EMPTY;
        winningLineCoords = null;
    }
}
