package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText userEmail ,userPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        Button createAccountButton = findViewById(R.id.register_button);
        userEmail = findViewById(R.id.register_email);
        userPassword = findViewById(R.id.register_password);
        TextView alreadyHaveAccountLink = findViewById(R.id.already_have_account_link);
        progressDialog = new ProgressDialog(this);

        alreadyHaveAccountLink.setOnClickListener(v -> SendUserToLoginActivity());

        createAccountButton.setOnClickListener(v -> CreateNewAccount());
    }

    private void CreateNewAccount() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this , "Please enter your email..." , Toast.LENGTH_LONG).show();
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this , "Please enter valid email address..." , Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this , "Please enter your password..." , Toast.LENGTH_LONG).show();
        }
        else if(password.length() < 6){
            Toast.makeText(this , "Password contain minimum 6 characters..." , Toast.LENGTH_LONG).show();
        }
        else {
            progressDialog.setTitle("Creating new account...");
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email , password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){

                            SendUserToMainActivity();
                            Toast.makeText(RegisterActivity.this ,"Account Created Successfully..." ,Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                        else {
                            String msg = Objects.requireNonNull(task.getException()).toString();
                            Toast.makeText(RegisterActivity.this ,"Error : " + msg ,Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(RegisterActivity.this , MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

    private void SendUserToLoginActivity() {
        Intent loginIntent = new Intent(RegisterActivity.this , LoginActivity.class);
        startActivity(loginIntent);
    }
}