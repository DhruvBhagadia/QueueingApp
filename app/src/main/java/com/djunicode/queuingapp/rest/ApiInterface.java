package com.djunicode.queuingapp.rest;

import com.djunicode.queuingapp.BuildConfig;
import com.djunicode.queuingapp.model.LocationTeacher;

import com.djunicode.queuingapp.model.RecentEvents;
import com.djunicode.queuingapp.model.TeacherCreateNew;
import com.djunicode.queuingapp.model.TeacherListModel;
import com.djunicode.queuingapp.model.TeacherModel;


import org.json.JSONObject;
import retrofit2.http.Body;

import com.djunicode.queuingapp.model.Student;
import com.djunicode.queuingapp.model.StudentForId;
import com.djunicode.queuingapp.model.StudentQueue;
import com.djunicode.queuingapp.model.StudentSubscriptions;
import com.djunicode.queuingapp.model.TeachersList;
import com.djunicode.queuingapp.model.UserEmailVerify;
import com.djunicode.queuingapp.model.UserModel;

import java.util.List;
import retrofit2.Call;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by DELL_PC on 09-01-2018.
 */

public interface ApiInterface {

  String API_TOKEN = BuildConfig.API_TOKEN;
  String API_SECRET_KEY = BuildConfig.API_SECRET_KEY;

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/student/")
  Call<Student> createStudentAccount(@Field("user") int user_id, @Field("name") String username,
      @Field("sapID") String sapId, @Field("department") String department,
      @Field("year") String year, @Field("batch") String batch,
      @Field("register_id") String register_id);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/users/{id}/")
  Call<UserModel> updateUserData(@Path("id") int id, @Field("username") String username,
      @Field("password") String password);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/teacher/")
  Call<TeacherModel> createTeacherAccount(@Field("name") String username, @Field
      ("user") int user_id, @Field("location") String location, @Field
      ("subject") String subject, @Field("sapId") String sapId,
      @Field("register_id") String register_id);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/users/")
  Call<StudentForId> getId(@Field("username") String username, @Field("password") String password);

  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @DELETE("queues/{id}/")
  Call<LocationTeacher> deleteTeacherLocation(@Path("id") int id);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/queue/{id}/add/")
  Call<StudentQueue> studentJoiningTheQueue(@Path("id") int id, @Field("queueItems") String sapID);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PATCH("queues/teacher/{id}/")
  Call<TeacherModel> updateTeachersLocation(@Path("id") int id, @Field("location") int location
      , @Field("user") int user, @Field("register_id") String register_id);

  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @GET("queues/teacher/{name}/")
  Call<TeacherModel> getTeacherId(@Path("name") String name);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/subject/")
  Call<TeacherListModel> getTeachers(@Field("name") String name);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/")
  Call<LocationTeacher> sendTeacherLocation(@Field("floor") Integer floor,
      @Field("department") String department, @Field("room") String room);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/")
  Call<LocationTeacher> sendQueueLocation(@Field("floor") Integer floor, @Field
          ("department") String department, @Field("room") String room);

  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @GET("queues/{id}/")
  Call<LocationTeacher> getTeacherLocation(@Path("id") int id);

  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @GET("queues/{id}/")
  Call<LocationTeacher> getQueueLocation(@Path("id") int id);

  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @GET("queues/teacher/name/{name}/")
  Call<TeacherModel> getIdForTeacherFromName(@Path("name") String name);

  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @GET("queues/teacher/name/")
  Call<TeacherModel> getLocationIntegerForTeacherFromName(@Field("name") String name);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/users/")
  Call<UserEmailVerify> sendEmail(@Field("username") String name, @Field("email") String email,
      @Field("password") String password);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/users/{id}/token/")
  Call<UserEmailVerify> verifyEmail(@Path("id") int id, @Field("token") String token);

  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @GET("queues/queue/{id}/")
  Call<StudentQueue> getUpdatedQueue(@Path("id") int id);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/student/{id}/")
  Call<StudentSubscriptions> setStudentSubscriptions(@Path("id") int id,
      @Field("teacherNames") String subscriptions);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/queue/{id}/index/")
  Call<StudentQueue> getUpdatedStudentLocation(@Path("id") int id, @Field("sapID") String sapID);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/queue/{id}/deletespecific/")
  Call<StudentQueue> deleteStudentFromQueue(@Path("id") Integer id, @Field("element") String sapID);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/queue/notification/")
  Call<TeacherCreateNew> startTheQueue(@Field("id") Integer id,
      @Field("teacherName") String teacherName);

  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @DELETE("queues/queue/{id}/")
  Call<TeacherCreateNew> deleteQueue(@Path("id") Integer id);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/subject/")
  Call<TeachersList> getTeachersList(@Field("name") String name);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/queue/")
  Call<TeacherCreateNew> sendSubmissionData(@Field("subject") String subject,
      @Field("startTime") String startTime, @Field("endTime") String endTime,
      @Field("maxLength") Integer noOfStudents, @Field("queueItems") String queueItems,
      @Field("location") Integer location);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/teacher/{id}/addqueue/")
  Call<TeacherCreateNew> linkQueueToTeacher(@Path("id") Integer teacherId,
      @Field("id") Integer queueId);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/teacher/{id}/deletequeue/")
  Call<TeacherCreateNew> deleteQueueLinkFromTeacher(@Path("id") Integer teacherId,
      @Field("id") Integer queueId);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/teacher/getqueues/")
  Call<List<TeacherCreateNew>> getParticularTeacherQueues(@Field("teacherName") String teacherName);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/teacher/{id}/subject/")
  Call<TeacherModel> addTeacherSubjects(@Path("id") int id, @Field("subject") String subject);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/queue/{id}/")
  Call<StudentQueue> editingQueue (@Path("id") int id, @Field("maxLength") int maxLength,
                                   @Field("startTime") String startTime, @Field("endTime") String endTime,
                                   @Field("subject") String subject, @Field("avgTime") String avgTime, @Field("size") int size);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @PUT("queues/queue/{id}/")
  Call<StudentQueue> editQueueLocation(@Path("id") int id, @Field("maxLength") int maxLength,
                                       @Field("startTime") String startTime, @Field("endTime")
                                               String endTime, @Field("subject") String subject,
                                       @Field("avgTime") String avgTime, @Field("size") int size,
                                       @Field("location") int location);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/teacher/login/")
  Call<TeacherModel> getValidId(@Field("sapId") String sapID, @Field("password") String password);

  @FormUrlEncoded
  @Headers({
          "Api-Token: " + API_TOKEN,
          "Api-Secret-Key: " + API_SECRET_KEY
  })
  @POST("queues/student/login/")
  Call<Student> getValidIdStudent(@Field("sapID") String sapID, @Field("password") String password);
}
