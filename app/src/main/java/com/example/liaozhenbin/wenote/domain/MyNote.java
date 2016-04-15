package com.example.liaozhenbin.wenote.domain;

/**
 * Created by liaozhenbin on 2016/1/6.
 */
public class MyNote {
    private String author;
    private String title;
    private String content;
    private String data;
    private String path;
    private int id;

    public MyNote() {
    }

    public MyNote(String author, String title, String content, String data, String path) {
        this.author = author;
        this.title = title;
        this.content = content;
        this.data = data;
        this.path = path;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getData() {
        return data;
    }

    public String getPath() {
        return path;
    }

    public int getId() {
        return id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setId(int id) {
        this.id = id;
    }
}