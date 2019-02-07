package com.djunicode.queuingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.activity.StudentQueueActivity;
import com.djunicode.queuingapp.model.ActiveStudentQueues;
import com.djunicode.queuingapp.model.LocationTeacher;
import com.djunicode.queuingapp.model.TeacherCreateNew;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActiveQueuesAdapter extends RecyclerView.Adapter<ActiveQueuesAdapter.MyViewHolder> {

    private Context context;
    private List<ActiveStudentQueues> studentQueuesList;
    private TeacherCreateNew studentQueue;
    private ApiInterface apiInterface;
    private SharedPreferences preferences;

    public ActiveQueuesAdapter(Context context, List<ActiveStudentQueues> studentQueuesList){
        this.context = context;
        this.studentQueuesList = studentQueuesList;
        this.apiInterface = ApiClient.getClient().create(ApiInterface.class);
        preferences = context.getSharedPreferences("Student", Context.MODE_PRIVATE);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView subjectName, startTime, endTime, location;

        public MyViewHolder(View itemView) {
            super(itemView);
            subjectName = (TextView) itemView.findViewById(R.id.subjectName);
            startTime = (TextView) itemView.findViewById(R.id.startTime);
            endTime = (TextView) itemView.findViewById(R.id.endTime);
            location = (TextView) itemView.findViewById(R.id.location);

            itemView.setOnClickListener(this);
        }

            @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ActiveStudentQueues sQueue = studentQueuesList.get(position);
            final Integer queueId = sQueue.getId();
            String sapId = preferences.getString("student_sapid", "");
            final Intent intent = new Intent(context, StudentQueueActivity.class);
            intent.putExtra("id", queueId);
            intent.putExtra("sapId", sapId);
            intent.putExtra("first", false);
            Log.e("id and sapId", String.valueOf(queueId) + " " + sapId);
//            intent.putExtra("subject", sQueue.getSubject());
//            intent.putExtra("startTime", sQueue.getStartTime());
//            intent.putExtra("endTime", sQueue.getEndTime());
//            intent.putExtra("location", sQueue.getLocation());
            context.startActivity(intent);
        }
    }

        @Override
        public ActiveQueuesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.active_queues_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ActiveQueuesAdapter.MyViewHolder holder, int position) {
        ActiveStudentQueues sQueue = studentQueuesList.get(position);
        if(sQueue.getLocation() != 0){
            Call<LocationTeacher> call = apiInterface.getQueueLocation(sQueue.getLocation());
            call.enqueue(new Callback<LocationTeacher>() {
                @Override
                public void onResponse(Call<LocationTeacher> call, Response<LocationTeacher> response) {
                    try{
                        String dept = response.body().getDepartment();
                        Integer floor = response.body().getFloor();
                        String room = response.body().getRoom();
                        holder.location.setText("Dept: " + dept + "  Floor: " + floor + "  Room: " + room);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<LocationTeacher> call, Throwable t) {

                }
            });
        } else {
            holder.location.setText("Not yet set.");
        }
        holder.startTime.setText(sQueue.getStartTime());
        holder.endTime.setText(sQueue.getEndTime());
        holder.subjectName.setText(sQueue.getSubject());
    }

    @Override
    public int getItemCount() {
        return studentQueuesList.size();
    }
}
