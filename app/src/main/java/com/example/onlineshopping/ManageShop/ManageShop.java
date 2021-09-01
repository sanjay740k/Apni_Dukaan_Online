package com.example.onlineshopping.ManageShop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.onlineshopping.AddItemType.AddItemType;
import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.example.onlineshopping.ShopInfo.ShopInfo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageShop extends AppCompatActivity {

    private String curUser = "";
    private RecyclerView recyclerView;
    private DatabaseReference ref;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_shop);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle("Your Shop Items");
        }

        ref = FirebaseDatabase.getInstance().getReference();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        curUser =  sharedPreferences.getString("KEY", "");

        recyclerView = findViewById(R.id.newRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent addListIntent = new Intent(ManageShop.this, AddItemType.class);
            startActivity(addListIntent);
        });

        GetData();
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Shopkeeper").child(curUser).child("Item Types");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> itemType = new ArrayList<>();
                for (DataSnapshot Item: snapshot.getChildren()) {
                    itemType.add(Item.getKey());
                    recyclerView.setAdapter(new ManageShopItemType(ManageShop.this, itemType));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.info_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.info_op){
            SendUserToShopInfoActivity();
        }
        return true;
    }

    private void SendUserToShopInfoActivity() {
        Intent ShopIntent = new Intent(ManageShop.this , ShopInfo.class);
        ShopIntent.putExtra("id", curUser);
        startActivity(ShopIntent);
        //finish();
    }
}