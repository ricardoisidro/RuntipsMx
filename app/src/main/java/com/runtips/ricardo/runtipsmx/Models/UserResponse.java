package com.runtips.ricardo.runtipsmx.Models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserResponse {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static Data parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        Data data = gson.fromJson(response, Data.class);
        return data;
    }

}
