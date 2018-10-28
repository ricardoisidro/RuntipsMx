package com.runtips.ricardo.runtipsmx.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * UserRegisterResponse.java
 *
 * Class for RegisterActivity.java.
 *
 * POJO for receive Register response, creating data: node
 */

public class UserRegisterResponse {

    @SerializedName("data")
    @Expose
    private DataRegisterResponse data;

    public DataRegisterResponse getDataRegisterResponse() {
        return data;
    }

    public void setDataRegisterResponse(DataRegisterResponse data) {

        this.data = data;
    }

    public static DataRegisterResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        DataRegisterResponse data = gson.fromJson(response, DataRegisterResponse.class);
        return data;
    }

}
