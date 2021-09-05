package com.example.firebaseis1313.entity;

import java.sql.Time;
import java.sql.Timestamp;

public class Room {
    String id;
    float area;
    int available_room;
    String description;
//    String createdTime;
    Long createdTime;
    Home home;
    Image image;
    float price;
    String title;
    int total_room;

    public Room() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getArea() {
        return area;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public int getAvailable_room() {
        return available_room;
    }

    public void setAvailable_room(int available_room) {
        this.available_room = available_room;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    //    public String getCreatedTime() {
//        return createdTime;
//    }
//
//    public void setCreatedTime(String createdTime) {
//        this.createdTime = createdTime;
//    }

    public Home getHome() {
        return home;
    }

    public void setHome(Home home) {
        this.home = home;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTotal_room() {
        return total_room;
    }

    public void setTotal_room(int total_room) {
        this.total_room = total_room;
    }
}
