package com.djunicode.queuingapp.rest;

import com.djunicode.queuingapp.model.LocationTeacher;
import com.djunicode.queuingapp.model.Student;
import com.djunicode.queuingapp.model.StudentForId;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by DELL_PC on 09-01-2018.
 */

public interface ApiInterface {

  @FormUrlEncoded
  @POST("queues/student/")
  Call<Student> createStudentAccount (@Field("user") int user_id, @Field("name") String username,
      @Field("sapID") String sapId, @Field("department") String department,
      @Field("year") String year, @Field("batch") String batch);

  @FormUrlEncoded
  @POST("queues/users/")
  Call<StudentForId> getId (@Field("username") String username, @Field("password") String password);

  @FormUrlEncoded
  @POST("queues/")
  Call<LocationTeacher> sendTeacherLocation (@Field("floor") Integer floor,
      @Field("department") String department, @Field("room") String room);

  @GET("queues/{id}/")
  Call<LocationTeacher> getTeacherLocation (@Path("id") int id);

  @DELETE("queues/{id}/")
  Call<LocationTeacher> deleteTeacherLocation (@Path("id") int id);

}
