package com.example.android.userflow;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

/**
 * Created by Skinner on 3/28/17.
 */

public class UserSession {

    //Constants
    public static final String USER_SESSION_DETAILS = "UserSessionDetails";
    public static final String FIELD_USERNAME = "username";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";
    public static final String FIELD_IS_LOGGED_IN = "isLoggedIn";
    public static final int LOGGED_OUT_VALUE = 0;
    public static final int LOGGED_IN_VALUE = 1;
    public static final String DEFAULT_STRING = "";

    //Variables
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    public UserSession(Context context) {
        mContext = context;
        mPreferences = context.getSharedPreferences(USER_SESSION_DETAILS, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public String getUserName() {
        return mPreferences.getString(FIELD_USERNAME, DEFAULT_STRING);
    }

    public String getFirstName() {
        return mPreferences.getString(FIELD_FIRST_NAME, DEFAULT_STRING);
    }

    public String getLastName(){
        return mPreferences.getString(FIELD_LAST_NAME, DEFAULT_STRING);
    }

    public int isLoggedIn() {
        return mPreferences.getInt(FIELD_IS_LOGGED_IN, LOGGED_OUT_VALUE);
    }

    public void login(String username,
                      String password,
                      String confirmationPassword,
                      String firstName,
                      String lastName) {

        if(UserValidation.validateName(username) == false) {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setMessage(R.string.username_validation_message);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return;
        }

        if(UserValidation.validatePassword(password, confirmationPassword) == false) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            alertDialogBuilder.setMessage(R.string.password_validation_message);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return;
        }

        if(UserValidation.validateName(firstName)) {
            mEditor.putString(FIELD_FIRST_NAME, firstName);
        }

        if(UserValidation.validateName(lastName)) {
            mEditor.putString(FIELD_LAST_NAME, lastName);
        }

        mEditor.putString(FIELD_USERNAME, username);
        mEditor.putString(FIELD_PASSWORD, password);
        mEditor.putInt(FIELD_IS_LOGGED_IN, LOGGED_IN_VALUE);

        mEditor.commit();
     }


    public void editUser(String username, String firstName, String lastName) {
        if(UserValidation.validateName(username)) {
            mEditor.putString(FIELD_USERNAME, username);
        }

        if(UserValidation.validateName(firstName)) {
            mEditor.putString(FIELD_FIRST_NAME, firstName);
        }

        if(UserValidation.validateName(lastName)) {
            mEditor.putString(FIELD_LAST_NAME, lastName);
        }

        mEditor.commit();
    }

    //Clears Preferences
    public void logout() {
        mEditor.clear();
        mEditor.commit();
    }

    public static class UserValidation {
        //Validates Password Entries
        @NonNull
        public static Boolean validatePassword(String password, String confirmationPassword) {
            if(password == null || confirmationPassword == null) {
                return false;
            }

            if(password.length() < 5) {
                return false;
            }

            if(!password.equals(confirmationPassword)) {
                return false;
            }

            return true;
        }

        //Validates First Name, Last Name, UserName Entries
        @NonNull
        public static Boolean validateName(String name) {
            if(name == null) {
                return false;
            }

            if(name.trim().length() == 0) {
                return false;
            }

            return true;
        }
    }



}
