package com.example.onlineshopping.MyOrder;

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
import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyOrderShop extends AppCompatActivity {

    private String curUser, curShop, curItemType;
    private RecyclerView recyclerView;
    private DatabaseReference ref;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_order_shop);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle("My Orders");
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
        DatabaseReference getData = ref.child("Users").child(curUser).child("My Order");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data: snapshot.getChildren()) {
                    GetShopName(data.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }

    private void GetShopName(String key) {
        DatabaseReference getData = ref.child("Shopkeeper").child(key).child("Info");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> str = new ArrayList<>();
                String value = snapshot.child("shopName").getValue(String.class);
                str.add(key+"*"+value);
                recyclerView.setAdapter(new MyOrderShopAdapter(MyOrderShop.this, str));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }
}