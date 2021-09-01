package com.example.onlineshopping.ShopItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.onlineshopping.DataType.Item;
import com.example.onlineshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ShopItemForUser extends AppCompatActivity {

    private String shopId, curUser, curItemType;
    private RecyclerView recyclerView;
    private DatabaseReference ref;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_item_for_user);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle("Items");
        }

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shopId = extras.getString("id");
            curItemType = extras.getString("item type");
        }

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        curUser =  sharedPreferences.getString("KEY", "");

        ref = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.newRecyclerViewForUser);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        GetData();
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Shopkeeper").child(shopId).child("Item Types").child(curItemType);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Item> item = new ArrayList<>();
                for (DataSnapshot dataItem: snapshot.getChildren()) {
                    //String uniqueID = UUID.randomUUID().toString();
                    item.add(new Item(curUser+"*"+shopId,dataItem.child("itemName").getValue().toString(),dataItem.child("itemPrice").getValue().toString(),
                            dataItem.child("count").getValue().toString() ,""));
                    recyclerView.setAdapter(new ShopItemForUserAdapter(ShopItemForUser.this, item));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }
}