package com.example.onlineshopping.ShopItemType;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.onlineshopping.R;
import com.example.onlineshopping.ShopInfo.ShopInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private String shopId, curShop="123", curUser;
    private DatabaseReference ref;
    private RecyclerView recyclerView;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shopId = extras.getString("id");
            curShop = extras.getString("shopname");
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle(curShop);
        }

        ref = FirebaseDatabase.getInstance().getReference();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.shopItemsRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        GetData();
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Shopkeeper").child(shopId).child("Item Types");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> itemType = new ArrayList<>();
                for (DataSnapshot Item: snapshot.getChildren()) {
                    itemType.add(shopId + "*" +Item.getKey());
                    recyclerView.setAdapter(new ShopActivityAdapter(ShopActivity.this, itemType));
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
        Intent ShopIntent = new Intent(ShopActivity.this , ShopInfo.class);
        ShopIntent.putExtra("id", shopId);
        startActivity(ShopIntent);
        //finish();
    }
}