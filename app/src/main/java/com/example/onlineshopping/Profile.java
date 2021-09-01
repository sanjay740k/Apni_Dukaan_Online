package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.Cart.MyCartShop;
import com.example.onlineshopping.Cart.MyCartShopAdapter;
import com.example.onlineshopping.Login.LoginActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    private TextView name, city, address;
    private EditText enterName, enterCity, enterAddress;
    private Button edit, save;
    private String curUser;
    DatabaseReference ref;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle("Profile");
        }

        ref = FirebaseDatabase.getInstance().getReference();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            curUser = extras.getString("id");
        }

        name = findViewById(R.id.curUserName);
        enterName = findViewById(R.id.enterUserName);
        address = findViewById(R.id.yourAddress);
        enterAddress = findViewById(R.id.enterYourAddress);
        city = findViewById(R.id.yourCity);
        enterCity = findViewById(R.id.enterYourCity);

        edit = findViewById(R.id.editProfile);
        edit.setOnClickListener(v -> EditProfile());
        save = findViewById(R.id.saveProfile);
        save.setOnClickListener(v -> SaveProfile());

        GetData();
    }

    private void EditProfile(){
        name.setVisibility(View.INVISIBLE);
        address.setVisibility(View.INVISIBLE);
        city.setVisibility(View.INVISIBLE);
        enterCity.setVisibility(View.VISIBLE);
        enterName.setVisibility(View.VISIBLE);
        enterAddress.setVisibility(View.VISIBLE);
        edit.setVisibility(View.INVISIBLE);
        save.setVisibility(View.VISIBLE);
    }

    private void SaveProfile(){
        String namestr = enterName.getText().toString().trim();
        String addressstr = enterAddress.getText().toString().trim();
        String citystr = enterCity.getText().toString().trim();

        if(namestr.isEmpty()){
            Toast.makeText(this, "Please write your name..." , Toast.LENGTH_LONG).show();
        }
        else if(citystr.isEmpty()){
            Toast.makeText(this, "Please write your city name..." , Toast.LENGTH_LONG).show();
        }
        else if(addressstr.isEmpty()){
            Toast.makeText(this, "Please write your address..." , Toast.LENGTH_LONG).show();
        }
        else {
            name.setVisibility(View.VISIBLE);
            address.setVisibility(View.VISIBLE);
            city.setVisibility(View.VISIBLE);
            enterCity.setVisibility(View.INVISIBLE);
            enterName.setVisibility(View.INVISIBLE);
            enterAddress.setVisibility(View.INVISIBLE);
            edit.setVisibility(View.VISIBLE);
            save.setVisibility(View.INVISIBLE);

            name.setText(namestr);
            address.setText(addressstr);
            city.setText(citystr);

            ref.child("Users").child(curUser).child("Info").child("Name").setValue(namestr);
            ref.child("Users").child(curUser).child("Info").child("City").setValue(citystr);
            ref.child("Users").child(curUser).child("Info").child("Address").setValue(addressstr);

            Toast.makeText(this,"Data successfully uploaded", Toast.LENGTH_LONG).show();
            SendUserToMainActivity();
        }
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Users").child(curUser).child("Info");
        ValueEventListener eventListener = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> str = new ArrayList<>();
                for (DataSnapshot data: snapshot.getChildren()) {
                    str.add(data.getValue().toString());
                }
                if(str.size() > 2){
                    name.setText("Name - " + str.get(2));
                    city.setText("City Name - " + str.get(1));
                    address.setText("address - " + str.get(0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }

    private void SendUserToMainActivity() {
        Intent mainIntent = new Intent(Profile.this , MainActivity.class);
        //mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        //finish();
    }
}