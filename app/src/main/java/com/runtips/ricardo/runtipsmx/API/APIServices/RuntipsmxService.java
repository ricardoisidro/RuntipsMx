package com.runtips.ricardo.runtipsmx.API.APIServices;

import com.runtips.ricardo.runtipsmx.Models.Post;
import com.runtips.ricardo.runtipsmx.Models.UserResponse;

import java.util.Observable;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RuntipsmxService {

    @Headers("Content-Type: application/json")
    @POST("users")
    Call<UserResponse> createUser(@Body Post post);

    //@GET("sessions")

}
