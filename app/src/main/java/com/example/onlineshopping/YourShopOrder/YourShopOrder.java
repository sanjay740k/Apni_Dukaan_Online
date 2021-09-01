package com.example.onlineshopping.YourShopOrder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.onlineshopping.Cart.MyCart;
import com.example.onlineshopping.Cart.MycartAdapter;
import com.example.onlineshopping.DataType.Item;
import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class YourShopOrder extends AppCompatActivity {

    private String curUser, customer, customerName;
    private RecyclerView recyclerView;
    private DatabaseReference ref;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.your_shop_order);

        ref = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            customer = extras.getString("customer");
            customerName = extras.getString("name");
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle(customerName);
        }

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        curUser = sharedPreferences.getString("KEY", "");

        recyclerView = findViewById(R.id.myCartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        GetData();
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Shopkeeper").child(curUser).child("Orders").child(customer);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Item> item = new ArrayList<>();
                for (DataSnapshot dataItem: snapshot.getChildren()) {
                    String uniqueID = UUID.randomUUID().toString();
                    item.add(new Item(uniqueID, dataItem.child("itemName").getValue().toString(),dataItem.child("itemPrice").getValue().toString(),
                            dataItem.child("count").getValue().toString(),dataItem.child("totalPrice").getValue().toString()));
                    recyclerView.setAdapter(new YourShopOrderAdapter(YourShopOrder.this, item));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }
}