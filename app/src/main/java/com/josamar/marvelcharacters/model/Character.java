package com.josamar.marvelcharacters.model;

public class Character {
    private String name;
    private String description;
    private Thumbnail thumbnail;

    private class Thumbnail{
        private String path;
        private String extension;
        public String getImage(){
            return path+"."+extension;
        }
    }
    public String getName() {
        return name;
    }
    public String getDescription(){
        return description;
    }
    public String getImage(){
        return thumbnail.getImage();
    }
}
