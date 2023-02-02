package com.uliteteam.notes;

public class Note {
    private long ID;
    private String title;
    private String content;
    private String date;
    private String color;

    public Note(String title, String content, String date, String color) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.color = color;
        
    }

    public Note() {}

    public Note(
            long ID,
            String title,
            String content,
            String date,
            String color) {

        this.ID = ID;
        this.title = title;
        this.content = content;
        this.date = date;
        this.color = color;
    }

    public long getID() {
        return this.ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(java.lang.String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(java.lang.String content) {
        this.content = content;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(java.lang.String date) {
        this.date = date;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(java.lang.String color) {
        this.color = color;
    }
}
