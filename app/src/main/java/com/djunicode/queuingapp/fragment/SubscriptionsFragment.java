package com.djunicode.queuingapp.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.customClasses.MultiSelectionSpinner;
import com.djunicode.queuingapp.customClasses.MultiSelectionSpinner.OnMultipleItemsSelectedListener;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionsFragment extends Fragment {

  private Spinner semesterSpinner;
  private MultiSelectionSpinner subjectSpinner, teacherSpinner;
  private FloatingActionButton subscriptionsFab;

  public SubscriptionsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);

    String[] array1 = {"Select Semester", "one", "two", "three", "four", "five", "six", "seven", "eight"};
    String[] array2 = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
    String[] array3 = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
        android.R.layout.simple_spinner_dropdown_item, array1);

    semesterSpinner = (Spinner) view.findViewById(R.id.semesterSpinner);
    subjectSpinner = (MultiSelectionSpinner) view.findViewById(R.id.subjectSpinner);
    teacherSpinner = (MultiSelectionSpinner) view.findViewById(R.id.teacherSpinner);
    subscriptionsFab = (FloatingActionButton) view.findViewById(R.id.subscriptionsFab);

    subjectSpinner.setEnabled(false);
    subjectSpinner.setAlpha(0.4f);
    subjectSpinner.setItems(array2);

    teacherSpinner.setEnabled(false);
    teacherSpinner.setAlpha(0.4f);
    teacherSpinner.setItems(array3);

    semesterSpinner.setAdapter(adapter);
    semesterSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String semester = parent.getItemAtPosition(position).toString();
        if(position != 0){
          subjectSpinner.setEnabled(true);
          subjectSpinner.setAlpha(1.0f);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(getContext(), "Please select the semester!", Toast.LENGTH_SHORT).show();
      }
    });

    subjectSpinner.setListener(new OnMultipleItemsSelectedListener() {
      @Override
      public void selectedIndices(List<Integer> indices) {

      }

      @Override
      public void selectedStrings(List<String> strings) {
        teacherSpinner.setEnabled(true);
        teacherSpinner.setAlpha(1.0f);
        Toast.makeText(getContext(), "Subjects:" + strings.toString(), Toast.LENGTH_LONG).show();
      }
    });

    teacherSpinner.setListener(new OnMultipleItemsSelectedListener() {
      @Override
      public void selectedIndices(List<Integer> indices) {

      }

      @Override
      public void selectedStrings(List<String> strings) {
        Toast.makeText(getContext(), "Teachers:" + strings.toString(), Toast.LENGTH_LONG).show();
      }
    });

    return view;
  }
}
