package com.djunicode.queuingapp.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.support.v7.widget.helper.ItemTouchHelper.SimpleCallback;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.adapter.RecentsAdapter;
import com.djunicode.queuingapp.customClasses.RecyclerItemTouchHelper;
import com.djunicode.queuingapp.customClasses.RecyclerItemTouchHelper.RecyclerItemTouchHelperListener;
import com.djunicode.queuingapp.model.RecentEvents;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentsFragment extends Fragment implements
    RecyclerItemTouchHelper.RecyclerItemTouchHelperListener{

  private RecyclerView recentsRecyclerView;
  private RecentsAdapter adapter;
  private RelativeLayout relativeLayout;

  public RecentsFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_recents, container, false);

    List<RecentEvents> recentEventsList = TeacherSubmissionFragment.recentEventsList;

    recentsRecyclerView = (RecyclerView) view.findViewById(R.id.recentsRecyclerView);
    recentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    adapter = new RecentsAdapter(getContext(), recentEventsList);
    relativeLayout = (RelativeLayout) view.findViewById(R.id.recentsRelativeLayout);

    recentsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    recentsRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
        DividerItemDecoration.VERTICAL));
    recentsRecyclerView.setAdapter(adapter);

    ItemTouchHelper.SimpleCallback simpleCallback = new RecyclerItemTouchHelper(0,
        ItemTouchHelper.LEFT, this);
    new ItemTouchHelper(simpleCallback).attachToRecyclerView(recentsRecyclerView);

    return view;
  }

  @Override
  public void onSwiped(ViewHolder viewHolder, int direction, int position) {
    if(viewHolder instanceof RecentsAdapter.MyViewHolder){
      final RecentEvents event = TeacherSubmissionFragment.recentEventsList
          .get(viewHolder.getAdapterPosition());

      final int deletedIndex = viewHolder.getAdapterPosition();
      adapter.removeItem(viewHolder.getAdapterPosition());

      Snackbar snackbar = Snackbar
          .make(relativeLayout, "Removed from recents!", Snackbar.LENGTH_LONG);
      snackbar.setAction("UNDO", new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          adapter.restoreItem(event, deletedIndex);
        }
      });
      snackbar.setActionTextColor(Color.YELLOW);
      snackbar.show();
    }
  }
}


