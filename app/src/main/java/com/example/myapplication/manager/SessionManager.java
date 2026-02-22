package com.example.myapplication.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        sharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLogin(boolean isLogin) {
        editor.putBoolean("is_login", isLogin);
        editor.apply();
    }

    public boolean isLogin() {
        return sharedPreferences.getBoolean("is_login", false);
    }

}
