package com.example.onlineshopping.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterAsShopkeeperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_as_shopkeeper);

        EditText mobileNumber = findViewById(R.id.mobileNumber);
        Button getOTP = findViewById(R.id.getotp_button);

        String curUser = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            curUser = extras.getString("id");
        }

        mobileNumber.setText(curUser);

        String finalCurUser = curUser;
        getOTP.setOnClickListener(v -> {

            String mobile = mobileNumber.getText().toString().trim();

            if(mobile.isEmpty() || mobile.length() < 10){
                mobileNumber.setError("Enter a valid mobile");
                mobileNumber.requestFocus();
            }
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Shopkeeper").child(finalCurUser);
            ref.setValue("");
            ref.keepSynced(true);
            Intent intent = new Intent(RegisterAsShopkeeperActivity.this, MainActivity.class);
            startActivity(intent);
            //finish();
        });
    }
}