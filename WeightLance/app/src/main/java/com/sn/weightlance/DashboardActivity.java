package com.androiddeft.loginandregistration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;

import Utitlities.SessionHandler;
import Utitlities.User;

public class DashboardActivity extends AppCompatActivity {
    private SessionHandler session;
    FirebaseAnalytics firebaseAnalytics;
    Bundle bundle = new Bundle();
    @Override
    public void onBackPressed() {
        System.exit(0);
       // Toast.makeText(this,"Tap LogOut to Exit",Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
           case R.id.alarmBtn:
                Intent intent = new Intent(DashboardActivity.this,AlarmActivity.class);
                startActivity(intent);
                return true;
            case R.id.profileBtn:
                Intent intent1 = new Intent(DashboardActivity.this,Profile.class);
                startActivity(intent1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseAnalytics.setAnalyticsCollectionEnabled(true);

        session = new SessionHandler(getApplicationContext());
        final User user = session.getUserDetails();
        TextView welcomeText = findViewById(R.id.welcomeText);

        welcomeText.setText("Welcome \n"+user.getFullName().toUpperCase());

        Button exerciseBtn = findViewById(R.id.btn_excercise);


        exerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, ExerciseActivity.class);
                startActivity(i);
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                firebaseAnalytics.setUserProperty("Event", "Exercise");
            }
        });
        Button foodBtn = findViewById(R.id.btn_add_food);
        foodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, FoodListActivity.class);
                startActivity(i);
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                firebaseAnalytics.setUserProperty("Event", "Food");

            }
        });
        Button reportBtn = findViewById(R.id.btn_report);
        reportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DashboardActivity.this, ReportActivity.class);
                i.putExtra("username",user.getUsername());
                startActivity(i);
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
                firebaseAnalytics.setUserProperty("Event", "Report");
            }
        });


        Button logoutBtn = findViewById(R.id.btnLogout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(DashboardActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}
