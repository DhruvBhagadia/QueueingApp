package com.djunicode.queuingapp.fragment;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.adapter.ActiveQueuesAdapter;
import com.djunicode.queuingapp.data.ActiveQueuesDbHelper;
import com.djunicode.queuingapp.data.QueuesDbHelper;
import com.djunicode.queuingapp.model.TeacherCreateNew;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ActiveStudentQueues extends Fragment {

    private RecyclerView activeQueuesRecyclerView;
    private ActiveQueuesAdapter activeQueuesAdapter;
    private List<com.djunicode.queuingapp.model.ActiveStudentQueues> studentQueueList;
    private ApiInterface apiInterface;
    private SQLiteDatabase db;

    public ActiveStudentQueues() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_active_student_queues, container, false);
        activeQueuesRecyclerView = (RecyclerView) view.findViewById(R.id.activeQueueRecyclerView);
        activeQueuesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        final ActiveQueuesDbHelper dbHelper = new ActiveQueuesDbHelper(getContext());
        db = dbHelper.getReadableDatabase();
        studentQueueList = dbHelper.getAllQueues();
        for(int i=0; i<studentQueueList.size(); i++)
            Log.e("Active Student Queues", String.valueOf(studentQueueList.get(i).getId()));
        activeQueuesAdapter = new ActiveQueuesAdapter(getContext(), studentQueueList);
        activeQueuesRecyclerView.setItemAnimator(new DefaultItemAnimator());
        activeQueuesRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        activeQueuesRecyclerView.setAdapter(activeQueuesAdapter);

        return view;
    }

}
