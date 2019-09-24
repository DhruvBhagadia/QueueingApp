package com.djunicode.queuingapp.fragment;


import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.activity.SightedTeacherActivity;
import com.djunicode.queuingapp.model.LocationTeacher;
import com.djunicode.queuingapp.model.TeacherListModel;
import com.djunicode.queuingapp.model.TeacherModel;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindTeacherFragment extends Fragment {

  private Spinner subjectSpinner, teacherSpinner, semSpinner;
  private CardView findTeacherButton, sightedTeacherButton;
  ApiInterface apiInterface;
  String location2 = "Dhruv";
  String dept;
  String teacherLocation, floor, room;
  int teacher_location_id;
  private List<String> list;
  ArrayAdapter<String> teacher_adapter;
  String teacher_selected;
  private Resources resources;

  public FindTeacherFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_find_teacher, container, false);
    apiInterface = ApiClient.getClient().create(ApiInterface.class);

    String[] sem = {"Select", "I", "II", "III", "IV", "V", "VI", "VII", "VIII"};

    String[] sem3_Sub = {"Select", "DLDA", "DS"};

    String[] sem4_Sub = {"Select", "AOA", "OS", "COA", "MATHS-IV", "CG"};

    String[] array = {"Select", "one", "two", "three", "four", "five", "six", "seven", "eight",
        "nine", "ten"};

    list = new ArrayList<>();

    subjectSpinner = (Spinner) view.findViewById(R.id.subjectSpinner);
    teacherSpinner = (Spinner) view.findViewById(R.id.teacherSpinner);
    semSpinner = (Spinner) view.findViewById(R.id.semSpinner);
    findTeacherButton = (CardView) view.findViewById(R.id.findTeacherButton);
    sightedTeacherButton = (CardView) view.findViewById(R.id.sightedTeacherButton);

    resources = getResources();

    findTeacherButton.setEnabled(false);

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
        android.R.layout.simple_spinner_dropdown_item, array);

    ArrayAdapter<String> sem_adapter = new ArrayAdapter<String>(getContext(),
        android.R.layout.simple_spinner_dropdown_item, sem);

    final ArrayAdapter<String> adapter_s3 = new ArrayAdapter<String>(getContext(),
        android.R.layout.simple_spinner_dropdown_item, sem3_Sub);

    final ArrayAdapter<String> adapter_s4 = new ArrayAdapter<String>(getContext(),
        android.R.layout.simple_spinner_dropdown_item, sem4_Sub);

    semSpinner.setAdapter(sem_adapter);
    subjectSpinner.setAdapter(adapter);
    teacherSpinner.setAdapter(adapter);

    subjectSpinner.setEnabled(false);
    subjectSpinner.setAlpha(0.4f);

    teacherSpinner.setEnabled(false);
    teacherSpinner.setAlpha(0.4f);

    semSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
          subjectSpinner.setEnabled(true);
          subjectSpinner.setAlpha(1.0f);
          teacherSpinner.setEnabled(false);
          teacherSpinner.setAlpha(0.4f);
          findTeacherButton.setEnabled(false);
        }
        switch (position) {
          case 1:
            subjectSpinner.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.sem_1)));
            break;
          case 2:
            subjectSpinner.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.sem_2)));
            break;
          case 3:
            subjectSpinner.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.sem_3)));
            break;
          case 4:
            subjectSpinner.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.sem_4)));
            break;
          case 5:
            subjectSpinner.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.sem_5)));
            break;
          case 6:
            subjectSpinner.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.sem_6)));
            break;
          case 7:
            subjectSpinner.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.sem_7)));
            break;
          case 8:
            subjectSpinner.setAdapter(new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                resources.getStringArray(R.array.sem_8)));
            break;
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    subjectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
          Call<TeacherListModel> call = apiInterface.getTeachers(subjectSpinner.getSelectedItem()
              .toString());
          call.enqueue(new Callback<TeacherListModel>() {
            @Override
            public void onResponse(Call<TeacherListModel> call,
                Response<TeacherListModel> response) {
              try {
                list = response.body().getTeachers();
                Log.e("List", list.toString());
              } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
              }
              teacher_adapter = new ArrayAdapter<String>(getContext(),
                  android.R.layout.simple_spinner_dropdown_item, list);
              teacherSpinner.setAdapter(teacher_adapter);

              Log.e("teacherSpinnerAdapter", Integer.toString(teacherSpinner.getAdapter().getCount()));
              if(teacherSpinner.getAdapter().getCount() != 0) {
                teacherSpinner.setEnabled(true);
                teacherSpinner.setAlpha(1.0f);
              }
            }

            @Override
            public void onFailure(Call<TeacherListModel> call, Throwable t) {

            }
          });

          Toast.makeText(getContext(), parent.getItemAtPosition(position).toString(),
              Toast.LENGTH_SHORT).show();
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    teacherSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        teacher_selected = adapterView.getItemAtPosition(i).toString();
        findTeacherButton.setEnabled(true);
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    findTeacherButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Call<TeacherModel> call1 = apiInterface.getIdForTeacherFromName(teacher_selected);
        call1.enqueue(new Callback<TeacherModel>() {
          @Override
          public void onResponse(Call<TeacherModel> call, Response<TeacherModel> response) {
            if (response.isSuccessful()) {
              try {
                teacher_location_id = response.body().getLocation();
                getTLocation();
                Log.e("FindTeacherFragment", "success");
              } catch (Exception e) {
                AlertDialog.Builder builder = new Builder(getActivity());
                builder.setMessage("Location Unknown.")
                        .setPositiveButton("OK", null)
                        .show();
                //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
              }
            }
          }

          @Override
          public void onFailure(Call<TeacherModel> call, Throwable t) {
            Log.e("T_l", "failed");
          }
        });

        teacherLocation = "Prof. " + teacher_selected +
            " " + "Department: " + "Waiting for Teacher, please try again";
//          AlertDialog.Builder builder = new Builder(getActivity());
//          builder.setMessage(teacherLocation)
//              .setPositiveButton("OK", null)
//              .show();
      }
    });

    sightedTeacherButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SightedTeacherActivity.class);
        getContext().startActivity(intent);
      }
    });

    return view;
  }

  void getTLocation() {
    Log.e("T_l", Integer.toString(teacher_location_id));
    Call<LocationTeacher> call = apiInterface.getTeacherLocation(teacher_location_id);
    call.enqueue(new Callback<LocationTeacher>() {
      @Override
      public void onResponse(Call<LocationTeacher> call, Response<LocationTeacher> response) {
        if (response.isSuccessful()) {
          try {
            dept = response.body().getDepartment();
            floor = response.body().getFloor().toString();
            room = response.body().getRoom();
            teacherLocation = "Prof. " + teacher_selected +
                " was last seen in Department: " + dept + " at Floor: " + floor + " in Room: "
                + room;
            AlertDialog.Builder builder = new Builder(getActivity());
            builder.setMessage(teacherLocation)
                .setPositiveButton("OK", null)
                .show();
          } catch (Exception e) {
            AlertDialog.Builder builder = new Builder(getActivity());
            builder.setMessage("Location Unknown.")
                .setPositiveButton("OK", null)
                .show();
//            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
          }
          //+ " Floor: "
          //+ response.body().getFloor().toString() + " Room: " + response.body().getRoom();
        }
      }


      @Override
      public void onFailure(Call<LocationTeacher> call, Throwable t) {
        Log.e("Dhruv", "failure");
      }
    });
  }

}
