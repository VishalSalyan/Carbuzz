package com.example.carbuzz.utils;

import com.example.carbuzz.data.CarData;
import com.example.carbuzz.data.UserData;

import java.util.ArrayList;

public class SessionData {
    private static final SessionData ourInstance = new SessionData();

    public static SessionData getInstance() {
        return ourInstance;
    }

    private SessionData() {
    }

    public ArrayList<CarData> totalCarList = new ArrayList<>();
    public UserData userData;
}
