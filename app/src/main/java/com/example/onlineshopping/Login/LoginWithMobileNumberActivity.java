package com.example.onlineshopping.Login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
            CheckData(mobile);
        });

        findViewById(R.id.loginWithEmail).setOnClickListener(v ->{
            Intent loginIntent = new Intent(LoginWithMobileNumberActivity.this , LoginActivity.class);
            startActivity(loginIntent);
        });
    }

    private void CheckData(String mobile) {
        String mobileNumber = "+91" + mobile;
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        DatabaseReference hideMenu = ref.child("Users");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(mobileNumber)) {
                    SendUserToLoginActivity(snapshot.child(mobileNumber).child("Mail").getValue().toString());
                }
                else{
                    SendUserToVerifyActivity(mobile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        hideMenu.addListenerForSingleValueEvent(eventListener);
    }

    private void SendUserToVerifyActivity(String mobile) {
        Intent intent = new Intent(LoginWithMobileNumberActivity.this, VerifyNumberActivity.class);
        intent.putExtra("mobile", mobile);
        startActivity(intent);
    }

    private void SendUserToLoginActivity(String mail) {
        Toast.makeText(this,"You have already registered please login...",Toast.LENGTH_LONG).show();
        Intent loginIntent = new Intent(LoginWithMobileNumberActivity.this , LoginActivity.class);
        loginIntent.putExtra("mail", mail);
        //loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        //finish();
    }
}