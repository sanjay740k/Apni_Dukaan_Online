package com.example.onlineshopping.YourShopOrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.onlineshopping.Cart.MyCartShop;
import com.example.onlineshopping.Cart.MyCartShopAdapter;
import com.example.onlineshopping.DataType.User;
import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Customer extends AppCompatActivity {

    private String curUser, curShop, curItemType;
    private RecyclerView recyclerView;
    private DatabaseReference ref;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle("Customers");
        }

        ref = FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        curUser = sharedPreferences.getString("KEY", "");

        recyclerView = findViewById(R.id.myCartShopRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        GetData();
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Shopkeeper").child(curUser).child("Orders");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    GetCustomerName(data.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }

    private void GetCustomerName(String mobile) {
        DatabaseReference getData = ref.child("Users").child(mobile).child("Info");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<User> str = new ArrayList<>();
                String name = snapshot.child("Name").getValue(String.class);
                String city = snapshot.child("City").getValue(String.class);
                String address = snapshot.child("Address").getValue(String.class);
                str.add(new User("", name, mobile, city,address));
                recyclerView.setAdapter(new CustomerAdapter(Customer.this, str));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }
}