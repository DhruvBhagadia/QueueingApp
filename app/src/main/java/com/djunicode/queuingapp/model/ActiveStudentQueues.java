package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ActiveStudentQueues {
    @SerializedName("id")
    public Integer id;

    @SerializedName("startTime")
    public String startTime;

    @SerializedName("endTime")
    public String endTime;

    @SerializedName("location")
    public Integer location;

    @SerializedName("subject")
    public String subject;

    public ActiveStudentQueues(Integer id, String startTime, String endTime, Integer location,
                               String subject){
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.location = location;
        this.subject = subject;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
