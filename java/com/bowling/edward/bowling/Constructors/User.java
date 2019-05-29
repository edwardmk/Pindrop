package com.bowling.edward.bowling.Constructors;

public class User {


    private String email;
    private String username;
    private String userId;

    public User(){

    }

    public User(String username, String email){
        this.username = username;
        this.email = email;
    }
    public User(String username, String email, String userId){
        this.username = username;
        this.email = email;
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getUserId() {
        return userId;
    }

    public void getUserId(String userId) {
        this.userId = userId;
    }
}
