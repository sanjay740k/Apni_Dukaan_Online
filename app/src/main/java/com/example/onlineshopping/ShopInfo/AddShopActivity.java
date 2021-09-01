package com.example.onlineshopping.ShopInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.example.onlineshopping.DataType.Shop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddShopActivity extends AppCompatActivity {

    private EditText shopName, shopkeeperName, mobileNumber, city, address;
    private DatabaseReference ref;
    private String saveShopName, saveShopkeeperName, saveMobileNumber, saveCity, saveAddress, curUser = "123";

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
        }

        ref = FirebaseDatabase.getInstance().getReference();
        Bundle extras = getIntent().getExtras();
        if (extras != null) curUser = extras.getString("id");

        shopName = findViewById(R.id.addShopShopName);
        shopkeeperName = findViewById(R.id.addShopShopkeeperName);
        mobileNumber = findViewById(R.id.addShopShopkeeperMobileNumber);
        city = findViewById(R.id.addShopCityName);
        address = findViewById(R.id.addShopPostalAddress);
        TextView saveInfo = findViewById(R.id.save);
        saveInfo.setOnClickListener(v -> {
            if(SavesInfo()){
                CityExist(saveCity);
            }
        });
    }

    private Boolean SavesInfo() {
        saveShopName = shopName.getText().toString().trim();
        saveShopkeeperName = shopkeeperName.getText().toString().trim();
        saveMobileNumber = mobileNumber.getText().toString().trim();
        saveCity = city.getText().toString().trim();
        saveAddress = address.getText().toString().trim();

        if(TextUtils.isEmpty(saveShopName)){
            Toast.makeText(AddShopActivity.this ,"Please write your Shop name", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(saveShopkeeperName)){
            Toast.makeText(AddShopActivity.this ,"Please write your Shopkeeper name", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(saveMobileNumber)){
            Toast.makeText(AddShopActivity.this ,"Please write your Mobile number", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(saveCity)){
            Toast.makeText(AddShopActivity.this ,"Please write your City name", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(saveAddress)){
            Toast.makeText(AddShopActivity.this ,"Please write your address", Toast.LENGTH_LONG).show();
        }
        else{
            return true;
        }
        return false;
    }

    private void CityExist(String s) {
        DatabaseReference cityRef = ref.child("Cities").child(s);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    cityRef.child(curUser).setValue("");
                    //cityRef.setValue(saveShopName);
                    cityRef.keepSynced(true);
                }
                else {
                    ref.child("Cities").child(s).child(curUser).setValue("");
                    ref.keepSynced(true);
                }
                Shop shop = new Shop(curUser, saveShopName, saveShopkeeperName, saveMobileNumber, saveCity, saveAddress);
                ref.child("Shopkeeper").child(curUser).child("Info").setValue(shop);
                ref.child("Cities").child(s).child(curUser).child("Info").setValue(shop);
                SendUserToMainActivity();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        cityRef.addListenerForSingleValueEvent(eventListener);
    }

    private void SendUserToMainActivity() {
        Toast.makeText(this,"Shop successfully added", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}