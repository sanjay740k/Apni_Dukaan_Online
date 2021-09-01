package com.example.onlineshopping.ShopInfo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ShopInfo extends AppCompatActivity {

    private TextView shopName, shopkeeperName, mobileNumber, city, address;
    private DatabaseReference ref;
    private String shopId;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle("Shop Info");
        }

        shopName = findViewById(R.id.infoShopName);
        shopkeeperName = findViewById(R.id.infoShopkeeperName);
        mobileNumber = findViewById(R.id.infoShopkeeperMobileNumber);
        city = findViewById(R.id.infoCityName);
        address = findViewById(R.id.infoPostalAddress);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shopId = extras.getString("id");
        }

        ref = FirebaseDatabase.getInstance().getReference();

        GetData();
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Shopkeeper").child(shopId).child("Info");
        ValueEventListener eventListener = new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> info = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()){
                    info.add(Objects.requireNonNull(data.getValue()).toString());
                }
                address.setText(address.getText() + "     " + info.get(0));
                shopkeeperName.setText(shopkeeperName.getText() + "     " + info.get(5));
                shopName.setText(shopName.getText() + "     " + info.get(3));
                city.setText(city.getText() + "     " + info.get(1));
                mobileNumber.setText(mobileNumber.getText() + "     " + info.get(2));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }
}