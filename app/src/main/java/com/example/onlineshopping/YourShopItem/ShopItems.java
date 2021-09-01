package com.example.onlineshopping.YourShopItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.onlineshopping.AddItemDetail;
import com.example.onlineshopping.DataType.Item;
import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopItems extends AppCompatActivity {

    private String curUser, curShop, curItemType;
    private RecyclerView recyclerView;
    private DatabaseReference ref;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_items);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle("Items");
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            curShop = extras.getString("shop name");
            curItemType = extras.getString("item type");
        }

        ref = FirebaseDatabase.getInstance().getReference();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        curUser =  sharedPreferences.getString("KEY", "");

        recyclerView = findViewById(R.id.newRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        FloatingActionButton fab = findViewById(R.id.fab1);
        fab.setOnClickListener(v -> {
            Intent addListIntent = new Intent(ShopItems.this, AddItemDetail.AddItem.class);
            addListIntent.putExtra("shop name",curShop);
            addListIntent.putExtra("item type", curItemType);
            startActivity(addListIntent);
        });
        GetData();
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Shopkeeper").child(curUser).child("Item Types").child(curItemType);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Item> item = new ArrayList<>();
                for (DataSnapshot dataItem: snapshot.getChildren()) {
                    String uniqueID = UUID.randomUUID().toString();
                    item.add(new Item(uniqueID,dataItem.child("itemName").getValue().toString(),dataItem.child("itemPrice").getValue().toString(),
                            "",""));
                    recyclerView.setAdapter(new ManageShopAdapter(ShopItems.this, item));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }
}