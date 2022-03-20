package com.scrambler.classes.file_operations;

public record Pair<T, V>(T first, V second) {

    public T getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
}
