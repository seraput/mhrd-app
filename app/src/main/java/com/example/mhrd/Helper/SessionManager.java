package com.example.mhrd.Helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {
    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    //user
    private static final String PREF_NAME = "LOGIN";
    public static final String LOGIN = "IS_LOGIN";
    public static final String LOGIN_ADMIN = "IS_LOGED_IN";
    public static final String LOGIN_SPV = "IS_LOGED_ON";
    public static final String ID = "ID";
    public static final String EMAIL = "EMAIL";
    public static final String NAME = "NAME";
    public static final String BRANCH = "BRANCH";
    public static final String PROJECTID = "PROJECTID";
    public static final String PROJECT = "PROJECT";
    public static final String LEVEL = "LEVEL";

    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession (String id, String nama, String email, String branch, String projectId, String project, String level){
        editor.putBoolean(LOGIN, true);
        editor.putBoolean(LOGIN_ADMIN, true);
        editor.putBoolean(LOGIN_SPV, true);
        editor.putString(ID, id);
        editor.putString(EMAIL, email);
        editor.putString(NAME, nama);
        editor.putString(BRANCH, branch);
        editor.putString(PROJECTID, projectId);
        editor.putString(PROJECT, project);
        editor.putString(LEVEL, level);
        editor.apply();
    }

//    public boolean isLogin(){
//        return sharedPreferences.getBoolean(LOGIN, false);
//    }

    public boolean isLogin(){
        return sharedPreferences.getBoolean(LOGIN, false);
    }

    public HashMap<String, String> getUserDetail(){

        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(BRANCH, sharedPreferences.getString(BRANCH, null));
        user.put(PROJECTID, sharedPreferences.getString(PROJECTID, null));
        user.put(PROJECT, sharedPreferences.getString(PROJECT, null));
        user.put(LEVEL, sharedPreferences.getString(LEVEL, null));

        return user;
    }
//    //Logout
//    public void LogoutUser(){
//        editor.clear();
//        editor.commit();
//        Intent intent = new Intent(context, LoginActivity.class);
//        context.startActivity(intent);
//        ((UserAccountActivity)context).finish();
//    }
//    public void LogoutAdmin(){
//        editor.clear();
//        editor.commit();
//        Intent intent = new Intent(context, LoginActivity.class);
//        context.startActivity(intent);
//        ((AdmMenuDashboard)context).finish();
//    }
//    public void LogoutKurir(){
//        editor.clear();
//        editor.commit();
//        Intent intent = new Intent(context, LoginActivity.class);
//        context.startActivity(intent);
//        ((KurirAkunActivity)context).finish();
//    }
}
