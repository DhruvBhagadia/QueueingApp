package com.djunicode.queuingapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.adapter.RemoveStudentAdapter;
import com.djunicode.queuingapp.customClasses.RecyclerItemTouchHelper;
import com.djunicode.queuingapp.customClasses.RemoveStudentTouchHelper;
import com.djunicode.queuingapp.data.QueuesDbHelper;
import com.djunicode.queuingapp.model.StudentQueue;
import com.djunicode.queuingapp.model.TeacherCreateNew;
import com.djunicode.queuingapp.receiver.QueueReceiver;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentListActivity extends AppCompatActivity implements
        RemoveStudentTouchHelper.RecyclerItemTouchHelperListener{

  private RecyclerView studentsRecyclerView;
  private FloatingActionButton cancelFab;
  private Handler handler;
  private ArrayList<String> queueList;
  private RemoveStudentAdapter adapter;
  private ApiInterface apiInterface;
  private ProgressBar loadingIndicator;
  private TextView emptyQueueTextView;
  private Intent intent;
  private int queueId;
  private QueuesDbHelper dbHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_student_list);

    studentsRecyclerView = (RecyclerView) findViewById(R.id.studentsRecyclerView);
    cancelFab = (FloatingActionButton) findViewById(R.id.cancelSubFab);
    loadingIndicator = (ProgressBar) findViewById(R.id.loadingIndicator);
    emptyQueueTextView = (TextView) findViewById(R.id.emptyQueueTextView);
    queueList = new ArrayList<>();
    handler = new Handler();
    dbHelper = new QueuesDbHelper(this);
    intent = getIntent();
    queueId = intent.getIntExtra("id", 1);

    loadingIndicator.setVisibility(View.VISIBLE);
    startRepeatingTask();

    adapter = new RemoveStudentAdapter(StudentListActivity.this, queueList);

    studentsRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    studentsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    studentsRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(),
            DividerItemDecoration.VERTICAL));
    studentsRecyclerView.setAdapter(adapter);

    ItemTouchHelper.SimpleCallback simpleCallback = new RemoveStudentTouchHelper(0,
            ItemTouchHelper.LEFT, this);
    new ItemTouchHelper(simpleCallback).attachToRecyclerView(studentsRecyclerView);

    cancelFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        new AlertDialog.Builder(StudentListActivity.this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Stop the submission.")
                .setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    stopRepeatingTask();
                    deleteQueue();
                    finish();
                  }
                }).setNegativeButton("No", null).show();
      }
    });

  }

  @Override
  public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
    if (viewHolder instanceof RemoveStudentAdapter.MyViewHolder) {
      removeStudent(viewHolder.getAdapterPosition());
    }
  }

  private Runnable statusChecker = new Runnable() {
    @Override
    public void run() {
      updateStudentList();
      handler.postDelayed(statusChecker, 10000);
      Toast.makeText(StudentListActivity.this, "Queue Updated", Toast.LENGTH_SHORT).show();
    }
  };

  void startRepeatingTask() {
    statusChecker.run();
  }

  void stopRepeatingTask() {
    handler.removeCallbacks(statusChecker);
  }

  void updateStudentList() {
    apiInterface = ApiClient.getClient().create(ApiInterface.class);
    Call<StudentQueue> call = apiInterface.getUpdatedQueue(queueId);
    call.enqueue(new Callback<StudentQueue>() {
      @Override
      public void onResponse(Call<StudentQueue> call, Response<StudentQueue> response) {
        queueList = response.body().getItems();
        if(queueList.size() == 0){
          emptyQueueTextView.setVisibility(View.VISIBLE);
        } else {
          emptyQueueTextView.setVisibility(View.GONE);
        }
        Log.e("QueueItems", queueList.toString());
        adapter = new RemoveStudentAdapter(StudentListActivity.this, queueList);
        studentsRecyclerView.setAdapter(adapter);
        loadingIndicator.setVisibility(View.GONE);
        studentsRecyclerView.setVisibility(View.VISIBLE);
      }

      @Override
      public void onFailure(Call<StudentQueue> call, Throwable t) {

      }
    });
  }

  private void deleteQueue(){
    Call<TeacherCreateNew> call1 = apiInterface
        .deleteQueueLinkFromTeacher(37, queueId);
    call1.enqueue(new Callback<TeacherCreateNew>() {
      @Override
      public void onResponse(Call<TeacherCreateNew> call,
          Response<TeacherCreateNew> response) {
        Call<TeacherCreateNew> call2 = apiInterface.deleteQueue(queueId);
        call2.enqueue(new Callback<TeacherCreateNew>() {
          @Override
          public void onResponse(Call<TeacherCreateNew> call,
              Response<TeacherCreateNew> response) {
            dbHelper.deleteQueueWithId(queueId);
          }

          @Override
          public void onFailure(Call<TeacherCreateNew> call, Throwable t) {

          }
        });
      }

      @Override
      public void onFailure(Call<TeacherCreateNew> call, Throwable t) {

      }
    });
  }

  void removeStudent(final int pos){

    Call<StudentQueue> call = apiInterface.deleteStudentFromQueue(queueId, queueList.get(pos));
                call.enqueue(new Callback<StudentQueue>() {
                  @Override
                  public void onResponse(Call<StudentQueue> call, Response<StudentQueue> response) {
                    Toast.makeText(StudentListActivity.this, "Removed from the queue!", Toast.LENGTH_SHORT).show();
                  }

                  @Override
                  public void onFailure(Call<StudentQueue> call, Throwable t) {

                  }
                });
     adapter.removeItem(pos);
  }

  @Override
  public void onBackPressed() {
    new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Stop the submission.")
            .setMessage("Are you sure?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                stopRepeatingTask();
                deleteQueue();
                finish();
              }
            }).setNegativeButton("No", null).show();
  }

}
