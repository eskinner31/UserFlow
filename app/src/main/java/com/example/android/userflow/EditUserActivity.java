package com.example.android.userflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Skinner on 3/27/17.
 */

public class EditUserActivity extends AppCompatActivity {

    //Constants
    public static final String TAG = EditUserActivity.class.getSimpleName();

    //Variables
    UserSession mSession;


    //BindViews
    @BindView(R.id.user_name_text_view) TextView mUserName;
    @BindView(R.id.first_name_text_view) TextView mFirstName;
    @BindView(R.id.last_name_text_view) TextView mLastName;
    @BindView(R.id.save_button) Button mSaveButton;
    @BindView(R.id.logout_button) Button mLogOutButton;

    @OnClick(R.id.logout_button)
    public void logOut() {
        logOutProcedure();
    }

    @OnClick(R.id.save_button)
    public void saveChanges() {
        updateUserDetails();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);
        mSession = new UserSession(this);
        if(mSession.isLoggedIn() == UserSession.LOGGED_OUT_VALUE) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        mUserName.setText(mSession.getUserName());
        mFirstName.setText(mSession.getFirstName());
        mLastName.setText(mSession.getLastName());

    }

    private void logOutProcedure() {
        mSession.logout();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void updateUserDetails() {
        String userName = mUserName.getText().toString();
        String firstName = mFirstName.getText().toString();
        String lastName = mLastName.getText().toString();

        if(!userName.equals(mSession.getUserName())) {
            if(!UserSession.UserValidation.validateName(userName)) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
                alertBuilder.setMessage(R.string.username_empty_message);
                AlertDialog alert = alertBuilder.create();
                alert.show();
                return;
            }
        }

        mSession.editUser(userName, firstName, lastName);

        Intent intent = new Intent(this, UserDetailActivity.class);
        startActivity(intent);
        finish();
    }

}
