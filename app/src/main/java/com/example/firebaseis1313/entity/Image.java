package com.example.firebaseis1313.entity;

import java.util.ArrayList;

public class Image {
    String id;
    ArrayList<String> listImageUrl;

    public Image() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<String> getListImageUrl() {
        return listImageUrl;
    }

    public void setListImageUrl(ArrayList<String> listImageUrl) {
        this.listImageUrl = listImageUrl;
    }
}
