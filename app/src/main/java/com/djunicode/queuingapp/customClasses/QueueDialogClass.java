package com.djunicode.queuingapp.customClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.activity.StudentQueueActivity;
import com.djunicode.queuingapp.model.Queue;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Ruturaj on 09-01-2018.
 */

public class QueueDialogClass extends Dialog {

  private Activity activity;
  private Button joinButton;
  private ImageView cancelButton;
  private TextView locationTextView, fromTextView, toTextView, batchTextView;
  ApiInterface apiInterface;
  private SharedPreferences sp_student;
  String sapid;
  public static int flag=0;

  public QueueDialogClass(Activity activity) {
    super(activity);
    this.activity = activity;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.queue_dialog_layout);

    locationTextView = (TextView) findViewById(R.id.locationTextView);
    fromTextView = (TextView) findViewById(R.id.fromTextView);
    toTextView = (TextView) findViewById(R.id.toTextView);
    batchTextView = (TextView) findViewById(R.id.batchTextView);
    joinButton = (Button) findViewById(R.id.joinButton);
    cancelButton = (ImageView) findViewById(R.id.cancelButton);
    sp_student = getContext().getSharedPreferences("Student", MODE_PRIVATE);
    sapid = sp_student.getString("student_sapid", "missing");
    if (sapid.equals("missing"))
      sapid = "90";

    apiInterface = ApiClient.getClient().create(ApiInterface.class);

    joinButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (flag == 0) {
          Intent intent = new Intent(activity, StudentQueueActivity.class);
          getContext().startActivity(intent);
          dismiss();
          Call<Queue> call = apiInterface.studentJoiningTheQueue(1, sapid);
          call.enqueue(new Callback<Queue>() {
            @Override
            public void onResponse(Call<Queue> call, Response<Queue> response) {
              if (response.isSuccessful()) {
                flag = 1;
                Log.i("studentJoining Res", response.body().toString());
              } else
                Log.i("studentJoining Res", response.errorBody().toString());
            }

            @Override
            public void onFailure(Call<Queue> call, Throwable t) {
              Log.i("studentJoining ResF", t.getMessage().toString());
            }
          });
        }
        else{
          Toast.makeText(getContext(), "You are already in the queue", Toast.LENGTH_SHORT).show();
        }
      }
    });

    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });

    locationTextView.setText("C1");
    fromTextView.setText("10:30am");
    toTextView.setText("12:00pm");
    batchTextView.setText("A2");
  }
}
