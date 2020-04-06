package com.example.carbuzz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.carbuzz.R;
import com.example.carbuzz.data.UserData;
import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.firebaseRepo.ServerResponse;

import static com.example.carbuzz.utils.Toasts.show;

public class ProfileActivity extends AppCompatActivity {

    private EditText etPhoneNumber, etUserName, etEmail, etFullName;
    private Button btnUpdate;
    private RadioButton rbFemale, rbMale;
    private String gender = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etEmail = findViewById(R.id.et_email);
        etUserName = findViewById(R.id.et_username);
        etFullName = findViewById(R.id.et_full_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        rbFemale = findViewById(R.id.rb_female);
        rbMale = findViewById(R.id.rb_male);
        btnUpdate = findViewById(R.id.btn_update);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    return;
                }
                String email = etEmail.getText().toString().trim();
                String userName = etUserName.getText().toString().trim();
                String fullName = etFullName.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();
                UserData userData = new UserData();
                userData.setEmail(email);
                userData.setUserName(userName);
                userData.setName(fullName);
                userData.setPhoneNumber(phoneNumber);
                userData.setGender(gender);
                FireBaseRepo.I.setProfile(userData, new ServerResponse<String>() {
                    @Override
                    public void onSuccess(String body) {
                        show.longMsg(ProfileActivity.this, body);
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        show.longMsg(ProfileActivity.this, error.getMessage());

                    }
                });
            }
        });
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (etEmail.getText().toString().trim().isEmpty()) {
            isValid = false;
            etEmail.setError("Please fill this field");
        }
        if (etUserName.getText().toString().trim().isEmpty()) {
            isValid = false;
            etUserName.setError("Please fill this field");
        }
        if (etFullName.getText().toString().trim().isEmpty()) {
            isValid = false;
            etFullName.setError("Please fill this field");
        }
        if (etPhoneNumber.getText().toString().trim().isEmpty()) {
            isValid = false;
            etPhoneNumber.setError("Please fill this field");
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
}
