package com.example.memesharingapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private String memeUrl = null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadMeme();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadMeme(){

        ProgressBar loadingBar=findViewById(R.id.progressBar);
        ImageView memeImage=findViewById(R.id.memeImageView);

//        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://meme-api.herokuapp.com/gimme";


// Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {
                    Log.d("success", "klkhjlkjh");

                    try {
                        memeUrl = response.getString("url");
                        loadingBar.onVisibilityAggregated(true);

                        Glide.with(this).load(memeUrl).listener(new RequestListener<Drawable>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                               loadingBar.onVisibilityAggregated(false);
                               loadMeme();
                                return false;
                            }

                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                loadingBar.onVisibilityAggregated(false);
                                return false;
                            }
                        }).into(memeImage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }




                }, error -> {

            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();

                });
       SingletonVolley.getInstance(this).addToRequestQueue(jsonObjectRequest);

    }
    public void shareMeme(View view) {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,"Hey checkout this cool meme"+memeUrl);
        Intent chooser=Intent.createChooser(intent,"share this image using ....");
        startActivity(chooser);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void nextMeme(View view) {
loadMeme();
    }
}