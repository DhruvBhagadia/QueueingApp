package com.djunicode.queuingapp.fragment;


import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.SessionManagement.SessionManager;
import com.djunicode.queuingapp.activity.LogInActivity;
import com.djunicode.queuingapp.activity.StudentScreenActivity;
import com.djunicode.queuingapp.activity.TeacherScreenActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpStudentFragment extends Fragment {

  private EditText usernameEditText, sapIDEditText, passwordEditText;
  private Spinner departmentSpinner, yearSpinner, batchSpinner;
  private CardView signUpStudentButton;
  private TextInputLayout studentSignUpinputLayoutUsername, studentSignUpinputLayoutSAPId,
      studentSignUpinputLayoutPassword, studentSignUpinputLayoutDepartment,
      studentSignUpinputLayoutYear, batchSpinnerLayout;
  private SharedPreferences sp_student, sp_teacher;
  // Session Manager Class
  SessionManager session;

  public SignUpStudentFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_sign_up_student, container, false);

    studentSignUpinputLayoutUsername = (TextInputLayout) view.findViewById(R.id.signUp_student_username);
    studentSignUpinputLayoutSAPId = (TextInputLayout) view.findViewById(R.id.signUp_student_SAPId);
    studentSignUpinputLayoutPassword = (TextInputLayout) view.findViewById(R.id.signUp_student_password);
    studentSignUpinputLayoutDepartment = (TextInputLayout) view.findViewById(
        R.id.signUp_student_department);
    studentSignUpinputLayoutYear = (TextInputLayout) view.findViewById(R.id.signUp_student_year);
    batchSpinnerLayout = (TextInputLayout) view.findViewById(R.id.batchTextInputLayout);
    usernameEditText = (EditText) view.findViewById(R.id.usernameEditText);
    sapIDEditText = (EditText) view.findViewById(R.id.sapIDEditText);
    passwordEditText = (EditText) view.findViewById(R.id.passwordEditText);
    departmentSpinner = (Spinner) view.findViewById(R.id.departmentSpinner);
    yearSpinner = (Spinner) view.findViewById(R.id.yearSpinner);
    batchSpinner = (Spinner) view.findViewById(R.id.batchSpinner);
    signUpStudentButton = (CardView) view.findViewById(R.id.signUpStudentButton);

    //studentSignUpinputLayoutUsername.setError(getString(R.string.err_msg_username));

    String username = usernameEditText.getText().toString();
    String sapID = sapIDEditText.getText().toString();
    String password = passwordEditText.getText().toString();
    sp_student = getActivity().getSharedPreferences("Student", MODE_PRIVATE);
    sp_teacher = getActivity().getSharedPreferences("Teacher", MODE_PRIVATE);

    // To remove Locally stored variables remove the below comment

    /*Editor editor_student = sp_student.edit();
    Editor editor_teacher = sp_teacher.edit();

    editor_student.remove("student_username");
    editor_student.remove("student_password");
    editor_student.commit();

    editor_teacher.remove("teacher_username");
    editor_teacher.remove("teacher_password");
    editor_teacher.commit();*/

    //if SharedPreferences contains username and password then redirect to Home activity
    if (sp_student.contains("student_username") && sp_student.contains("student_password")) {
      Intent in = new Intent(getContext(), StudentScreenActivity.class);
      startActivity(in);

    } else {

      if (sp_teacher.contains("teacher_username") && sp_teacher.contains("teacher_password")) {
        Toast.makeText(getActivity(), "I got it!", Toast.LENGTH_SHORT).show();
        Intent in = new Intent(getContext(), TeacherScreenActivity.class);
        // StudentScreenActivity just for demo till the time teacher fragments are not ready
        startActivity(in);

      }

    }

    departmentSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String department = parent.getItemAtPosition(position).toString();
        Log.i("Dept", department);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    yearSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String year = parent.getItemAtPosition(position).toString();
        Log.i("Year", year);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    batchSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String batch = parent.getItemAtPosition(position).toString();
        Log.i("Batch", batch);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    // Session Manager
    session = new SessionManager(getContext(), "Student");

    signUpStudentButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (validSignUp()) {
          session.createLoginSession(usernameEditText.getText().toString(),
              passwordEditText.getText().toString());
          Intent intent = new Intent(getActivity(), StudentScreenActivity.class);
          startActivity(intent);
//          finish();
          Toast.makeText(getContext(), usernameEditText.getText().toString(),
              Toast.LENGTH_SHORT).show();
          Toast.makeText(getContext(), passwordEditText.getText().toString(),
              Toast.LENGTH_SHORT).show();
        }
      }
    });

    return view;
  }

  private Boolean validSignUp() {

    if (!validateUsername()) {
      return false;
    }

    if (!validateDepartment()) {
      return false;
    }

    if (!validateYear()) {
      return false;
    }

    if (!validateSAPId()) {
      return false;
    }

    if (!validatePassword()) {
      return false;
    }

    return true;
  }

  private boolean validateUsername() {
    if (usernameEditText.getText().toString().trim().isEmpty()) {
      studentSignUpinputLayoutUsername.setError(getString(R.string.err_msg_username));
      requestFocus(usernameEditText);
      return false;
    } else if ((usernameEditText.getText().toString().length() < 5)) {
      studentSignUpinputLayoutUsername
          .setError(getString(R.string.err_msg_username_inappropriate_length));
      requestFocus(usernameEditText);
      return false;
    } else {
      studentSignUpinputLayoutUsername.setErrorEnabled(false);
    }

    return true;
  }

  private boolean validateSAPId() {
    if (sapIDEditText.getText().toString().trim().isEmpty()) {
      studentSignUpinputLayoutSAPId.setError(getString(R.string.err_msg_SAPId));
      requestFocus(sapIDEditText);
      return false;
    } else if ((sapIDEditText.getText().toString().length() != 11)) {
      studentSignUpinputLayoutSAPId
          .setError(getString(R.string.err_msg_SAPId_inappropriate_length));
      requestFocus(sapIDEditText);
      return false;
    } else {
      studentSignUpinputLayoutSAPId.setErrorEnabled(false);
    }

    return true;
  }

  private boolean validatePassword() {
    if (passwordEditText.getText().toString().trim().isEmpty()) {
      studentSignUpinputLayoutPassword.setError(getString(R.string.err_msg_password));
      requestFocus(passwordEditText);
      return false;
    } else if ((passwordEditText.getText().toString().length() < 5)) {
      studentSignUpinputLayoutPassword
          .setError(getString(R.string.err_msg_password_inappropriate_length));
      requestFocus(passwordEditText);
      return false;
    } else {
      studentSignUpinputLayoutPassword.setErrorEnabled(false);
    }

    return true;
  }

  private boolean validateDepartment() {
    if (departmentSpinner.getSelectedItemPosition() == 0) {
      studentSignUpinputLayoutDepartment.setError(getString(R.string.err_msg_department));
      //requestFocusSpinner(departmentSpinner);
      return false;
    } else {
      studentSignUpinputLayoutDepartment.setError("");
      return true;
    }
  }

  private boolean validateYear() {
    if (yearSpinner.getSelectedItemPosition() == 0) {
      studentSignUpinputLayoutYear.setError(getString(R.string.err_msg_year));
      //requestFocusSpinner(yearSpinner);
      return false;
    } else {
      studentSignUpinputLayoutYear.setError("");
      return true;
    }
  }

  private void requestFocus(View view) {
    if (view.requestFocus()) {
      getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
  }
}
