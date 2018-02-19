package com.djunicode.queuingapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL_PC on 13-01-2018.
 */

public class LocationTeacher {

  @SerializedName("id")
  public Integer id;

  @SerializedName("floor")
  public Integer floor;

  @SerializedName("department")
  public String department;

  @SerializedName("room")
  public String room;

  @SerializedName("updated_at")
  public String updated_at;


  public LocationTeacher(Integer id, Integer floor, String department, String room, String updated_at) {
    this.id = id;
    this.floor = floor;
    this.department = department;
    this.room = room;
    this.updated_at = updated_at;
  }

  public Integer getId() {
    return id;
  }

  public Integer getFloor() {
    return floor;
  }

  public String getDepartment() {
    return department;
  }

  public String getRoom() {
    return room;
  }

  public String getUpdated_at() {
    return updated_at;
  }
}
