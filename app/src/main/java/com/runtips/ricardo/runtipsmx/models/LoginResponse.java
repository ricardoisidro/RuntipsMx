package com.runtips.ricardo.runtipsmx.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.runtips.ricardo.runtipsmx.models.DataLoginResponse;

public class LoginResponse {
    @SerializedName("data")
    @Expose
    private DataLoginResponse datalogin;

    public DataLoginResponse getData() {

        return datalogin;
    }

    public void setData(DataLoginResponse data) {

        this.datalogin = datalogin;
    }

    public static DataLoginResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        DataLoginResponse datalogin = gson.fromJson(response, DataLoginResponse.class);
        return datalogin;
    }

}
