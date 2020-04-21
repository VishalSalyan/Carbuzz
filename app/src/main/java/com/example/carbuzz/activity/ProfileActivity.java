package com.example.carbuzz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.example.carbuzz.R;
import com.example.carbuzz.data.UserData;
import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.firebaseRepo.ServerResponse;
import com.example.carbuzz.utils.SessionData;
import com.squareup.picasso.Picasso;

import static com.example.carbuzz.utils.Toasts.show;

public class ProfileActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private EditText etPhoneNumber, etEmail, etFullName;
    private Button btnUpdate;
    private ImageView profileImage;
    private RadioButton rbFemale, rbMale;
    private String gender = null;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etEmail = findViewById(R.id.et_email);
        etFullName = findViewById(R.id.et_full_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        profileImage = findViewById(R.id.image_view);
        rbFemale = findViewById(R.id.rb_female);
        rbMale = findViewById(R.id.rb_male);
        btnUpdate = findViewById(R.id.btn_update);
        setData();

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    return;
                }
                final String email = etEmail.getText().toString().trim();
                final String fullName = etFullName.getText().toString().trim();
                final String phoneNumber = etPhoneNumber.getText().toString().trim();

                FireBaseRepo.I.uploadFile(SessionData.getInstance().getLocalData().getName() + ".jpg", imageUri, new ServerResponse<String>() {
                    @Override
                    public void onSuccess(String body) {
                        UserData userData = new UserData();
                        userData.setEmail(email);
                        userData.setName(fullName);
                        userData.setPhoneNumber(phoneNumber);
                        userData.setGender(gender);
                        userData.setUserImage(body);
                        userData.setPassword(SessionData.getInstance().getLocalData().getPassword());
                        userData.setFavouriteCars(SessionData.getInstance().getLocalData().getFavouriteCars());
                        SessionData.getInstance().saveLocalData(userData);
                        FireBaseRepo.I.setProfile(userData, new ServerResponse<String>() {
                            @Override
                            public void onSuccess(String body) {
                                show.longMsg(ProfileActivity.this, body);
                            }

                            @Override
                            public void onFailure(Throwable error) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Throwable error) {

                    }
                });

            }
        });
    }

    private void setData() {
        etEmail.setText(SessionData.getInstance().getLocalData().getEmail());
        etFullName.setText(SessionData.getInstance().getLocalData().getName());
        etPhoneNumber.setText(SessionData.getInstance().getLocalData().getPhoneNumber());
        if (SessionData.getInstance().getLocalData().getUserImage() != null)
            Picasso.get().load(SessionData.getInstance().getLocalData().getUserImage()).into(profileImage);
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (etEmail.getText().toString().trim().isEmpty()) {
            isValid = false;
            etEmail.setError("Please fill this field");
        }
        if (etFullName.getText().toString().trim().isEmpty()) {
            isValid = false;
            etFullName.setError("Please fill this field");
        }
        if (etPhoneNumber.getText().toString().trim().isEmpty()) {
            isValid = false;
            etPhoneNumber.setError("Please fill this field");
        }
        if (imageUri == null) {
            isValid = false;
            show.longMsg(ProfileActivity.this, "Please Select image");
        }
        if (rbMale.isChecked()) {
            gender = "Male";
        } else if (rbFemale.isChecked()) {
            gender = "Female";
        } else {
            show.longMsg(ProfileActivity.this, "Please Select gender");
            isValid = false;
        }
        return isValid;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode) {

                case REQUEST_CODE:
                    if (resultCode == Activity.RESULT_OK) {
                        profileImage.setImageURI(data.getData());
                        imageUri = data.getData();
                        break;
                    } else if (resultCode == Activity.RESULT_CANCELED) {
                        Log.e("TAG", "Selecting picture cancelled");
                    }
                    break;
            }
        } catch (Exception e) {
            Log.e("TAG", "Exception in onActivityResult : " + e.getMessage());
        }
    }
}
