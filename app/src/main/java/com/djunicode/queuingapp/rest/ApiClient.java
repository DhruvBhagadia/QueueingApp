package com.djunicode.queuingapp.rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL_PC on 09-01-2018.
 */

public class ApiClient {

  private static Retrofit retrofit = null;

  public static Retrofit getClient () {
    retrofit = new Retrofit.Builder()
        .baseUrl("http://192.168.0.100:8000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    return retrofit;
  }
}
