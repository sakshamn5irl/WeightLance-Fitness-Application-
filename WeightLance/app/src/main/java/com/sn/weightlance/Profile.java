package com.androiddeft.loginandregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import Utitlities.SessionHandler;
import Utitlities.User;

public class Profile extends AppCompatActivity {

    TextView uname,fname,id,sessionexpiry;
    private SessionHandler session;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Profile.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();
        uname = (TextView)findViewById(R.id.txt_uname);
        uname.setText("UserName: "+user.getUsername());
        fname = (TextView)findViewById(R.id.txt_fullname);
        fname.setText("Full Name: "+user.getFullName());
        id = (TextView)findViewById(R.id.txt_id);
        id.setText("User ID: "+String.valueOf(user.getid()));
        sessionexpiry = (TextView)findViewById(R.id.txt_session);
        sessionexpiry.setText("Session Expires On: "+String.valueOf(user.getSessionExpiryDate()));




    }
}
