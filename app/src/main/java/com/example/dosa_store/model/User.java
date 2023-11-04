package com.example.dosa_store.model;

public class User {
    private int id;
    private String username;
    private String password;
    private String displayName;
    private String address;
    private String email;
    private String phone;
    private String userImg;
    private String image;

    public User() {
    }

    public User(String username, String password, String displayName, String address, String email, String phone, String userImg, String image) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.userImg = userImg;
        this.image = image;
    }

    public User(int id, String username, String password, String displayName, String address, String email, String phone, String userImg, String image) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.userImg = userImg;
        this.image = image;
    }

    public User(String username, String password, String displayName) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
    }

    public User(String username, String password, String displayName, String address, String phone, String email) {
        this.username = username;
        this.password = password;
        this.displayName = displayName;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
