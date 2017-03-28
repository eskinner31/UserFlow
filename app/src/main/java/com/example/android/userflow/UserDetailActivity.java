package com.example.android.userflow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Skinner on 3/27/17.
 */

public class UserDetailActivity extends AppCompatActivity {

    //Constants
    public static final String TAG = UserDetailActivity.class.getSimpleName();

    //Variables
    private UserSession mSession;
    private String userName;
    private String firstName;
    private String lastName;

    //BindViews
    @BindView(R.id.user_name_text_view) TextView mUserName;
    @BindView(R.id.first_name_text_view) TextView mFirstName;
    @BindView(R.id.last_name_text_view) TextView mLastName;
    @BindView(R.id.edit_button) Button mEditButton;

    @OnClick(R.id.edit_button)
    public void edit() {
        Intent intent = new Intent(this, EditUserActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);
        ButterKnife.bind(this);
        mSession = new UserSession(this);
        if(mSession.isLoggedIn() == UserSession.LOGGED_OUT_VALUE) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        userName = setNameField(getResources().getString(R.string.user_name_text),mSession.getUserName());
        firstName = setNameField(getResources().getString(R.string.first_name_text), mSession.getFirstName());
        lastName = setNameField(getResources().getString(R.string.last_name_text), mSession.getLastName());
        mUserName.setText(userName);
        mFirstName.setText(firstName);
        mLastName.setText(lastName);
    }

    private String setNameField(String stringResource, String sessionString) {
        String formattedString;
        String emptyFieldString = getResources().getString(R.string.empty_name_field);

        if(sessionString.equals("")) {
            formattedString = stringResource + " " + emptyFieldString;
        } else {
            formattedString = stringResource + " " + sessionString;
        }

        return formattedString;
    }
}
