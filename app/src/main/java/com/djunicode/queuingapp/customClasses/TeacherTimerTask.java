package com.djunicode.queuingapp.customClasses;

import android.util.Log;
import com.djunicode.queuingapp.model.LocationTeacher;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;
import java.util.TimerTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DELL_PC on 23-01-2018.
 */

public class TeacherTimerTask extends TimerTask{

  ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
  int i = 19;

  @Override
  public void run() {

    Call<LocationTeacher> call = apiInterface.deleteTeacherLocation(i);
    call.enqueue(new Callback<LocationTeacher>() {
      @Override
      public void onResponse(Call<LocationTeacher> call, Response<LocationTeacher> response) {
        Log.i("Info", "Deleted");
      }

      @Override
      public void onFailure(Call<LocationTeacher> call, Throwable t) {
        Log.i("Info", "Deletion failed");
      }
    });

  }
}
