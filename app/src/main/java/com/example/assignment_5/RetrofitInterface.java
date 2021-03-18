package com.example.assignment_5;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET("quotes/80")
    Call<ArrayList<ModelQuotes>> getModelData();
}
