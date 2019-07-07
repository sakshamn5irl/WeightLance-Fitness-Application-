package com.androiddeft.loginandregistration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
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
import java.util.List;

import Utitlities.GetDataAdapter;
import Utitlities.RecyclerViewAdapter;

public class FoodListActivity extends AppCompatActivity {

    List<GetDataAdapter> GetDataAdapter1;

    RecyclerView recyclerView;

    RecyclerView.LayoutManager recyclerViewlayoutManager;

    RecyclerView.Adapter recyclerViewadapter;

    String GET_JSON_DATA_HTTP_URL = "http://3.19.45.207/androidphp/food/list.php";
    String JSON_IMAGE_TITLE_NAME = "food_desc";
    String JSON_IMAGE_URL = "food_img";


    JsonArrayRequest jsonArrayRequest ;

    RequestQueue requestQueue ;
    Button addFood;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FoodListActivity.this,DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        GetDataAdapter.flag=2;

        addFood = (Button)findViewById(R.id.btnAddFood);
        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FoodListActivity.this,UploadFoodActivity.class);
                startActivity(intent);
            }
        });
        GetDataAdapter1 = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview1);

        recyclerView.setHasFixedSize(true);

        recyclerViewlayoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(recyclerViewlayoutManager);



        getData();

    }

    public void getData(){
        jsonArrayRequest = new JsonArrayRequest(GET_JSON_DATA_HTTP_URL,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        parseData(response);
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
    public void parseData(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
            GetDataAdapter GetDataAdapter2 = new GetDataAdapter();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                GetDataAdapter2.setImageTitleNamee(json.getString(JSON_IMAGE_TITLE_NAME));
                GetDataAdapter2.setImageServerUrl(json.getString(JSON_IMAGE_URL));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            GetDataAdapter1.add(GetDataAdapter2);
        }

        recyclerViewadapter = new RecyclerViewAdapter(GetDataAdapter1, this);

        recyclerView.setAdapter(recyclerViewadapter);
    }
}



