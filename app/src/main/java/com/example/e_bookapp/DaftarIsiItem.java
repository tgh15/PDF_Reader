package com.example.e_bookapp;

public class DaftarIsiItem {
    final private String title;
    final private String description;
    final private int page;

    public DaftarIsiItem(String title, String description, int page) {
        this.title = title;
        this.description = description;
        this.page = page;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return title;
    }

    public int getPage(){return page;}
}

