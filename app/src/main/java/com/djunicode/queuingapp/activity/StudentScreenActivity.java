package com.djunicode.queuingapp.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.fragment.FindTeacherFragment;
import com.djunicode.queuingapp.fragment.SubmissionFragment;
import com.djunicode.queuingapp.fragment.SubscriptionsFragment;

public class StudentScreenActivity extends AppCompatActivity {

  private final static String TAG = LogInActivity.class.getSimpleName();

  private BottomNavigationView bottomNavigationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_student_screen);

    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

    if(savedInstanceState == null){
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
            }
            return true;
          }
        });
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

  @Override
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
