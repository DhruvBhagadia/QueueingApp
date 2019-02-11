package com.djunicode.queuingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.activity.StudentQueueActivity;
import com.djunicode.queuingapp.model.LocationTeacher;
import com.djunicode.queuingapp.model.RecentEvents;
import com.djunicode.queuingapp.model.StudentQueue;
import com.djunicode.queuingapp.model.TeacherCreateNew;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvailableQueuesAdapter extends
        RecyclerView.Adapter<AvailableQueuesAdapter.MyViewHolder> {

    private Context context;
    private List<TeacherCreateNew> recentEventsList;
    private ApiInterface apiInterface;
    private SharedPreferences preferences;

    public AvailableQueuesAdapter(Context context, List<TeacherCreateNew> recentEventsList) {
        this.context = context;
        this.recentEventsList = recentEventsList;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        preferences = context.getSharedPreferences("Student", Context.MODE_PRIVATE);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView locationTextView, fromTextView, toTextView, subjectTextView, startedTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            locationTextView = (TextView) itemView.findViewById(R.id.locationTextView);
            fromTextView = (TextView) itemView.findViewById(R.id.fromTextView);
            toTextView = (TextView) itemView.findViewById(R.id.toTextView);
            subjectTextView = (TextView) itemView.findViewById(R.id.subjectTextView);
            startedTextView = (TextView) itemView.findViewById(R.id.startedTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            TeacherCreateNew event = recentEventsList.get(getAdapterPosition());
            Log.e("FlagClick", event.getFlag().toString());
            if (event.getFlag() != 0) {
                String sapId = preferences.getString("student_sapid", "");
                final Intent intent = new Intent(context, StudentQueueActivity.class);
                intent.putExtra("id", event.getId());
                intent.putExtra("sapId", sapId);
                intent.putExtra("first", true);
                Log.e("server id", Integer.toString(event.getId()));
                Call<StudentQueue> call = apiInterface
                        .studentJoiningTheQueue(event.getId(), sapId);
                call.enqueue(new Callback<StudentQueue>() {
                    @Override
                    public void onResponse(Call<StudentQueue> call, Response<StudentQueue> response) {
                        if (response.isSuccessful()) {
                            Log.i("studentJoining Res", response.body().toString());
                            context.startActivity(intent);
                        } else {
                            Log.i("studentJoining Res", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<StudentQueue> call, Throwable t) {
                        Log.i("studentJoining ResF", t.getMessage().toString());
                    }
                });
            } else {
                Toast.makeText(context, "Submission not yet started!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public AvailableQueuesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.available_queues_list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AvailableQueuesAdapter.MyViewHolder holder, int position) {
        TeacherCreateNew event = recentEventsList.get(position);
        if (event.getLocation() != null) {
            Call<LocationTeacher> call = apiInterface.getQueueLocation(event.getLocation());
            call.enqueue(new Callback<LocationTeacher>() {
                @Override
                public void onResponse(Call<LocationTeacher> call, Response<LocationTeacher> response) {
                    try {
                        String dept = response.body().getDepartment();
                        Integer floor = response.body().getFloor();
                        String room = response.body().getRoom();
                        holder.locationTextView.setText("Dept: " + dept + "  Floor: " + floor + "  Room: " + room);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<LocationTeacher> call, Throwable t) {

                }
            });
        } else {
            holder.locationTextView.setText("Not yet set.");
        }
        holder.fromTextView.setText(event.getFrom());
        holder.toTextView.setText(event.getTo());
        holder.subjectTextView.setText(event.getSubject());
        Log.e("Flag", event.getFlag().toString());
        if (event.getFlag() == 0) {
            holder.startedTextView.setText("No");
        } else {
            holder.startedTextView.setTextColor(Color.GREEN);
            holder.startedTextView.setText("Yes");
        }
    }

    @Override
    public int getItemCount() {
        return recentEventsList.size();
    }
}
