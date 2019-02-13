package com.runtips.ricardo.runtipsmx.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.runtips.ricardo.runtipsmx.models.UserLoginResponse;

public class DataLoginResponse {
    @SerializedName("user")
    @Expose
    private UserLoginResponse user;
    @SerializedName("token")
    @Expose
    private String token;

    public DataLoginResponse(UserLoginResponse user, String token) {
        this.user = user;
        this.token = token;

    }
    public UserLoginResponse getUser() {
        return user;
    }

    public void setUser(UserLoginResponse user) {

        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static DataLoginResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        DataLoginResponse datalogin = gson.fromJson(response, DataLoginResponse.class);
        return datalogin;
    }
}
