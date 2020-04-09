package com.example.carbuzz.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.carbuzz.data.CarData;
import com.example.carbuzz.data.UserData;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SessionData {
    private static final String MyPREFERENCES = "MyPrefs";
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    private static final SessionData ourInstance = new SessionData();

    public static SessionData getInstance() {
        return ourInstance;
    }

    private SessionData() {
    }

    private Gson gson = new Gson();
    private String data = null;
    public ArrayList<CarData> totalCarList = new ArrayList<>();
    public UserData userData;

    public void initSharedPref(Context context) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void saveLogin(boolean isLogin) {
        editor = sharedpreferences.edit();
        editor.putBoolean("key", isLogin);
        editor.putString("data", data);
        editor.apply();
    }

    public void saveLocalData(UserData userData) {
        data = gson.toJson(userData);
    }

    public UserData getLocalData() {
        data = sharedpreferences.getString("data", null);
        return gson.fromJson(data, UserData.class);
    }

    public boolean isLogin() {
        return sharedpreferences.getBoolean("key", false);
    }

    public void clearSessionData() {
        editor.clear();
        editor.apply();
    }
}
