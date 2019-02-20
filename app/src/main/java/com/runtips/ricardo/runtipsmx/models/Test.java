package com.runtips.ricardo.runtipsmx.models;

import com.runtips.ricardo.runtipsmx.app.RuntipsMXApp;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Test extends RealmObject {

    @PrimaryKey
    private int id;
    private int time;
    private int pulse;
    private float weight;
    private int value;
    @Required @Index
    private Date date;

    public Test() {

    }

    public Test(int time, int pulse, float weight){
        this.id = RuntipsMXApp.testId.incrementAndGet();
        this.time = time;
        this.pulse = pulse;
        this.weight = weight;
        this.value = 0;
        this.date = new Date();

    }

    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPulse() {
        return pulse;
    }

    public void setPulse(int pulse) {
        this.pulse = pulse;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }



}
