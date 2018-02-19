package com.djunicode.queuingapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.djunicode.queuingapp.R;

public class SightedTeacherActivity extends AppCompatActivity {

  private Toolbar toolbar;
  private Spinner subjectSpinner, teacherSpinner, departmentSpinner, roomSpinner;
  private CardView updateTeacherLocationButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sighted_teacher);

    toolbar = (Toolbar) findViewById(R.id.toolbar);
    subjectSpinner = (Spinner) findViewById(R.id.subjectSpinner);
    teacherSpinner = (Spinner) findViewById(R.id.teacherSpinner);
    departmentSpinner = (Spinner) findViewById(R.id.departmentSpinner);
    roomSpinner = (Spinner) findViewById(R.id.roomSpinner);
    updateTeacherLocationButton = (CardView) findViewById(R.id.updateTeacherLocationButton);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);

    String[] array = {"Select", "one", "two", "three", "four", "five", "six", "seven", "eight",
        "nine", "ten"};
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        android.R.layout.simple_spinner_dropdown_item, array);

    subjectSpinner.setAdapter(adapter);
    teacherSpinner.setAdapter(adapter);
    departmentSpinner.setAdapter(adapter);
    roomSpinner.setAdapter(adapter);

    teacherSpinner.setEnabled(false);
    teacherSpinner.setAlpha(0.4f);
    departmentSpinner.setEnabled(false);
    departmentSpinner.setAlpha(0.4f);
    roomSpinner.setEnabled(false);
    roomSpinner.setAlpha(0.4f);

    subjectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0){
          teacherSpinner.setEnabled(true);
          teacherSpinner.setAlpha(1.0f);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    teacherSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0){
          departmentSpinner.setEnabled(true);
          departmentSpinner.setAlpha(1.0f);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    departmentSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0){
          roomSpinner.setEnabled(true);
          roomSpinner.setAlpha(1.0f);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    finish();
    return super.onOptionsItemSelected(item);
  }
}
