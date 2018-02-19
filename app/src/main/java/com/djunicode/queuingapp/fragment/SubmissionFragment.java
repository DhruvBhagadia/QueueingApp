package com.djunicode.queuingapp.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.activity.StudentScreenActivity;
import com.djunicode.queuingapp.customClasses.QueueDialogClass;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmissionFragment extends Fragment {

  private Spinner subjectSpinner, teacherNameSpinner;
  private FloatingActionButton fab;

  public SubmissionFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_submission, container, false);

    String[] array = {"Select", "one", "two", "three", "four", "five", "six", "seven", "eight",
        "nine", "ten"};
    subjectSpinner = (Spinner) view.findViewById(R.id.subjectSpinner);
    teacherNameSpinner = (Spinner) view.findViewById(R.id.teacherNameSpinner);
    fab = (FloatingActionButton) view.findViewById(R.id.submissionFab);

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
        android.R.layout.simple_spinner_dropdown_item, array);

    subjectSpinner.setAdapter(adapter);
    teacherNameSpinner.setAdapter(adapter);

    teacherNameSpinner.setEnabled(false);
    teacherNameSpinner.setAlpha(0.4f);

    fab.setEnabled(false);

    subjectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0){
          teacherNameSpinner.setEnabled(true);
          teacherNameSpinner.setAlpha(1.0f);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    teacherNameSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0){
          fab.setEnabled(true);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });


    fab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        QueueDialogClass queueDialog = new QueueDialogClass(getActivity());
        queueDialog.show();
      }
    });
    return view;
  }

}
