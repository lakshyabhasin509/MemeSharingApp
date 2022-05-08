package com.example.memesharingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMeme();
    }

    private void loadMeme(){

        ImageView memeImage=findViewById(R.id.memeImage);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://meme-api.herokuapp.com/gimme";

// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {

                    try {
                        String memeUrl = response.getString(url);
                        Glide.with(this).load(memeUrl).into(memeImage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }, error -> {

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

                });
        queue.add(jsonObjectRequest);

    }
    public void shareMeme(View view) {
    }

    public void nextMeme(View view) {

    }
}