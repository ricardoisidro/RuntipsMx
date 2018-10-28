package com.runtips.ricardo.runtipsmx.api.apiservices;

import com.runtips.ricardo.runtipsmx.models.LoginResponse;
import com.runtips.ricardo.runtipsmx.models.PostLogin;
import com.runtips.ricardo.runtipsmx.models.PostRegister;
import com.runtips.ricardo.runtipsmx.models.UserRegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RuntipsmxService {

    @Headers("Content-Type: application/json")
    @POST("users")
    Call<UserRegisterResponse> createUser(@Body PostRegister postRegister);

    @Headers("Content-Type: application/json")
    @POST("sessions")
    Call<LoginResponse> createLogin(@Body PostLogin pl);

}
