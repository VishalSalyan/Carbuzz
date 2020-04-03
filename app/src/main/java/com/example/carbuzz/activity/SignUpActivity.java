package com.example.carbuzz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.carbuzz.R;
import com.example.carbuzz.data.UserData;
import com.example.carbuzz.firebaseRepo.FireBaseRepo;
import com.example.carbuzz.firebaseRepo.ServerResponse;

import static com.example.carbuzz.utils.GoTo.go;
import static com.example.carbuzz.utils.Toasts.show;

public class SignUpActivity extends AppCompatActivity {
    private EditText etEmail, etUserName, etPhoneNumber, etPassword, etConfirmPassword;
    private Button btnLogin, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        initViews();
        clickEvents();
    }

    private void clickEvents() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    return;
                }
                String email = etEmail.getText().toString().trim();
                String userName = etUserName.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                String confirmPassword = etConfirmPassword.getText().toString().trim();

                UserData userData = new UserData();
                userData.setEmail(email);
                userData.setName(userName);
                userData.setPhoneNumber(phoneNumber);
                userData.setPassword(password);

                if (password.equals(confirmPassword)) {
                    FireBaseRepo.I.signUp(userData, new ServerResponse<Boolean>() {
                        @Override
                        public void onSuccess(Boolean body) {
                            show.longMsg(SignUpActivity.this, "Account Created Successfully");
                            go.to(SignUpActivity.this, LoginActivity.class);
                            finish();
                        }

                        @Override
                        public void onFailure(Throwable error) {
                            show.longMsg(SignUpActivity.this, error.getMessage());
                        }
                    });
                }
            }
        });
    }

    // Id of Btn and EditText
    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etUserName = findViewById(R.id.et_username);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_Confirmpassword);

        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_signup);
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (etEmail.getText().toString().trim().isEmpty()) {
                isValid = false;
                etEmail.setError("Please fill this field");
            }
            if (etUserName.getText().toString().trim().isEmpty()) {
                isValid = false;
                etUserName.setError("Please fill this field");
            }
            if (etPhoneNumber.getText().toString().trim().isEmpty()) {
                isValid = false;
                etUserName.setError("Please fill this field");
            }
            if (etPassword.getText().toString().trim().isEmpty()) {
                isValid = false;
                etPassword.setError("Please fill this field");
            }
            if (etConfirmPassword.getText().toString().trim().isEmpty()) {
                isValid = false;
                etConfirmPassword.setError("Please fill this field");
            }
        }
        return isValid;
    }
}
