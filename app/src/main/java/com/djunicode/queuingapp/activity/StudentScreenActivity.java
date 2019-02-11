package com.djunicode.queuingapp.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.fragment.ActiveStudentQueues;
import com.djunicode.queuingapp.fragment.FindTeacherFragment;
import com.djunicode.queuingapp.fragment.SubmissionFragment;
import com.djunicode.queuingapp.fragment.SubscriptionsFragment;

import com.djunicode.queuingapp.service.MyFirebaseInstanceIdService;
import com.djunicode.queuingapp.service.MyFirebaseMessagingService;
import com.djunicode.queuingapp.utils.NotificationUtils;
//import com.google.firebase.messaging.FirebaseMessaging;

public class StudentScreenActivity extends AppCompatActivity {

    private final static String TAG = LogInActivity.class.getSimpleName();

    private BottomNavigationView bottomNavigationView;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_screen);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);


        if (savedInstanceState == null) {
            loadSubmissionFragment();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_submission:
                                loadSubmissionFragment();
                                return true;
                            case R.id.action_subscriptions:
                                loadSubscriptionsFragment();
                                return true;
                            case R.id.action_find_teacher:
                                loadFindTeacherFragment();
                                return true;
                            case R.id.action_active_queues:
                                loadActiveQueuesFragment();
                                return true;
                        }
                        return true;
                    }
                });

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(MyFirebaseInstanceIdService.REGISTRATION_COMPLETE)) {
//          FirebaseMessaging.getInstance().subscribeToTopic("global");

                    displayFirebaseRegId();
                } else if (intent.getAction().equals(MyFirebaseMessagingService.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            }
        };
        displayFirebaseRegId();
    }

    private void displayFirebaseRegId() {
        SharedPreferences preferences = getApplicationContext()
                .getSharedPreferences("com.djunicode.queuingapp", MODE_PRIVATE);
        Log.e("Firebase RegId", preferences.getString("regId", "empty"));
    }

    private void loadSubmissionFragment() {
        SubmissionFragment fragment = new SubmissionFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.appear, R.anim.disappear,
                R.anim.appear, R.anim.disappear);
        transaction.replace(R.id.containerLayout, fragment);
        transaction.addToBackStack(TAG);
        transaction.commit();
    }

    private void loadSubscriptionsFragment() {
        SubscriptionsFragment fragment = new SubscriptionsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.appear, R.anim.disappear,
                R.anim.appear, R.anim.disappear);
        transaction.replace(R.id.containerLayout, fragment);
        transaction.addToBackStack(TAG);
        transaction.commit();
    }

    private void loadFindTeacherFragment() {
        FindTeacherFragment fragment = new FindTeacherFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.appear, R.anim.disappear,
                R.anim.appear, R.anim.disappear);
        transaction.replace(R.id.containerLayout, fragment);
        transaction.addToBackStack(TAG);
        transaction.commit();
    }

    private void loadActiveQueuesFragment(){
        ActiveStudentQueues fragment = new ActiveStudentQueues();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.appear, R.anim.disappear,
                R.anim.appear, R.anim.disappear);
        transaction.replace(R.id.containerLayout, fragment);
        transaction.addToBackStack(TAG);
        transaction.commit();
    }

    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(MyFirebaseInstanceIdService.REGISTRATION_COMPLETE));

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,
                new IntentFilter(MyFirebaseMessagingService.PUSH_NOTIFICATION));

        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit the app.")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }
}
