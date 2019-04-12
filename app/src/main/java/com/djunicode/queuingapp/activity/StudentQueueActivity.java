package com.djunicode.queuingapp.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.data.ActiveQueuesDbHelper;
import com.djunicode.queuingapp.model.ActiveStudentQueues;
import com.djunicode.queuingapp.model.StudentQueue;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentQueueActivity extends AppCompatActivity {

    private TextView queuePositionTextView, positionTV, removedFromQueueTextView, queueClosedTextView;
    private CardView doneButton, queueNumber, removedFromQueue, queueClosed;
    private Animation upToDown, downToUp, scale;
    private Handler handler;
    private ApiInterface apiInterface;
    private ProgressBar loadingIndicator;
    private Integer queueId, location = 0;
    private String sapId, subject, startTime, endTime;
    private SQLiteDatabase db;
    private ActiveQueuesDbHelper dbHelper;
    private boolean first, kill = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_queue);

        queuePositionTextView = (TextView) findViewById(R.id.queuePositionTextView);
        removedFromQueueTextView = (TextView) findViewById(R.id.removedFromQueueTextView);
        queueClosedTextView = (TextView) findViewById(R.id.queueClosedTextView);
        positionTV = (TextView) findViewById(R.id.positionTV);
        doneButton = (CardView) findViewById(R.id.doneButton);
        queueNumber = (CardView) findViewById(R.id.queueNumberCardView);
        removedFromQueue = (CardView) findViewById(R.id.removedFromQueueCardView);
        queueClosed = (CardView) findViewById(R.id.queueClosedCardView);
        loadingIndicator = (ProgressBar) findViewById(R.id.loadingIndicator);
        handler = new Handler();
        dbHelper = new ActiveQueuesDbHelper(this);
        db = dbHelper.getWritableDatabase();

        Intent intent = getIntent();
        queueId = intent.getIntExtra("id", 0);
        sapId = intent.getStringExtra("sapId");
        first = intent.getBooleanExtra("first", false);

        upToDown = AnimationUtils.loadAnimation(this, R.anim.up_to_down);
        downToUp = AnimationUtils.loadAnimation(this, R.anim.down_to_up);
        scale = AnimationUtils.loadAnimation(this, R.anim.scale);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        positionTV.setAnimation(upToDown);
        doneButton.setAnimation(downToUp);
        queueNumber.setAnimation(scale);
        loadingIndicator.setVisibility(View.VISIBLE);

        startRepeatingTask();

        doneButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRepeatingTask();
                finish();
            }
        });
    }

    private Runnable statusChecker = new Runnable() {
        @Override
        public void run() {
            if (kill)
                return;
            updateStudentList();
            handler.postDelayed(statusChecker, 10000);
            // Toast.makeText(StudentQueueActivity.this, "Location Updated", Toast.LENGTH_SHORT).show();
        }
    };

    void startRepeatingTask() {
        statusChecker.run();
    }

    void stopRepeatingTask() {
        handler.removeCallbacks(statusChecker);
        exitQueue();
    }

    void updateStudentList() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<StudentQueue> call = apiInterface.getUpdatedStudentLocation(queueId, sapId);
        call.enqueue(new Callback<StudentQueue>() {
            @Override
            public void onResponse(Call<StudentQueue> call, Response<StudentQueue> response) {
                Log.e("Deleted QueueId", String.valueOf(queueId));
                try{
                    int index = response.body().getIndex();
                    Log.e("Index", Integer.toString(index));
                    subject = response.body().getData().getSubject();
                    startTime = response.body().getData().getStartTime();
                    endTime = response.body().getData().getEndTime();
                    location = response.body().getData().getLocation();
                    Log.e("null logs", subject + " " + endTime + " " + startTime);
                    loadingIndicator.setVisibility(View.GONE);
                    queuePositionTextView.setVisibility(View.VISIBLE);
                    if(index == 0) {
                        queueNumber.setVisibility(View.INVISIBLE);
                        removedFromQueue.setVisibility(View.VISIBLE);
                        removedFromQueueTextView.setText("You have been removed from the Queue :(");
                    }
                    else
                        queuePositionTextView.setText(Integer.toString(index));
                } catch (Exception e) {
                    e.printStackTrace();
                    queueNumber.setVisibility(View.INVISIBLE);
                    queueClosed.setVisibility(View.VISIBLE);
                    queueClosedTextView.setText("The submission is ended by the teacher");
                }
            }

            @Override
            public void onFailure(Call<StudentQueue> call, Throwable t) {

            }
        });
    }

    private void exitQueue() {
        Call<StudentQueue> call = apiInterface.deleteStudentFromQueue(queueId, sapId);
        call.enqueue(new Callback<StudentQueue>() {
            @Override
            public void onResponse(Call<StudentQueue> call, Response<StudentQueue> response) {
                Toast.makeText(StudentQueueActivity.this, "Exited the Queue!", Toast.LENGTH_SHORT).show();
                dbHelper.deleteQueue(queueId);
                kill = true;
            }

            @Override
            public void onFailure(Call<StudentQueue> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
//    new AlertDialog.Builder(this)
//            .setIcon(android.R.drawable.ic_dialog_alert)
//            .setTitle("You will be removed from the Queue")
//            .setMessage("Are you sure you want to exit?")
//            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//              @Override
//              public void onClick(DialogInterface dialog, int which) {
//                stopRepeatingTask();
//                finish();
//              }
//            }).setNegativeButton("No", null).show();

//        startTime = "1:00:00";
//        endTime = "3:00:00";
//        subject = "Test";
        if (first){
            ActiveStudentQueues activeStudentQueues = new ActiveStudentQueues(queueId, startTime,
                    endTime, location, subject);
            Log.e("StudentQueueActivity", String.valueOf(queueId) + " " +  startTime + " "
                    + endTime + " " + location + " " + subject);
            dbHelper.addQueue(activeStudentQueues);
        }
        handler.removeCallbacks(statusChecker);
        kill = true;
        finish();
    }
}
