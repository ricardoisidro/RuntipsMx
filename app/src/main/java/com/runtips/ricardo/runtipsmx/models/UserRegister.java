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

    /*public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelPhone() {
        return celPhone;
    }

    public void setCelPhone(String celPhone) {
        this.celPhone = celPhone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }*/

}
