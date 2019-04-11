package com.djunicode.queuingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.djunicode.queuingapp.R;

import java.util.ArrayList;

public class RemoveStudentAdapter extends RecyclerView.Adapter<RemoveStudentAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<String> studentList;

    public RemoveStudentAdapter(Context context, ArrayList<String> studentList){
        this.context = context;
        this.studentList = studentList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView sapID, serialNo;
        public RelativeLayout viewForeground, viewBackground;

        public MyViewHolder(View itemView) {
            super(itemView);
            sapID = (TextView) itemView.findViewById(R.id.sapID);
            serialNo = (TextView) itemView.findViewById(R.id.serialNo);
            viewBackground = (RelativeLayout) itemView.findViewById(R.id.backgroundView);
            viewForeground = (RelativeLayout) itemView.findViewById(R.id.foregroundView);
        }
    }

    @Override
    public RemoveStudentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row,
                parent, false);
        return new RemoveStudentAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RemoveStudentAdapter.MyViewHolder holder, int position) {
        holder.serialNo.setText(Integer.toString(position + 1) + ".");
        holder.sapID.setText(studentList.get(position));
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public void removeItem(int position) {
        studentList.remove(position);
        notifyItemRemoved(position);
    }
}
