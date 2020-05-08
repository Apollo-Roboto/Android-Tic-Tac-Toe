package com.example.tic_tac_toe;

import androidx.annotation.NonNull;

import java.util.Iterator;
import java.util.function.Consumer;

/** collection of three identical item */
public class Triplet<T>{
    public T first;
    public T second;
    public T third;
    Triplet(T f, T s, T t){
        first = f;
        second = s;
        third = t;
    }

    @Override
    public String toString() {
        return "[" + first +
                ", " + second +
                ", " + third +
                ']';
    }
}