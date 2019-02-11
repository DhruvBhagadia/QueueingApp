package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentQueue {

    @SerializedName("id")
    public Integer id;

    @SerializedName("isEmpty")
    public Boolean isEmpty;

    @SerializedName("isFull")
    public Boolean isFull;

    @SerializedName("size")
    public Integer size;

    @SerializedName("maxLength")
    public Integer maxLength;

    @SerializedName("startTime")
    public String startTime;

    @SerializedName("endTime")
    public String endTime;

    @SerializedName("avgTime")
    public String avgTime;

    @SerializedName("subject")
    public String subject;

    @SerializedName("lock")
    public Boolean lock;

    @SerializedName("created_at")
    public String created_at;

    @SerializedName("updated_at")
    public String updated_at;

    @SerializedName("queueItems")
    public String queueItems;

    @SerializedName("items")
    public ArrayList<String> items = new ArrayList<>();

    @SerializedName("index")
    public Integer index;

    @SerializedName("location")
    public Integer location;

    @SerializedName("data")
    public StudentQueue data;


    /*public Integer id, size, maxLength;
    public Boolean isEmpty, isFull, lock;
    public String subject, queueItems;
    public Date startTime, endTime, avgTime;
    public Timestamp created_at, updated_at;*/


    public StudentQueue(Integer id, Boolean isEmpty, Boolean isFull, Integer size,
                        Integer maxLength, String startTime, String endTime,
                        String avgTime, String subject, Boolean lock, String created_at,
                        String updated_at, String queueItems, ArrayList<String> items, Integer index,
                        Integer location, StudentQueue data) {

        this.id = id;
        this.isEmpty = isEmpty;
        this.isFull = isFull;
        this.size = size;
        this.maxLength = maxLength;
        this.startTime = startTime;
        this.endTime = endTime;
        this.avgTime = avgTime;
        this.subject = subject;
        this.lock = lock;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.queueItems = queueItems;
        this.items = items;
        this.index = index;
        this.location = location;
        this.data = data;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getEmpty() {
        return isEmpty;
    }

    public void setEmpty(Boolean empty) {
        isEmpty = empty;
    }

    public Boolean getFull() {
        return isFull;
    }

    public void setFull(Boolean full) {
        isFull = full;
    }

    public void setAvgTime(String avgTime) {
        this.avgTime = avgTime;
    }

    public String getAvgTime() {
        return avgTime;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setQueueItems(String queueItems) {
        this.queueItems = queueItems;
    }

    public String getQueueItems() {
        return queueItems;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getSize() {
        return size;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public StudentQueue getData() {
        return data;
    }

    public void setData(StudentQueue data) {
        this.data = data;
    }
}
