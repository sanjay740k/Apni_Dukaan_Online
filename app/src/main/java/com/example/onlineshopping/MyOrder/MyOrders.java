package com.example.onlineshopping.MyOrder;

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

public class MyOrders extends AppCompatActivity {

    private String curUser, shopId, curItemType;
    private RecyclerView recyclerView;
    private DatabaseReference ref;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            actionBar.setTitle("My Ordered Items");
        }

        ref = FirebaseDatabase.getInstance().getReference();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            shopId = extras.getString("id");
        }

        curUser = GetUserMobileId();

        recyclerView = findViewById(R.id.myCartRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));

        GetData();
    }

    public String GetUserMobileId() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        return sharedPreferences.getString("KEY", "");
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Users").child(curUser).child("My Order").child(shopId);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Item> item = new ArrayList<>();
                for (DataSnapshot dataItem: snapshot.getChildren()) {
                    //String uniqueID = UUID.randomUUID().toString();
                    item.add(new Item(curUser+"*"+shopId ,dataItem.child("itemName").getValue().toString(),dataItem.child("itemPrice").getValue().toString(),
                            dataItem.child("count").getValue().toString(),dataItem.child("totalPrice").getValue().toString()));
                    recyclerView.setAdapter(new MyOrdersAdapter(MyOrders.this, item));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }
}