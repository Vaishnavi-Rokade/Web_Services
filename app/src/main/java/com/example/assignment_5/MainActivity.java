package com.example.assignment_5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        RetrofitInterface retrofitInterface=RetrofitClient.getClient().create(RetrofitInterface.class);
        findViewById(R.id.btn_Retrofit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                retrofitInterface.getModelData().enqueue(new Callback<ArrayList<ModelQuotes>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ModelQuotes>> call, Response<ArrayList<ModelQuotes>> response) {
                        Toast.makeText(MainActivity.this, "Retrofit", Toast.LENGTH_SHORT).show();
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));
                        recyclerView.setAdapter(new RecyclerAdapter(MainActivity.this,response.body()));
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ModelQuotes>> call, Throwable t) {

                    }
                });
            }
        });

        findViewById(R.id.btn_Volly).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest("https://breaking-bad-quotes.herokuapp.com/v1/quotes/80", new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(MainActivity.this, "Volly", Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        ArrayList<ModelQuotes> quotesArrayList = gson.fromJson(response.toString(), new TypeToken<ArrayList<ModelQuotes>>() {
                        }.getType());
                        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
                        recyclerView.setAdapter(new RecyclerAdapter(MainActivity.this, quotesArrayList));
                    }
                },  new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                Volley.newRequestQueue(MainActivity.this).add(stringRequest);
            }
        });

        findViewById(R.id.btn_Http).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://breaking-bad-quotes.herokuapp.com/v1/quotes/5";
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                client.newCall(request).enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                        e.printStackTrace();
//                        Toast.makeText(MainActivity.this," e.getMessage().toString()", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Gson gson = new Gson();

                                try {
                                    Toast.makeText(MainActivity.this, "Okhttp", Toast.LENGTH_SHORT).show();
                                    ArrayList<ModelQuotes> quotesArrayList = gson.fromJson(response.body().string(),new TypeToken<ArrayList<ModelQuotes>>(){}.getType());
                                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,false));
                                    recyclerView.setAdapter(new RecyclerAdapter(MainActivity.this,quotesArrayList));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
//                                Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }
}