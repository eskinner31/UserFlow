package com.example.android.userflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    //Constants
    public static final String TAG = LoginActivity.class.getSimpleName();

    //Variables
    private UserSession mSession;

    //BindViews
    @BindView(R.id.user_name) EditText mUserName;
    @BindView(R.id.password_edit_text) EditText mPassword;
    @BindView(R.id.password_confirmation_edit_text) EditText mConfirmPassword;
    @BindView(R.id.first_name_edit_text) EditText mFirstName;
    @BindView(R.id.last_name_edit_text) EditText mLastName;
    @BindView(R.id.submit_button) Button mSubmitButton;

    @OnClick(R.id.submit_button)
    public void submit() {
        submission();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mSession = new UserSession(this);
        checkSession(mSession.isLoggedIn());
    }

    private void checkSession(int sessionStatus) {
        if(sessionStatus == UserSession.LOGGED_IN_VALUE) {
            Intent intent = new Intent(this, UserDetailActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void submission() {
        if(!UserSession.UserValidation.validateName(mUserName.getText().toString())) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setMessage(R.string.username_message);
            AlertDialog alert = alertBuilder.create();
            alert.show();
            return;
        }

        if(!UserSession.UserValidation.validatePassword(mPassword.getText().toString(),
                mConfirmPassword.getText().toString())) {

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);

            if(!mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {

                alertBuilder.setMessage(R.string.password_fails_match_message);

            } else {

                alertBuilder.setMessage(R.string.password_too_short_message);
            }
            AlertDialog alert = alertBuilder.create();
            alert.show();
            return;
        }

        mSession.login(mUserName.getText().toString(), mPassword.getText().toString(),
                mConfirmPassword.getText().toString(), mFirstName.getText().toString(),
                mLastName.getText().toString());

        Intent intent = new Intent(this, UserDetailActivity.class);
        startActivity(intent);
        finish();
    }

}
