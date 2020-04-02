package com.example.carbuzz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.carbuzz.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText etEmail,etUsername,etPhonenumber, etPassword,etConfirmPassword;
    private Button loginBtn,signUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();

    }
    // Id of Btn and EditText

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etPhonenumber = findViewById(R.id.et_phonenumber);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_Confirmpassword);


        loginBtn = findViewById(R.id.btn_login);
        signUpBtn = findViewById(R.id.btn_signup);


        //OnClick for Buttons

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
