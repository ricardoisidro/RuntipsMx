package com.runtips.ricardo.runtipsmx.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLoginResponse {

    @SerializedName("weight")
    @Expose
    private int weight;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("cel_phone")
    @Expose
    private String celPhone;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;

    public UserLoginResponse(int weight, String sex, String name, String lastName, int id, String email, String celPhone, String birthDate) {
        this.weight = weight;
        this.sex = sex;
        this.name = name;
        this.lastName = lastName;
        this.id = id;
        this.email = email;
        this.celPhone = celPhone;
        this.birthDate = birthDate;
    }

    public int getWeight() {
        return weight;
    }

    public String getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getCelPhone() {
        return celPhone;
    }

    public String getBirthDate() {
        return birthDate;
    }





}
