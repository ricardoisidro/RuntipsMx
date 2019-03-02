package com.runtips.ricardo.runtipsmx.models;

import com.runtips.ricardo.runtipsmx.app.RuntipsMXApp;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class UserModel extends RealmObject {

    @PrimaryKey
    private int id;
    @Index
    private String mail;
    private String phone;
    private String facebook;
    private String name;
    private String surname;
    private String password;
    private String birthday;
    private String sex;
    private String state;

    public UserModel() {

    }

    public UserModel(String name, String surname,
                     String birthday, String sex,
                     String state, String phone,
                     String mail, String password,
                     String facebook) {
        this.id = 1; //RuntipsMXApp.userId.incrementAndGet();
        this.mail = mail;
        this.phone = phone;
        this.facebook = facebook;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.birthday = birthday;
        this.sex = sex;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacebook() { return facebook; }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getState() { return state; }

    public void setState(String state) { this.state = state; }
}
