package com.example.root.wallpaperapp.models;

import com.google.firebase.database.Exclude;

public class wallpaper {
    @Exclude
    public String id;
    @Exclude
    public String category;

    public String Url , title;

    @Exclude
    public boolean isFav =false;

    public wallpaper(String id, String url, String title,String category) {
        this.id = id;
        Url = url;
        this.title = title;
        this.category = category;
    }
}