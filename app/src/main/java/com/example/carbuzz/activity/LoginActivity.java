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


public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin, btnSignUp, btnGoogleLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        login();
    }

    private void login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidate()) {
                    return;
                }

                String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                UserData userData = new UserData();
                userData.setName(email);
                userData.setPassword(password);
                FireBaseRepo.I.login(userData, new ServerResponse<Boolean>() {
                    @Override
                    public void onSuccess(Boolean body) {
                        if (body) {
                            go.to(LoginActivity.this, HomeActivity.class);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Throwable error) {
                        show.longMsg(LoginActivity.this, error.getMessage());
                    }
                });
            }
        });
    }

    private boolean isValidate() {
        boolean isValid = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            if (etEmail.getText().toString().trim().isEmpty()) {
                isValid = false;
                etEmail.setError("Please fill this field");
            }
            if (etPassword.getText().toString().trim().isEmpty()) {
                isValid = false;
                etPassword.setError("Please fill this field");
            }
        }

        return isValid;
    }

    // Ids of Btn and EditText
    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        btnLogin = findViewById(R.id.btn_update);
        btnSignUp = findViewById(R.id.btn_signup);
        btnGoogleLogin = findViewById(R.id.btn_googleLogin);


        //OnClick for Buttons

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
