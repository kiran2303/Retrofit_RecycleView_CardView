package com.bavariya.kiran.retrofitrecyclecardview;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APiService {

    @GET("/json")
    Call<List<Product>> getbook();

}