package com.djunicode.queuingapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.djunicode.queuingapp.R;

public class StudentQueueActivity extends AppCompatActivity {

  private TextView queuePositionTextView, positionTV;
  private CardView doneButton, queueNumber;
  private Animation upToDown, downToUp, scale;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_student_queue);

    queuePositionTextView = (TextView) findViewById(R.id.queuePositionTextView);
    positionTV = (TextView) findViewById(R.id.positionTV);
    doneButton = (CardView) findViewById(R.id.doneButton);
    queueNumber = (CardView) findViewById(R.id.queueNumberCardView);

    upToDown = AnimationUtils.loadAnimation(this, R.anim.up_to_down);
    downToUp = AnimationUtils.loadAnimation(this, R.anim.down_to_up);
    scale = AnimationUtils.loadAnimation(this, R.anim.scale);

    positionTV.setAnimation(upToDown);
    doneButton.setAnimation(downToUp);
    queueNumber.setAnimation(scale);

    queuePositionTextView.setText("18");
  }
}
