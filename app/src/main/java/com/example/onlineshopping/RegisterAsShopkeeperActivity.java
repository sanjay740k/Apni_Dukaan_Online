package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterAsShopkeeperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_shopkeeper);

        EditText mobileNumber = findViewById(R.id.mobileNumber);
        Button getOTP = findViewById(R.id.getotp_button);

        getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile = mobileNumber.getText().toString().trim();

                if(mobile.isEmpty() || mobile.length() < 10){
                    mobileNumber.setError("Enter a valid mobile");
                    mobileNumber.requestFocus();
                    return;
                }
                //Intent intent = new Intent(RegisterAsShopkeeperActivity.this, VerifyNumberActivity.class);
                //intent.putExtra("mobile", mobile);
                //startActivity(intent);
            }
        });
    }
}