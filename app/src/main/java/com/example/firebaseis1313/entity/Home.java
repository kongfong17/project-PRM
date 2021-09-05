package com.example.firebaseis1313.entity;

public class Home {
    String id;
    String address;
    int available_room;
    float distance;
    Host host;
    String latitude;
    String longitude;
    int total_room;

    public Home() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAvailable_room() {
        return available_room;
    }

    public void setAvailable_room(int available_room) {
        this.available_room = available_room;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getTotal_room() {
        return total_room;
    }

    public void setTotal_room(int total_room) {
        this.total_room = total_room;
    }
}
