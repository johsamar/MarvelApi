package com.josamar.marvelcharacters.model;

import java.util.List;

public class InfoCharacter {
    private Data data;

    private class Data{
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

        public String getName(int i) {
            return results.get(i).getName();
        }
        public String getDescription(int i) {
            return results.get(i).getDescription();
        }
        public String getImage(int i) {
            return results.get(i).getImage();
        }

        public int getCantResults() {
            return results.size();
        }
    }

    public String getName(int i) {
        return data.getName(i);
    }
    public String getDescription(int i) {
        return data.getDescription(i);
    }
    public String getImage(int i) {
        return data.getImage(i);
    }

    public int getCantResults() {
        return data.getCantResults();
    }
}

