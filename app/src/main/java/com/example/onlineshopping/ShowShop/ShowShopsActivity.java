package com.example.onlineshopping.ShowShop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.example.onlineshopping.DataType.Shop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShowShopsActivity extends AppCompatActivity {

    private DatabaseReference ref;
    private RecyclerView recyclerView;
    private String curUser, city;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shops);

        ref = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            city = extras.getString("city name");
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle(city);
            //actionBar.setDisplayHomeAsUpEnabled(true); // back
        }

        recyclerView = findViewById(R.id.showshopsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GetData();
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Cities").child(city);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Shop> shop = new ArrayList<>();
                for (DataSnapshot Shopid: snapshot.getChildren()) {
                    String Shopname = Shopid.child("Info").child("shopName").getValue().toString();
                    shop.add(new Shop(curUser, Shopname, "", Shopid.getKey(), "", ""));
                    recyclerView.setAdapter(new ShowShopsAdapter(ShowShopsActivity.this, shop));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }
}