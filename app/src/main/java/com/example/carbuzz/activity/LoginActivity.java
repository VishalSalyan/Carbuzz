package com.example.carbuzz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.carbuzz.R;


public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button loginBtn,signUpBtn,googleLoginbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

    }
    // Id of Btn and EditText

    private void initViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        loginBtn = findViewById(R.id.btn_login);
        signUpBtn = findViewById(R.id.btn_signup);
        googleLoginbtn = findViewById(R.id.btn_googleLogin);


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
