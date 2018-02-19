package com.djunicode.queuingapp.fragment;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ListView;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.adapter.BottomSheetAdapter;

public class BottomSheetFragment extends BottomSheetDialogFragment {

  public BottomSheetFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false);

    RecyclerView bottomRecyclerView = (RecyclerView) view
        .findViewById(R.id.bottomSheetRecyclerView);

    BottomSheetAdapter adapter = new BottomSheetAdapter(getContext(),
        TeacherSubmissionFragment.recentEventsList);

    bottomRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    bottomRecyclerView.setItemAnimator(new DefaultItemAnimator());
    bottomRecyclerView
        .addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
    bottomRecyclerView.setAdapter(adapter);
    return view;
  }

}
