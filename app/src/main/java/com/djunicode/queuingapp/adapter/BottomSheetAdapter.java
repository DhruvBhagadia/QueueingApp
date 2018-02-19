package com.djunicode.queuingapp.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.fragment.TeacherSubmissionFragment;
import com.djunicode.queuingapp.model.RecentEvents;
import java.util.List;

/**
 * Created by Ruturaj on 24-01-2018.
 */

public class BottomSheetAdapter extends RecyclerView.Adapter<BottomSheetAdapter.MyViewHolder> {

  private Context context;
  private List<RecentEvents> recentEventsList;
  private RecentEvents recentEvent;

  public BottomSheetAdapter(Context context, List<RecentEvents> recentEventsList) {
    this.context = context;
    this.recentEventsList = recentEventsList;
  }

  public class MyViewHolder extends RecyclerView.ViewHolder implements OnClickListener{

    TextView timeTV, batchTV;
    LinearLayout bottomLinearlayout;

    public MyViewHolder(View itemView) {
      super(itemView);
      batchTV = (TextView) itemView.findViewById(R.id.batchNameBottom);
      timeTV = (TextView) itemView.findViewById(R.id.timeBottom);
      bottomLinearlayout = (LinearLayout) itemView.findViewById(R.id.bottomLinearLayout);

      bottomLinearlayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
      Bundle bundle = new Bundle();
      bundle.putInt("Position", getAdapterPosition());

      TeacherSubmissionFragment fragment = new TeacherSubmissionFragment();
      fragment.setArguments(bundle);

      FragmentTransaction transaction = ((FragmentActivity)context).getSupportFragmentManager()
          .beginTransaction();

      transaction.replace(R.id.containerLayoutTeacher, fragment);
      transaction.commit();

      TeacherSubmissionFragment.bottomSheetFragment.dismiss();
    }
  }

  @Override
  public BottomSheetAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottom_row_layout,
        parent, false);
    return new MyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(BottomSheetAdapter.MyViewHolder holder, int position) {
    recentEvent = recentEventsList.get(position);
    holder.timeTV.setText(recentEvent.getStartTime());
    holder.batchTV.setText(recentEvent.getBatchName());
  }

  @Override
  public int getItemCount() {
    return recentEventsList.size();
  }
}
