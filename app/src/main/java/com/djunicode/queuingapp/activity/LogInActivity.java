package com.djunicode.queuingapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.SessionManagement.SessionManager;
import com.djunicode.queuingapp.fragment.FindTeacherFragment;
import com.djunicode.queuingapp.fragment.LogInStudentFragment;
import com.djunicode.queuingapp.fragment.LogInTeacherFragment;
import com.djunicode.queuingapp.fragment.SignUpStudentFragment;
import com.djunicode.queuingapp.fragment.SignUpTeacherFragment;

public class LogInActivity extends AppCompatActivity {

  private final static String TAG = LogInActivity.class.getSimpleName();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_log_in);

    Intent intent = getIntent();
    String user = intent.getStringExtra("user");
    String userLogin = intent.getStringExtra("userLogin");

    if (user != null && user.equals("student")) {
      loadSignUpStudentFragment();
    } else if (user != null && user.equals("teacher")) {
      loadSignUpTeacherFragment();
    } else if (userLogin != null && userLogin.equals("student")) {
      loadLogInStudentFragment();
    } else if (userLogin != null && userLogin.equals("teacher")) {
      loadLogInTeacherFragment();
    }

  }

  private void loadLogInTeacherFragment() {
    LogInTeacherFragment fragment = new LogInTeacherFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
        R.anim.enter_from_left, R.anim.exit_to_right);
    transaction.replace(R.id.containerFrame, fragment);
    transaction.addToBackStack(TAG);
    transaction.commit();
  }

  private void loadLogInStudentFragment() {
    LogInStudentFragment fragment = new LogInStudentFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
        R.anim.enter_from_left, R.anim.exit_to_right);
    transaction.replace(R.id.containerFrame, fragment);
    transaction.addToBackStack(TAG);
    transaction.commit();
  }

  private void loadSignUpTeacherFragment() {
    SignUpTeacherFragment fragment = new SignUpTeacherFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
        R.anim.enter_from_left, R.anim.exit_to_right);
    transaction.replace(R.id.containerFrame, fragment);
    transaction.addToBackStack(TAG);
    transaction.commit();
  }

  private void loadSignUpStudentFragment() {
    SignUpStudentFragment fragment = new SignUpStudentFragment();
    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left,
        R.anim.enter_from_left, R.anim.exit_to_right);
    transaction.replace(R.id.containerFrame, fragment);
    transaction.addToBackStack(TAG);
    transaction.commit();
  }

  @Override
  public void onBackPressed() {
    finish();
  }
}
