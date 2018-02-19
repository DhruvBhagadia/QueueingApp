package com.djunicode.queuingapp.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.djunicode.queuingapp.R;
import java.io.IOException;

public class TeacherProfileActivity extends AppCompatActivity {

  private int PICK_IMAGE_REQUEST = 1;

  private Resources resources;
  private Bitmap src;
  private ImageView profileImageView, timeTableImageView;
  private TextView teacherName;
  private LinearLayout submissionLL, subscribersLL, xyzLL;
  private Animation upToDown, leftToRight, rightToLeft, scale;
  private FloatingActionButton uploadFab;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP) {
      getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
      /*getWindow().setExitTransition(new Fade());
      getWindow().setEnterTransition(new Slide());*/
      this.overridePendingTransition(0, 0);
    }

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_teacher_profile);

    profileImageView = (ImageView) findViewById(R.id.profileImageView);
    timeTableImageView = (ImageView) findViewById(R.id.timeTableImageView);
    teacherName = (TextView) findViewById(R.id.teacherNameTextView);
    submissionLL = (LinearLayout) findViewById(R.id.ll1);
    subscribersLL = (LinearLayout) findViewById(R.id.ll2);
    xyzLL = (LinearLayout) findViewById(R.id.ll3);
    uploadFab = (FloatingActionButton) findViewById(R.id.uploadFab);
    resources = getResources();

    src = BitmapFactory.decodeResource(resources, R.drawable.profile_back);
    createCircularImage(src);

    upToDown = AnimationUtils.loadAnimation(this, R.anim.up_to_down);
    leftToRight = AnimationUtils.loadAnimation(this, R.anim.enter_from_left);
    rightToLeft = AnimationUtils.loadAnimation(this, R.anim.enter_from_right);
    scale = AnimationUtils.loadAnimation(this, R.anim.scale);

    profileImageView.setAnimation(leftToRight);
    teacherName.setAnimation(rightToLeft);
    submissionLL.setAnimation(scale);
    subscribersLL.setAnimation(scale);
    xyzLL.setAnimation(scale);

    profileImageView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        PICK_IMAGE_REQUEST = 1;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pick Image"), PICK_IMAGE_REQUEST);
      }
    });

    uploadFab.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        PICK_IMAGE_REQUEST = 0;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Pick Image"), PICK_IMAGE_REQUEST);
      }
    });
  }

  public void onClick(View v) {
    Intent intent = new Intent(this, timeTableActivity.class);
    if(VERSION.SDK_INT >= VERSION_CODES.LOLLIPOP){
      timeTableImageView.setTransitionName("timeTable");
      ActivityOptionsCompat options = ActivityOptionsCompat
          .makeSceneTransitionAnimation(this, timeTableImageView, "timeTable");
      startActivity(intent, options.toBundle());
      this.overridePendingTransition(0,0);
    } else {
      startActivity(intent);
      this.overridePendingTransition(0,0);
    }
  }

  private void createCircularImage(Bitmap bitmap){
    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
    RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(resources, resized);
    rbd.setCircular(true);
    profileImageView.setImageDrawable(rbd);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (PICK_IMAGE_REQUEST == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

      Uri uri = data.getData();

      try {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        // Log.d(TAG, String.valueOf(bitmap));
        createCircularImage(bitmap);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else if (PICK_IMAGE_REQUEST == 0 && resultCode == RESULT_OK && data != null && data.getData() != null) {

      Uri uri = data.getData();

      try {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
         Log.d("Time table", String.valueOf(bitmap));
        timeTableImageView.setImageBitmap(bitmap);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
