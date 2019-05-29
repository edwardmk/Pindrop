package com.bowling.edward.bowling.Constructors;

public class Alley {

    public String name;
    public String location;
    public String currLocation;
    public String website;
    public String email;
    public String placeID;

    public double rating;

    public Alley(String name, String location, double rating, String placeID, String currLocation) {
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.placeID = placeID;
        this.currLocation = currLocation;
    }
    public String getCurrLocation() {
        return currLocation;
    }

    public void setCurrLocation(String currLocation) {
        this.currLocation = currLocation;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
