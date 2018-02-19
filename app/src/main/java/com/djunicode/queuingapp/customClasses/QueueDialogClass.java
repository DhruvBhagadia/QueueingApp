package com.djunicode.queuingapp.customClasses;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.activity.StudentQueueActivity;

/**
 * Created by Ruturaj on 09-01-2018.
 */

public class QueueDialogClass extends Dialog {

  private Activity activity;
  private Button joinButton;
  private ImageView cancelButton;
  private TextView locationTextView, fromTextView, toTextView, batchTextView;

  public QueueDialogClass(Activity activity) {
    super(activity);
    this.activity = activity;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.queue_dialog_layout);

    locationTextView = (TextView) findViewById(R.id.locationTextView);
    fromTextView = (TextView) findViewById(R.id.fromTextView);
    toTextView = (TextView) findViewById(R.id.toTextView);
    batchTextView = (TextView) findViewById(R.id.batchTextView);
    joinButton = (Button) findViewById(R.id.joinButton);
    cancelButton = (ImageView) findViewById(R.id.cancelButton);

    joinButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(activity, StudentQueueActivity.class);
        getContext().startActivity(intent);
        dismiss();
      }
    });

    cancelButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        dismiss();
      }
    });

    locationTextView.setText("C1");
    fromTextView.setText("10:30am");
    toTextView.setText("12:00pm");
    batchTextView.setText("A2");
  }
}
