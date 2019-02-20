package com.runtips.ricardo.runtipsmx.models;

import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class UserModel {

    @PrimaryKey
    private int id;
    @Index
    private String mail;
    private String phone;
    private String facebook;
    private String name;
    private String surname;
    private String password;
    private int age;
    private String sex;

}
