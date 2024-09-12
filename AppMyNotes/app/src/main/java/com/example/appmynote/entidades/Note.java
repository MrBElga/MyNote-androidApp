package com.example.appmynote.entidades;

public class Note {
    private String title;
    private String priority;


    public Note(String title, String priority) {
        this.title = title;
        this.priority = priority;

    }

    public String getTitle() {
        return title;
    }

    public String getPriority() {
        return priority;
    }
}
