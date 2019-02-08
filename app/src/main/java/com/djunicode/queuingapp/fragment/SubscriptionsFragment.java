package com.djunicode.queuingapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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
import com.djunicode.queuingapp.customClasses.MultiSelectionSpinner;
import com.djunicode.queuingapp.customClasses.MultiSelectionSpinner.OnMultipleItemsSelectedListener;
import com.djunicode.queuingapp.model.StudentSubscriptions;
import com.djunicode.queuingapp.model.TeachersList;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubscriptionsFragment extends Fragment {

    private Spinner semesterSpinner;
    private MultiSelectionSpinner subjectSpinner, teacherSpinner;
    private FloatingActionButton subscriptionsFab;
    private ApiInterface apiInterface;
    private Integer id;
    private List<String> subscriptionList;
    private List<String> teachers;
    private SharedPreferences preferences;
    private Resources resources;

    public SubscriptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subscriptions, container, false);

        String[] array1 = {"Select Semester", "one", "two", "three", "four", "five", "six", "seven",
                "eight"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, array1);

        semesterSpinner = (Spinner) view.findViewById(R.id.semesterSpinner);
        subjectSpinner = (MultiSelectionSpinner) view.findViewById(R.id.subjectSpinner);
        teacherSpinner = (MultiSelectionSpinner) view.findViewById(R.id.teacherSpinner);
        subscriptionsFab = (FloatingActionButton) view.findViewById(R.id.subscriptionsFab);
        preferences = getActivity()
                .getSharedPreferences("Student", Context.MODE_PRIVATE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        resources = getResources();

        subjectSpinner.setEnabled(false);
        subjectSpinner.setAlpha(0.4f);

        teacherSpinner.setEnabled(false);
        teacherSpinner.setAlpha(0.4f);

        semesterSpinner.setAdapter(adapter);
        semesterSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String semester = parent.getItemAtPosition(position).toString();
                if (position != 0) {
                    subjectSpinner.setEnabled(true);
                    subjectSpinner.setAlpha(1.0f);
                }
                switch (position) {
                    case 1:
                        subjectSpinner.setItems(resources.getStringArray(R.array.sem_1));
                        break;
                    case 2:
                        subjectSpinner.setItems(resources.getStringArray(R.array.sem_2));
                        break;
                    case 3:
                        subjectSpinner.setItems(resources.getStringArray(R.array.sem_3));
                        break;
                    case 4:
                        subjectSpinner.setItems(resources.getStringArray(R.array.sem_4));
                        break;
                    case 5:
                        subjectSpinner.setItems(resources.getStringArray(R.array.sem_5));
                        break;
                    case 6:
                        subjectSpinner.setItems(resources.getStringArray(R.array.sem_6));
                        break;
                    case 7:
                        subjectSpinner.setItems(resources.getStringArray(R.array.sem_7));
                        break;
                    case 8:
                        subjectSpinner.setItems(resources.getStringArray(R.array.sem_8));
                        break;
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
            public void selectedStrings(List<String> selectedSubjects) {
                teacherSpinner.setEnabled(true);
                teacherSpinner.setAlpha(1.0f);
                //Toast.makeText(getContext(), "Subjects:" + selectedSubjects.toString(), Toast.LENGTH_LONG)
                //  .show();
                teachers = new ArrayList<>();
                for (int i = 0; i < selectedSubjects.size(); i++) {
                    Call<TeachersList> call = apiInterface.getTeachersList(selectedSubjects.get(i));
                    call.enqueue(new Callback<TeachersList>() {
                        @Override
                        public void onResponse(Call<TeachersList> call, Response<TeachersList> response) {
                            try {
                                Log.e("queues/subject/", response.body().getTeachers().toString());
                                List<String> teacherList = response.body().getTeachers();
                                for (int i = 0; i < teacherList.size(); i++) {
                                    teachers.add(teacherList.get(i));
                                    Set<String> teachersSet = new HashSet<>(teachers);
                                    teachers.clear();
                                    teachers.addAll(teachersSet);
                                    teacherSpinner.setItems(teachers);
                                }
                            } catch (Exception e) {
                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<TeachersList> call, Throwable t) {

                        }
                    });
                }
            }
        });

        teacherSpinner.setListener(new OnMultipleItemsSelectedListener() {
            @Override
            public void selectedIndices(List<Integer> indices) {

            }

            @Override
            public void selectedStrings(List<String> strings) {
                subscriptionList = strings;
                //  Toast.makeText(getContext(), "Teachers:" + strings.toString(), Toast.LENGTH_LONG).show();
            }
        });

        subscriptionsFab.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.e("RegId", token);

                JSONArray jsonArray = new JSONArray(subscriptionList);
                Log.e("JSONArray", jsonArray.toString());
                Log.e("StudentID", String.valueOf(preferences.getInt("studentID", 0)));
                Call<StudentSubscriptions> call = apiInterface
                        .setStudentSubscriptions(preferences.getInt("studentID", 0), jsonArray.toString());
                call.enqueue(new Callback<StudentSubscriptions>() {
                    @Override
                    public void onResponse(Call<StudentSubscriptions> call,
                                           Response<StudentSubscriptions> response) {
                        try {
                            Log.e("Subs", response.body().getSubscription().toString());
                            Toast.makeText(getContext(), "Subscribed successfully", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentSubscriptions> call, Throwable t) {

                    }
                });
            }
        });

        return view;
    }
}
