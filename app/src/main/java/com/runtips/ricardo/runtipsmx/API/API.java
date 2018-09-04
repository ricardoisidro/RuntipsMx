package com.runtips.ricardo.runtipsmx.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    public static final String BASE_URL = "https://staging.runtipsmx.com/api/v1/";
    private static Retrofit retrofit = null;

    public static Retrofit getApi(){
        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
