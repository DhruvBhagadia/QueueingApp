package com.djunicode.queuingapp.activity;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomNavigationView.OnNavigationItemSelectedListener;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuItem;

import android.view.Window;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.fragment.RecentsFragment;
import com.djunicode.queuingapp.fragment.TeacherLocationFragment;
import com.djunicode.queuingapp.fragment.TeacherSubmissionFragment;

public class TeacherScreenActivity extends AppCompatActivity {

  private final static String TAG = TeacherScreenActivity.class.getSimpleName();
  public static  BottomNavigationView bottomNavigationView;
  private Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
      getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
      getWindow().setExitTransition(new Explode());
      getWindow().setAllowEnterTransitionOverlap(true);
    }
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_teacher_screen);

    bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationViewTeacher);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if(savedInstanceState == null) loadTeacherSubmissionFragment();

    bottomNavigationView.setOnNavigationItemSelectedListener(
        new OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
              case R.id.action_submission_teacher:
                loadTeacherSubmissionFragment();
                return true;
              case R.id.action_location:
                loadTeacherLocationFragment();
                return true;
              case R.id.action_recents:
                loadRecentsFragment();
                return true;
            }
            return false;
          }
        });
  }

  private void loadTeacherSubmissionFragment(){
    TeacherSubmissionFragment fragment = new TeacherSubmissionFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.appear, R.anim.disappear,
        R.anim.appear, R.anim.disappear);
    transaction.replace(R.id.containerLayoutTeacher, fragment);
    transaction.addToBackStack(TAG);
    transaction.commit();
  }

  private void loadTeacherLocationFragment(){
    TeacherLocationFragment fragment = new TeacherLocationFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.appear, R.anim.disappear,
        R.anim.appear, R.anim.disappear);
    transaction.replace(R.id.containerLayoutTeacher, fragment);
    transaction.addToBackStack(TAG);
    transaction.commit();
  }

  private void loadRecentsFragment(){
    RecentsFragment fragment = new RecentsFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.appear, R.anim.disappear,
        R.anim.appear, R.anim.disappear);
    transaction.replace(R.id.containerLayoutTeacher, fragment);
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if(item.getItemId() == R.id.action_profile){
      Intent intent = new Intent(this, TeacherProfileActivity.class);
      if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP){
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        this.overridePendingTransition(0,0);
      } else {
        startActivity(intent);
        this.overridePendingTransition(0,0);
      }
    }
    return super.onOptionsItemSelected(item);
  }
}
