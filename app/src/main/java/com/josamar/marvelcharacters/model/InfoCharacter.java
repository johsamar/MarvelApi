package com.josamar.marvelcharacters.model;

import java.util.List;

public class InfoCharacter {
    private Data data;

    private class Data{
        private List<Character> results;

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

        public List<Character> getResults() {
            return results;
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
    public List<Character> getLista() {
        return data.getResults();
    }
}

