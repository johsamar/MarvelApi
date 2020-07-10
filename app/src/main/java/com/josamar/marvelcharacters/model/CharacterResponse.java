package com.josamar.marvelcharacters.model;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class CharacterResponse {
    private Data data;

    private class Data{
        //private List<String> results = new ArrayList<>();
        private List<Info> results;

        public class Info {
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

        public String getName() {
            return results.get(0).getName();
        }
        public String getDescription() {
            return results.get(0).getDescription();
        }
        public String getImage() {
            return results.get(0).getImage();
        }

    }

    public String getName() {
        return data.getName();
    }
    public String getDescription() {
        return data.getDescription();
    }
    public String getImage() {
        return data.getImage();
    }
}
