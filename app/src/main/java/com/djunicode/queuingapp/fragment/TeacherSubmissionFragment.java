package com.djunicode.queuingapp.fragment;


import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.activity.StudentListActivity;
import com.djunicode.queuingapp.activity.TeacherScreenActivity;
import com.djunicode.queuingapp.model.RecentEvents;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherSubmissionFragment extends Fragment {

  public static Spinner subjectSpinner, batchSpinner;
  private CardView fromTimePickerButton;
  private ImageButton studentsButton, timerButton;
  private FloatingActionButton createFab, startFab, cancelFab, createNewFab, editFab;
  private LinearLayout fabLL1, fabLL2, fabLL3, fabLL4;
  private CoordinatorLayout coordinatorLayout;
  private RelativeLayout relativeLayout;
  private Animation fabOpen, fabClose, rotateForward, rotateBackward;
  private Boolean isFabOpen, fromSelected, toSelected, studentsSelected;
  private String fromTime;
  private String toTime;
  public static List<RecentEvents> recentEventsList = new ArrayList<>();
  public static BottomSheetFragment bottomSheetFragment;
  private Bundle globalArgs;

  public TeacherSubmissionFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_teacher_submission, container, false);

    String[] array = {"Select", "one", "two", "three", "four", "five", "six", "seven", "eight",
        "nine", "ten"};

    final Bundle args = getArguments();

    isFabOpen = false;
    fromSelected = false;
    toSelected = false;
    studentsSelected = false;
    subjectSpinner = (Spinner) view.findViewById(R.id.subjectSpinner);
    batchSpinner = (Spinner) view.findViewById(R.id.batchSpinner);
    fromTimePickerButton = (CardView) view.findViewById(R.id.fromTimePickerButton);
    studentsButton = (ImageButton) view.findViewById(R.id.studentsButton);
    timerButton = (ImageButton) view.findViewById(R.id.timerButton);
    createFab = (FloatingActionButton) view.findViewById(R.id.createFab);
    startFab = (FloatingActionButton) view.findViewById(R.id.startFab);
    cancelFab = (FloatingActionButton) view.findViewById(R.id.cancelFab);
    createNewFab = (FloatingActionButton) view.findViewById(R.id.createNewFab);
    editFab = (FloatingActionButton) view.findViewById(R.id.editFab);
    coordinatorLayout = (CoordinatorLayout) view.findViewById(R.id.teacherCoordinatorLayout);
    relativeLayout = (RelativeLayout) view.findViewById(R.id.submissionRelativeLayout);
    fabLL1 = (LinearLayout) view.findViewById(R.id.fabLL1);
    fabLL2 = (LinearLayout) view.findViewById(R.id.fabLL2);
    fabLL3 = (LinearLayout) view.findViewById(R.id.fabLL3);
    fabLL4 = (LinearLayout) view.findViewById(R.id.fabLL4);
    fabOpen = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
    fabClose = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);
    rotateForward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_forward);
    rotateBackward = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_backward);

    if (args != null) {
      globalArgs = args;
      createFab.setImageResource(R.drawable.ic_upload);
    }

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
        android.R.layout.simple_spinner_dropdown_item, array);

    subjectSpinner.setAdapter(adapter);
    batchSpinner.setAdapter(adapter);

    batchSpinner.setEnabled(false);
    batchSpinner.setAlpha(0.4f);

    subjectSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position != 0) {
          batchSpinner.setEnabled(true);
          batchSpinner.setAlpha(1.0f);
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });

    fromTimePickerButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (batchSpinner.getSelectedItemPosition() != 0) {
          TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
              new OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                  if (hourOfDay <= 11 && minute <= 59) {
                    Snackbar.make(coordinatorLayout,
                        "Submission is from " + hourOfDay + ":" + minute + "am",
                        Snackbar.LENGTH_LONG).show();
                    fromTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + "am";
                  } else {
                    if (hourOfDay == 12 && minute == 0) {
                      Snackbar.make(coordinatorLayout,
                          "Submission is from " + hourOfDay + ":" + minute + "pm",
                          Snackbar.LENGTH_LONG).show();
                      fromTime =
                          Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + "pm";
                    } else {
                      hourOfDay -= 12;
                      Snackbar.make(coordinatorLayout,
                          "Submission is from " + hourOfDay + ":" + minute + "pm",
                          Snackbar.LENGTH_LONG).show();
                      fromTime =
                          Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + "pm";
                    }
                  }
                  fromSelected = true;
                }
              },
              0, 0, false);
          timePickerDialog.show();
        } else {
          Toast.makeText(getContext(), "Please select the batch!", Toast.LENGTH_SHORT).show();
        }
      }
    });

    timerButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (fromSelected) {
          TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
              new OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                  if (hourOfDay <= 11 && minute <= 59) {
                    Snackbar.make(coordinatorLayout,
                        "Submission is till " + hourOfDay + ":" + minute + "am",
                        Snackbar.LENGTH_LONG).show();
                    toTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + "am";
                  } else {
                    if (hourOfDay == 12 && minute == 0) {
                      Snackbar.make(coordinatorLayout,
                          "Submission is till " + hourOfDay + ":" + minute + "pm",
                          Snackbar.LENGTH_LONG).show();
                      toTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + "pm";
                    } else {
                      hourOfDay -= 12;
                      Snackbar.make(coordinatorLayout,
                          "Submission is till " + hourOfDay + ":" + minute + "pm",
                          Snackbar.LENGTH_LONG).show();
                      toTime = Integer.toString(hourOfDay) + ":" + Integer.toString(minute) + "pm";
                    }
                  }
                  toSelected = true;
                }
              },
              0, 0, false);
          timePickerDialog.show();
        } else {
          Toast.makeText(getContext(), "Please select from time.", Toast.LENGTH_SHORT).show();
        }
      }
    });

    studentsButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (fromSelected) {
          final EditText input = new EditText(getContext());
          input.setInputType(InputType.TYPE_CLASS_NUMBER);
          AlertDialog.Builder builder = new Builder(getContext());
          builder.setTitle("Enter the number of students");
          if (input.getParent() != null) {
            ((ViewGroup) input.getParent()).removeView(input);
            input.setText("");
          }
          builder.setView(input);
          builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              Toast.makeText(getContext(), input.getText().toString() + " students selected",
                  Toast.LENGTH_SHORT).show();
              toTime = input.getText().toString() + " students";
              studentsSelected = true;
            }
          });
          builder.setNegativeButton("CANCEL", null);
          builder.show();
        } else {
          Toast.makeText(getContext(), "Please select from time.", Toast.LENGTH_SHORT).show();
        }
      }
    });

    createFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if (args != null) {
          Log.i("Position", Integer.toString(args.getInt("Position")));
//          recentEventsList.remove(extras.getInt("Position"));
          if (toSelected || studentsSelected) {
            AlertDialog.Builder builder = new Builder(getContext());
            builder.setMessage("Do you want to use the last location or set new location?")
                .setPositiveButton("LAST", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    if (!TeacherLocationFragment.locationUpdated) {
                      updateLocation(true);
                      String message = "No last location set! Please update your location.";
                      Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                    } else {
                      recentEventsList
                          .set(args.getInt("Position"),
                              new RecentEvents(subjectSpinner.getSelectedItem().toString(),
                                  batchSpinner.getSelectedItem().toString(), fromTime, toTime,
                                  TeacherLocationFragment.locationString));
                      Toast.makeText(getContext(), "Data updated!", Toast.LENGTH_SHORT).show();
                    }
                    createFab.setImageResource(R.drawable.ic_add);
                  }
                })
                .setNegativeButton("NEW", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialog, int which) {
                    updateLocation(true);
                    createFab.setImageResource(R.drawable.ic_add);
                  }
                });
            builder.show();
          } else {
            Toast.makeText(getContext(), "Please select all fields.", Toast.LENGTH_SHORT).show();
          }
        } else {
          animateFab();
        }
      }
    });

    cancelFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        animateFab();
      }
    });

    startFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        animateFab();
        Intent intent = new Intent(getContext(), StudentListActivity.class);
        startActivity(intent);
      }
    });

    createNewFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        animateFab();

        if (toSelected || studentsSelected) {
          AlertDialog.Builder builder = new Builder(getContext());
          builder.setMessage("Do you want to use the last location or set new location?")
              .setPositiveButton("LAST", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  if (!TeacherLocationFragment.locationUpdated) {
                    updateLocation(false);

                    String message = "No last location set! Please update your location.";
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                  } else {
                    recentEventsList
                        .add(new RecentEvents(subjectSpinner.getSelectedItem().toString(),
                            batchSpinner.getSelectedItem().toString(), fromTime, toTime,
                            TeacherLocationFragment.locationString));
                    Toast.makeText(getContext(), "Created new event!", Toast.LENGTH_SHORT).show();
                  }
                  createFab.setImageResource(R.drawable.ic_add);
                }
              })
              .setNegativeButton("NEW", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                  updateLocation(false);
                  createFab.setImageResource(R.drawable.ic_add);
                }
              });
          builder.show();
        } else {
          Toast.makeText(getContext(), "Please select all fields.", Toast.LENGTH_SHORT).show();
        }
      }
    });

    editFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        bottomSheetFragment = new BottomSheetFragment();
        bottomSheetFragment
            .show(getActivity().getSupportFragmentManager(), bottomSheetFragment.getTag());
      }
    });

    return view;
  }

  private void updateLocation(boolean flag) {
    Bundle extras = new Bundle();
    extras.putBoolean("Flag", flag);
    extras.putString("Subject", subjectSpinner.getSelectedItem().toString());
    extras.putString("Batch", batchSpinner.getSelectedItem().toString());
    extras.putString("From", fromTime);
    extras.putString("To", toTime);
    if(flag) extras.putInt("Position", globalArgs.getInt("Position"));

    FragmentTransaction transaction = getActivity().getSupportFragmentManager()
        .beginTransaction();

    TeacherLocationFragment locationFragment = new TeacherLocationFragment();
    locationFragment.setArguments(extras);

    TeacherScreenActivity.bottomNavigationView
        .setSelectedItemId(R.id.action_location);

    transaction.replace(R.id.containerLayoutTeacher, locationFragment);
    transaction.commit();
  }

  private void animateFab() {
    if (isFabOpen) {
      fabLL1.setVisibility(View.INVISIBLE);
      fabLL2.setVisibility(View.INVISIBLE);
      fabLL3.setVisibility(View.INVISIBLE);
      fabLL4.setVisibility(View.INVISIBLE);
      isFabOpen = false;
      createFab.setAnimation(rotateBackward);
      fabLL1.animate().translationY(0).alpha(0.0f);
      fabLL2.animate().translationY(0).alpha(0.0f);
      fabLL3.animate().translationY(0).alpha(0.0f);
      fabLL4.animate().translationY(0).alpha(0.0f);
      if (VERSION.SDK_INT >= 23) {
        relativeLayout.setForeground(new ColorDrawable(getResources().
            getColor(android.R.color.transparent)));
      }
    } else {
      fabLL1.setVisibility(View.VISIBLE);
      fabLL2.setVisibility(View.VISIBLE);
      fabLL3.setVisibility(View.VISIBLE);
      fabLL4.setVisibility(View.VISIBLE);
      isFabOpen = true;
      createFab.setAnimation(rotateForward);
      fabLL1.animate().translationY(-getResources().getDimension(R.dimen.standard_65)).alpha(1.0f);
      fabLL2.animate().translationY(-getResources().getDimension(R.dimen.standard_115)).alpha(1.0f);
      fabLL3.animate().translationY(-getResources().getDimension(R.dimen.standard_165)).alpha(1.0f);
      fabLL4.animate().translationY(-getResources().getDimension(R.dimen.standard_215)).alpha(1.0f);
      if (VERSION.SDK_INT >= 23) {
        relativeLayout.setForeground(new ColorDrawable(ContextCompat.
            getColor(getContext(), R.color.transparent_white)));
      }
    }
  }

}
