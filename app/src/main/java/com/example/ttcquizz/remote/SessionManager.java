package com.example.ttcquizz.remote;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ttcquizz.model.User;
import com.google.gson.Gson;

public class SessionManager {
    private static final String LOGIN_SHARED_PREF = "LOGIN_SHARED_PREF";
    private static final String TAG_TOKEN = "TAG_TOKEN";
    private static final String TAG_USER = "TAG_USER";
    private static final String FIRST_TIME = "FIRST_TIME";

    private static SessionManager instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    private SessionManager(Context context) {
        gson = new Gson();
        sharedPreferences = context.getSharedPreferences(LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (instance == null) {
            instance = new SessionManager(context);
        }
        return instance;
    }

    public boolean saveDevLiceToken(String token) {
        editor.putString(TAG_TOKEN, token);
        editor.apply();
        return true;
    }

    public String getDeviceToken() {
        return sharedPreferences.getString(TAG_TOKEN, null);
    }

    public void deleteToken() {
        editor.putString(TAG_TOKEN, null);
        editor.commit();
    }

    public boolean saveUser(User user) {
        editor.putString(TAG_USER, gson.toJson(user));
        editor.apply();
        return true;
    }

    public void saveStatus(boolean status) {
        editor.putBoolean(FIRST_TIME, status);
        editor.apply();
    }

    public boolean getStatus() {
        return sharedPreferences.getBoolean(FIRST_TIME,false);

    }

    public User getUser() {
        User user = new User();
        String data = sharedPreferences.getString(TAG_USER, null);
        if (data != null) {
            user = gson.fromJson(data, User.class);
        }
        return user;
    }
}
