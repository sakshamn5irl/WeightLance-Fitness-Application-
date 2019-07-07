package com.androiddeft.loginandregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {


    ArrayList<String> arrayList = new ArrayList<String>();
    String GET_JSON_DATA_HTTP_URL = "http://3.19.45.207/androidphp/report/list.php";
    String JSON_Height = "height";
    String JSON_Weight = "weight";
    String JSON_BMI = "bmi";
    String JSON_AGE = "age";
    String JSON_UNAME = "username";
    String username,Rheight,Rweight,Rbmi,RAge;
    JsonArrayRequest jsonArrayRequest ;
TextView height,weight,bmi,age,bmiVerdict,bmr;
    RequestQueue requestQueue ;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ReportActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();

        username = intent.getStringExtra("username");
        //Toast.makeText(this,username,Toast.LENGTH_LONG).show();
        height = (TextView)findViewById(R.id.txt_height);
        weight = (TextView)findViewById(R.id.txt_weight);
        age = (TextView)findViewById(R.id.txt_age);
        bmi = (TextView)findViewById(R.id.txt_bmi);
        bmiVerdict = (TextView)findViewById(R.id.txt_bmiVerdict);
        bmr = (TextView) findViewById(R.id.txt_bmr);

        JSON_DATA_WEB_CALL();
         }

    public void JSON_DATA_WEB_CALL(){

        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        JSON_PARSE_DATA_AFTER_WEBCALL(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        requestQueue = Volley.newRequestQueue(this);

        requestQueue.add(jsonArrayRequest);
    }

    public void JSON_PARSE_DATA_AFTER_WEBCALL(JSONArray array){

        //Toast.makeText(this,username+"12",Toast.LENGTH_LONG).show();
        for(int i = 0; i<array.length(); i++) {
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                String a = json.getString(JSON_UNAME);
                //Toast.makeText(this,a,Toast.LENGTH_LONG).show();
                if(a.toLowerCase().equals(username.toLowerCase())) {
                    String h = json.getString(JSON_Height);
                    String w = json.getString(JSON_Weight);
                    String ag = json.getString(JSON_AGE);
                    String bm = json.getString(JSON_BMI);
                   height.setText("Height: "+h);
                    weight.setText("Weight: "+w);
                    age.setText("Age: "+ag);
                    bmi.setText("BMI: "+bm);
                    String bmiv = BmiReport(bm);
                    bmr.setText("Body Mass Ratio: "+BMR(h,w,ag));
                    bmiVerdict.setText("BMI Report: " +bmiv);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public String BmiReport(String bmi)
    {
        int a = Integer.parseInt(bmi);
        if(a<=18.5)
        {
            return "UnderWeight";
        }
        else if(a<24.9&&a>18.5)
        {
            return "Normal";
        }
        else if(a<29.9 && a>24.9)
        {
            return "OverWeight";
        }
        else
        {
            return "Obese";
        }
    }
    public String BMR(String h,String w,String a)
    {
        int heigh = Integer.parseInt(h);
        int weigh = Integer.parseInt(w);
        int agge = Integer.parseInt(a);
        double bmr = (66 + (6.23 * weigh) + (12.7 * heigh) - (6.8 * agge));
        return String.valueOf(bmr);
    }

}




