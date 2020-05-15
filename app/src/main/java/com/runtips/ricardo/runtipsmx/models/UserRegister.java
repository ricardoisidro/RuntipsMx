package com.runtips.ricardo.runtipsmx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * UserRegister.java
 *
 * POJO for RegisterActivity, for create nodes wrapped by user:
 */

public class UserRegister {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("cel_phone")
    @Expose
    private String celPhone;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("password_confirmation")
    @Expose
    private String passwordConfirmation;

    public UserRegister(String email, String celPhone, String name, String lastName, int weight, String birthDate, String sex, String password, String passwordConfirmation) {
        this.email = email;
        this.celPhone = celPhone;
        this.name = name;
        this.lastName = lastName;
        this.weight = weight;
        this.birthDate = birthDate;
        this.sex = sex;
        this.password = password;
        this.passwordConfirmation = passwordConfirmation;
    }
}
