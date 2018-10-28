package com.runtips.ricardo.runtipsmx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * PostRegister.java
 *
 * POJO for RegisterActivity for create the user: node
 */
public class PostRegister {

    @SerializedName("user")
    @Expose
    private UserRegister user;

    public UserRegister getUser() {
        return user;
    }

    public void setUser(UserRegister user) {
        this.user = user;
    }

}