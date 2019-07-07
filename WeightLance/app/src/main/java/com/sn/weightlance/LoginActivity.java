package com.androiddeft.loginandregistration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import Utitlities.MySingleton;
import Utitlities.SessionHandler;

public class LoginActivity extends AppCompatActivity {
    private static final String KEY_STATUS = "status";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_EMPTY = "";
    private EditText etUsername;
    private EditText etPassword;
    private String username;
    private String password;
    private ProgressDialog pDialog;
    private String login_url = "http://3.19.45.207/androidphp/login/login.php";
    private SessionHandler session;

    @Override
    public void onBackPressed() {
        System.exit(0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = new SessionHandler(getApplicationContext());

        if(session.isLoggedIn()){
            loadDashboard();
        }
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);

        Button register = findViewById(R.id.btnLoginRegister);
        Button login = findViewById(R.id.btnLogin);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username = etUsername.getText().toString().toLowerCase().trim();
                password = etPassword.getText().toString().trim();
                if (validateInputs()) {
                    login();
                }
            }
        });
    }


    private void loadDashboard() {
        Intent i = new Intent(getApplicationContext(), DashboardActivity.class);
       // i.putExtra();
        startActivity(i);
        finish();

    }


    private void displayLoader() {
        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setMessage("Logging In.. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();

    }

    private void login() {
        displayLoader();
        JSONObject request = new JSONObject();
        try {
            request.put(KEY_USERNAME, username);
            request.put(KEY_PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsArrayRequest = new JsonObjectRequest
                (Request.Method.POST, login_url, request, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        pDialog.dismiss();
                        try {
                            if (response.getInt(KEY_STATUS) == 0) {
                                session.loginUser(username,response.getString(KEY_FULL_NAME),response.getInt("user_id"));
                                loadDashboard();
                            }else{
                                Toast.makeText(getApplicationContext(),
                                        response.getString(KEY_MESSAGE), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();


                        Toast.makeText(getApplicationContext(),
                                error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(jsArrayRequest);
    }


    private boolean validateInputs() {
        if(KEY_EMPTY.equals(username)){
            etUsername.setError("Username cannot be empty");
            etUsername.requestFocus();
            return false;
        }
        if(KEY_EMPTY.equals(password)){
            etPassword.setError("Password cannot be empty");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }
}
