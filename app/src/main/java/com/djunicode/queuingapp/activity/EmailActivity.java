package com.djunicode.queuingapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.djunicode.queuingapp.R;
import com.djunicode.queuingapp.model.UserEmailVerify;
import com.djunicode.queuingapp.rest.ApiClient;
import com.djunicode.queuingapp.rest.ApiInterface;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmailActivity extends AppCompatActivity {

    private TextInputLayout signUpEmailTIL, verifyTIL;
    private EditText signUpEmailEditText, verifyEditText;
    private Button sendCodeButton, verifyEmailButton;
    private ApiInterface apiInterface;
    public static Integer id;
    private SharedPreferences preferences;
    private ProgressDialog progressDialog;

    public String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Intent intent = getIntent();
        final String user = intent.getStringExtra("user");

        signUpEmailTIL = (TextInputLayout) findViewById(R.id.signUpEmailTIL);
        verifyTIL = (TextInputLayout) findViewById(R.id.verifyTIL);
        signUpEmailEditText = (EditText) findViewById(R.id.signUpEmailEditText);
        verifyEditText = (EditText) findViewById(R.id.verifyEditText);
        sendCodeButton = (Button) findViewById(R.id.sendCodeButton);
        verifyEmailButton = (Button) findViewById(R.id.verifyEmailButton);
        preferences = this.getSharedPreferences("com.djunicode.queuingapp", MODE_PRIVATE);

        verifyEditText.setEnabled(false);
        verifyEditText.setAlpha(0.4f);

        verifyEmailButton.setEnabled(false);

        sendCodeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog
                        .show(EmailActivity.this, "Sending code.", "Please wait...");
                String email = signUpEmailEditText.getText().toString();
//        email.split("@")[0]

                Call<UserEmailVerify> call = apiInterface.sendEmail(getSaltString(), email, "demopass");
                if (user.equals("teacher")) {
                    if (email.contains("@djsce.ac.in")) {
            /*call.enqueue(new Callback<UserEmailVerify>() {
              @Override
              public void onResponse(@NonNull Call<UserEmailVerify> call,
                  @NonNull Response<UserEmailVerify> response) {
                try{
                  id = response.body().getId();
                  preferences.edit().putInt("teacherID", id).apply();
                  Log.i("ID:", Integer.toString(id));
                } catch (Exception e){
                  Toast.makeText(EmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
              }

              @Override
              public void onFailure(Call<UserEmailVerify> call, Throwable t) {
                Log.e("Error:", t.getMessage());
              }
            });*/
                    } else {
                        Log.e("Error: ", "Invalid teacher email");
                    }
                    call.enqueue(new Callback<UserEmailVerify>() {
                        @Override
                        public void onResponse(@NonNull Call<UserEmailVerify> call,
                                               @NonNull Response<UserEmailVerify> response) {
                            try {
                                id = response.body().getId();
                                preferences.edit().putInt("teacherUserID", id).apply();
                                Log.i("ID:", Integer.toString(id));
                                verifyEmailButton.setEnabled(true);
                                progressDialog.dismiss();
                                Toast.makeText(EmailActivity.this, "Code sent, please check your email.",
                                        Toast.LENGTH_LONG).show();
                                verifyEditText.setEnabled(true);
                                verifyEditText.setAlpha(1.0f);
                            } catch (Exception e) {
                                Toast.makeText(EmailActivity.this, "Invalid e-mail", Toast.LENGTH_LONG).show();
                                Log.e("TeEx", e.getMessage());
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserEmailVerify> call, Throwable t) {
                            Log.e("Error:", t.getMessage());
                        }
                    });
                } else {
                    call.enqueue(new Callback<UserEmailVerify>() {
                        @Override
                        public void onResponse(Call<UserEmailVerify> call, Response<UserEmailVerify> response) {
                            try {
                                id = response.body().getId();
                                preferences.edit().putInt("studentID", id).apply();
                                Log.e("ID:", id.toString());
                                verifyEmailButton.setEnabled(true);
                                progressDialog.dismiss();
                                Toast.makeText(EmailActivity.this, "Code sent, please check your email.",
                                        Toast.LENGTH_LONG).show();
                                verifyEditText.setEnabled(true);
                                verifyEditText.setAlpha(1.0f);
                            } catch (Exception e) {
                                Toast.makeText(EmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UserEmailVerify> call, Throwable t) {
                            Log.e("Error:", t.getMessage());
                        }
                    });
                }
                sendCodeButton.setEnabled(false);
            }
        });

        verifyEmailButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog
                        .show(EmailActivity.this, "Verifying.", "Please wait...");
                final Intent intent = new Intent(EmailActivity.this, LogInActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Call<UserEmailVerify> call = apiInterface
                        .verifyEmail(id, verifyEditText.getText().toString());
                call.enqueue(new Callback<UserEmailVerify>() {
                    @Override
                    public void onResponse(Call<UserEmailVerify> call, Response<UserEmailVerify> response) {
                        try {
                            Boolean valid = response.body().getValid();
                            Log.e("Valid:", valid.toString());
                            progressDialog.dismiss();
                            if (valid) {
                                if (user.equals("teacher")) {
                                    intent.putExtra("user", "teacher");
                                } else {
                                    intent.putExtra("user", "student");
                                }
                                startActivity(intent);
                            } else {
                                Toast.makeText(EmailActivity.this, "Invalid Token!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(EmailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserEmailVerify> call, Throwable t) {
                        Log.e("Error:", t.getMessage());
                    }
                });

        /*if (user.equals("teacher")) {
          intent.putExtra("user", user);
        } else {
          intent.putExtra("user", "student");
        }
        startActivity(intent);*/

            }
        });
    }
}
