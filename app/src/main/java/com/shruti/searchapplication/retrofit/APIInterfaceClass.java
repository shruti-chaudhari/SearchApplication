package com.shruti.searchapplication.retrofit;

import com.shruti.searchapplication.constants.Constants;
import com.shruti.searchapplication.dao.ArticlesResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface APIInterfaceClass {

    @GET(Constants.search)
    Call<ArticlesResponse> getWeather(@QueryMap Map<String,String> options);

}
