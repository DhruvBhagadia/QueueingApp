package com.djunicode.queuingapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.model.RecentEvents;
import java.util.List;

/**
 * Created by Ruturaj on 18-01-2018.
 */

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.MyViewHolder> {

  private Context context;
  private String[] subjectString, classString, fromString, toString, location;
  private int size;
  private List<RecentEvents> recentEventsList;
  private RecentEvents recentEvent;

  public RecentsAdapter(Context context, List<RecentEvents> recentEventsList){
    this.context = context;
    this.recentEventsList = recentEventsList;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder{

    TextView subjectName, batchName, startTime, endTime, location;
    public RelativeLayout viewBackground, viewForeground;

    public MyViewHolder(View itemView) {
      super(itemView);
      subjectName = (TextView) itemView.findViewById(R.id.subjectName);
      batchName = (TextView) itemView.findViewById(R.id.batchName);
      startTime = (TextView) itemView.findViewById(R.id.startTime);
      endTime = (TextView) itemView.findViewById(R.id.endTime);
      location = (TextView) itemView.findViewById(R.id.location);
      viewBackground = (RelativeLayout) itemView.findViewById(R.id.backgroundView);
      viewForeground = (RelativeLayout) itemView.findViewById(R.id.foregroundView);
    }
  }

  @Override
  public RecentsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recents_row_layout,
        parent, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RecentsAdapter.MyViewHolder holder, int position) {
    recentEvent = recentEventsList.get(position);
    holder.subjectName.setText(recentEvent.getSubjectName());
    holder.batchName.setText(recentEvent.getBatchName());
    holder.startTime.setText(recentEvent.getStartTime());
    holder.endTime.setText(recentEvent.getEndTime());
    holder.location.setText(recentEvent.getLocation());
  }

  @Override
  public int getItemCount() {
    return recentEventsList.size();
  }

  public void removeItem(int position){
    recentEventsList.remove(position);
    notifyItemRemoved(position);
  }

  public void restoreItem(RecentEvents event, int position){
    recentEventsList.add(position, event);
    notifyItemInserted(position);
  }
}
