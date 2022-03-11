package com.example.javaproject.classes.fileOperations;

public record Pair<T, V>(T first, V second) {

    public T getFirst() {
        return first;
    }

    public V getSecond() {
        return second;
    }
}
