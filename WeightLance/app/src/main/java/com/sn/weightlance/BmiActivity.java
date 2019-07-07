package com.androiddeft.loginandregistration;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BmiActivity extends AppCompatActivity {
      EditText height,weight,age;
      Button submit;
        String height1,weight1,food1;
        String age1,bmi;
    String ServerURL = "http://3.19.45.207/androidphp/userfitness/insert.php" ;
    Spinner spinner;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(this,"THIS IS COMPULSORY",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        spinner = (Spinner) findViewById(R.id.foodspin);

        List<String> categories = new ArrayList<String>();
        categories.add("Vegetarian");
        categories.add("Non Vegetarian");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        spinner.setAdapter(dataAdapter);

        height = (EditText) findViewById(R.id.height);
        weight = (EditText) findViewById(R.id.weight);
        age = (EditText) findViewById(R.id.age);

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = getIntent();
                String username = i.getStringExtra("userid");
                getData();
               // Toast.makeText(BmiActivity.this, bmi, Toast.LENGTH_LONG).show();
                InsertBmi(username,height1,weight1,age1,bmi,food1);
                i = new Intent(BmiActivity.this,LoginActivity.class);

                startActivity(i);
            }
        });
    }

    public void getData(){
        height1 = height.getText().toString();
        weight1 = weight.getText().toString();
        age1 = age.getText().toString();
       int bmi1 = (Integer.parseInt(weight1)*703) / (Integer.parseInt(height1)*Integer.parseInt(height1));
       //int bmi1  = 95/(170*170);
        Toast.makeText(BmiActivity.this, String.valueOf(bmi1), Toast.LENGTH_LONG).show();
       bmi = String.valueOf(bmi1);

      food1 = spinner.getSelectedItem().toString();
    }

    public void InsertBmi(final String username, final String height,final String weight,final String age,final String bmi,final String food){

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String UserNameHolder = username ;
                String HeightHolder = height ;
                String WeightHolder = weight ;
                String FoodHolder = food ;
                String AgeHolder = String.valueOf(age);
                String BmiHolder = String.valueOf(bmi);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("username", UserNameHolder));
                nameValuePairs.add(new BasicNameValuePair("height", HeightHolder));
                nameValuePairs.add(new BasicNameValuePair("weight", WeightHolder));
                nameValuePairs.add(new BasicNameValuePair("food", FoodHolder));
                nameValuePairs.add(new BasicNameValuePair("age", AgeHolder));
                nameValuePairs.add(new BasicNameValuePair("bmi", BmiHolder));

                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(ServerURL);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    HttpEntity httpEntity = httpResponse.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Inserted Successfully";
            }

            @Override
            protected void onPostExecute(String result) {

                super.onPostExecute(result);

                Toast.makeText(BmiActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }

        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

        sendPostReqAsyncTask.execute(username,height,weight,bmi,age,food);
    }
}
