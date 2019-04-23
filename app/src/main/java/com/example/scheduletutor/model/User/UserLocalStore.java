package com.example.scheduletutor.model.User;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;

public class UserLocalStore {
    public static final String SP_Name = "userDetails";
    private SharedPreferences userLocalDatabase;
    private SharedPreferences.Editor editor;
    private static final String FULL_NAME = "full_name";
    private static final String USER_ID = "user_id";
    private static final String ROLE_ID = "role_id";
    private static final String USER_EMAIL = "user_email";
    private static final String IS_LOGIN = "is_login";
    private static final String USER_RATING = "user_rating";
    private static final String USER_PHOTO = "user_photo";

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_Name,0);
        editor = userLocalDatabase.edit();
    }

    public void storeUserData(User user){
        editor.putString(FULL_NAME, user.getUserName());
        editor.putString(USER_ID, user.getUserID());
        editor.putString(ROLE_ID,user.getUserRoleID());
        editor.putString(USER_EMAIL,user.getUserEmail());
        editor.putString(USER_RATING, user.getUserRating());
        editor.putString(USER_PHOTO, user.getUserRating());
        editor.commit();
    }

    public User getLoggedInUser(){
        String userID = userLocalDatabase.getString(USER_ID, "");
        String roleID = userLocalDatabase.getString(ROLE_ID, "");
        String userFullName = userLocalDatabase.getString(FULL_NAME, "");
        String userEmail = userLocalDatabase.getString(USER_EMAIL, "");
        String userRating = userLocalDatabase.getString(USER_RATING,"");
        String userPhoto = userLocalDatabase.getString(USER_PHOTO,"");

        User storedUser = new User();
        storedUser.setUserEmail(userEmail);
        storedUser.setUserRating(userRating);
        storedUser.setUserID(userID);
        storedUser.setUserRoleID(roleID);
        storedUser.setUserName(userFullName);
        storedUser.setUserPhoto(userPhoto);
        return storedUser;
    }

    public void setUserLoggedIn(Boolean loggedIn){
        editor.putBoolean(IS_LOGIN, loggedIn);
        editor.commit();
    }

    public boolean getUserLoggedIn(){
        return userLocalDatabase.getBoolean(IS_LOGIN, false);
    }
    public void clearUserData(){
        editor.clear();
        editor.commit();
    }

}

