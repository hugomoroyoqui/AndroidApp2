package com.hugo.firstapp;

public class LocationSchema {

    private String location_ID;
    private String name;
    private double latitude;
    private double longitude;

    public LocationSchema(String location_ID, String name, double latitude, double longitude) {
        this.location_ID = location_ID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLocation_ID() {
        return location_ID;
    }

    public void setLocation_ID(String location_ID) {
        this.location_ID = location_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
