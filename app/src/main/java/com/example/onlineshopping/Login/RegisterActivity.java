package com.example.onlineshopping.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName,userPassword,name, city, adress;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        Bundle extras = getIntent().getExtras();
        if (extras != null) mobile = "+91" + extras.getString("mobile");

        Button createAccountButton = findViewById(R.id.register_button);
        userName = findViewById(R.id.register_username);
        userPassword = findViewById(R.id.register_password);
        name = findViewById(R.id.register_name);
        city = findViewById(R.id.register_cityname);
        adress = findViewById(R.id.register_address);
        progressDialog = new ProgressDialog(this);

        createAccountButton.setOnClickListener(v -> CreateNewAccount());
    }

    private void CreateNewAccount() {
        String email = userName.getText().toString().trim() + "@gmail.com";
        String password = userPassword.getText().toString().trim();
        String curuserName = name.getText().toString().trim();
        String userCity = city.getText().toString().trim();
        String userAddress = adress.getText().toString().trim();

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this , "Please enter valid username..." , Toast.LENGTH_LONG).show();
        }
        if(curuserName.isEmpty()){
            Toast.makeText(this , "Please enter your name..." , Toast.LENGTH_LONG).show();
        }
        else if(userCity.isEmpty()){
            Toast.makeText(this , "Please enter your city name..." , Toast.LENGTH_LONG).show();
        }
        else if(userAddress.isEmpty()){
            Toast.makeText(this , "Please enter your address..." , Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(password)){
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
                            String string = userName.getText().toString().trim().toLowerCase();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.child("Mail").child(string).child("mobile").setValue(mobile);
                            databaseReference.child("Users").child(mobile).child("Mail").setValue(string);
                            databaseReference.child("Users").child(mobile).child("Info").child("Name").setValue(curuserName);
                            databaseReference.child("Users").child(mobile).child("Info").child("City").setValue(userCity);
                            databaseReference.child("Users").child(mobile).child("Info").child("Address").setValue(userAddress);
                            databaseReference.keepSynced(true);

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
}