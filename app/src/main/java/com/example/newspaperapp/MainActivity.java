package com.example.newspaperapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
RecyclerView recyclerView;
//step2*******************************
private RecyclerView.LayoutManager mLayoutManager;
RecyclerView.Adapter mAdapter;
ArrayList<HashMap<String,String>> arrayListNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }
    private void parseAPIResponse(String response)
    {

        try {
            JSONObject objResponse =new JSONObject(response);
            JSONArray arrayHeader = objResponse.getJSONArray("headlines");
            for (int i = 0; i <arrayHeader.length() ; i++) {
            JSONObject currItem=arrayHeader.getJSONObject(i);
            String title=currItem.getString("title");
            String imgUrl=currItem.getString("imgUrl");
            String desc=currItem.getString("description");
            HashMap<String,String>map=new HashMap<>();
            map.put("title",title);
            map.put("url",imgUrl);
            map.put("detail",desc);
            arrayListNews.add(map);
                mAdapter =new HomeListAdapter(MainActivity.this,arrayListNews);
                recyclerView.setAdapter(mAdapter);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void initComponents() {
        //Step1******************************************
        recyclerView=findViewById(R.id.recyclerView);
        //Step2******************************************
        mLayoutManager=new LinearLayoutManager(this);
        //Step3*************************************
        recyclerView.setLayoutManager(mLayoutManager);
        arrayListNews=new ArrayList<>();
        callAPI();
    }

    private void callAPI() {
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://api.myjson.com/bins/vwe65";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("=====","=======response"+response);
                        //Toast.makeText(MainActivity.this, response.substring(0,1000), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setCancelable(true);
                        builder.setTitle("MyJason");
                        builder.setMessage(response);
                        builder.show();
                        parseAPIResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
