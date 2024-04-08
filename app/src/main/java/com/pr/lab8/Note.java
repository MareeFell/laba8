package com.pr.lab8;

import androidx.annotation.NonNull;

public class Note {
    private final int Id;
    private final String text;


    public Note(int id, String text) {
        Id = id;
        this.text = text;
    }

    public int getId() {
        return Id;
    }

    public String getText() {
        return text;
    }

    @NonNull
    @Override
    public String toString() {
        return Id + " " + text;
    }
}
