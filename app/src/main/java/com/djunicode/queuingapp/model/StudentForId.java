package com.djunicode.queuingapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DELL_PC on 24-01-2018.
 */

public class StudentForId {

  @SerializedName("username")
  public String username;
  @SerializedName("password")
  public String password;
  //    @SerializedName("salt")
//    public String salt;
  @SerializedName("id")
  public Integer id;
//    @SerializedName("created_at")
//    public String updated_at;


  public StudentForId(String username, String password, String salt, Integer id, String created_at) {
    this.id = id;
    this.username = username;
    this.password = password;
//        this.salt = salt;
//        this.updated_at = created_at;
  }

  public Integer getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

//    public String getSalt() {
//        return salt;
//    }
//
//    public String getCreated_at() {
//        return updated_at;
//    }

}
