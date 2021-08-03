package com.example.onlineshopping;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class LoginWithMobileNumberActivity extends AppCompatActivity {

    private EditText userMobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_mobile_number);

        userMobileNumber = findViewById(R.id.loginWithMobileNumber);

        findViewById(R.id.login_button).setOnClickListener(v -> {

            String mobile = userMobileNumber.getText().toString().trim();

            if(mobile.isEmpty() || mobile.length() < 10){
                userMobileNumber.setError("Enter a valid mobile");
                userMobileNumber.requestFocus();
                return;
            }
            Intent intent = new Intent(LoginWithMobileNumberActivity.this, VerifyNumberActivity.class);
            intent.putExtra("mobile", mobile);
            startActivity(intent);
        });

        findViewById(R.id.loginWithEmail).setOnClickListener(v ->{
            Intent intent = new Intent(LoginWithMobileNumberActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }
}