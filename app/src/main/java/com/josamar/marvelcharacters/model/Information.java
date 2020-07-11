package com.josamar.marvelcharacters.model;

import android.graphics.Bitmap;

public class Information {
    private String name;
    private Bitmap image;

    public Information(String name, Bitmap image) {
        this.name = name;
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
