package com.djunicode.queuingapp.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.djunicode.queuingapp.R;

public class StudentListActivity extends AppCompatActivity {

  private ListView studentsList;
  private FloatingActionButton cancelFab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_student_list);

    String[] array = {"60004160001", "60004160002", "60004160003", "60004160004", "60004160005",
        "60004160006", "60004160007", "60004160008", "60004160009", "60004160010"};

    studentsList = (ListView) findViewById(R.id.studentsList);
    cancelFab = (FloatingActionButton) findViewById(R.id.cancelSubFab);

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
        R.layout.list_row, array);

    studentsList.setAdapter(adapter);

    cancelFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }
}
