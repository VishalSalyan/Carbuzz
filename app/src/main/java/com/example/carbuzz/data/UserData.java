package com.example.carbuzz.data;

import java.util.ArrayList;
import java.util.HashMap;

public class UserData {
    private String name = null;
    private String email = null;
    private String phoneNumber = null;
    private String userImage = null;
    private String gender = null;
    private String password = null;
    private HashMap<String, WishListData> favouriteCars = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public HashMap<String, WishListData> getFavouriteCars() {
        return favouriteCars;
    }

    public void setFavouriteCars(HashMap<String, WishListData> favouriteCars) {
        this.favouriteCars = favouriteCars;
    }
}
