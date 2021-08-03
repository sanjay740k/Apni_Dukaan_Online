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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText userEmail ,userPassword;
    private TextView needNewAccount ,forgotPassword;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Button loginButton = findViewById(R.id.login_button);
        userEmail = findViewById(R.id.login_email);
        userPassword = findViewById(R.id.login_password);
        needNewAccount = findViewById(R.id.need_new_account_link);
        forgotPassword = findViewById(R.id.forget_password_link);
        progressDialog = new ProgressDialog(this);

        needNewAccount.setOnClickListener(v -> SendUserToRgisterActivity());

        loginButton.setOnClickListener(v -> AllowUserToLogin());
    }

    private void AllowUserToLogin() {
        String email = userEmail.getText().toString();
        String password = userPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this , "Please enter your email..." , Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(this , "Please enter your password..." , Toast.LENGTH_LONG).show();
        }
        else{
            progressDialog.setTitle("Sign In...");
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(email ,password)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            String currentUserId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
                            databaseReference.child("User").child(currentUserId).setValue("");

                            SendUserToMainActivity();
                            Toast.makeText(LoginActivity.this ,"Logged in Successful...", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                        else {
                            String msg = Objects.requireNonNull(task.getException()).toString();
                            Toast.makeText(LoginActivity.this ,"Error : " + msg ,Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(LoginActivity.this , MainActivity.class);
        //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        //finish();
    }

    private void SendUserToRgisterActivity() {
        Intent registerIntent = new Intent(LoginActivity.this , RegisterActivity.class);
        startActivity(registerIntent);
    }
}