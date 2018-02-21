package com.djunicode.queuingapp.rest;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by DELL_PC on 09-01-2018.
 */

public class ApiClient {

  private static Retrofit retrofit = null;

  public static Retrofit getClient () {

    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


    retrofit = new Retrofit.Builder()
        .baseUrl("http://sahiljajodia01.pythonanywhere.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    return retrofit;
  }
}
