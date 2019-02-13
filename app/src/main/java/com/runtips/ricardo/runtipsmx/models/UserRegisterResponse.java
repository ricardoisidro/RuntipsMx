package com.runtips.ricardo.runtipsmx.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.runtips.ricardo.runtipsmx.models.DataRegisterResponse;

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
    private com.runtips.ricardo.runtipsmx.models.DataRegisterResponse data;

    public com.runtips.ricardo.runtipsmx.models.DataRegisterResponse getDataRegisterResponse() {
        return data;
    }

    public void setDataRegisterResponse(com.runtips.ricardo.runtipsmx.models.DataRegisterResponse data) {

        this.data = data;
    }

    public static com.runtips.ricardo.runtipsmx.models.DataRegisterResponse parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        com.runtips.ricardo.runtipsmx.models.DataRegisterResponse data = gson.fromJson(response, DataRegisterResponse.class);
        return data;
    }

}
