package com.example.onlineshopping;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.example.onlineshopping.DataType.Item;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddItemDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_detail);
    }

    public static class AddItem extends AppCompatActivity {

        private String curUser, curShop, curItemType;
        private DatabaseReference ref;
        private EditText thisItemName, thisItemPrice;

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_item);

            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
            }

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                curShop = extras.getString("shop name");
                curItemType = extras.getString("item type");
            }

            ref = FirebaseDatabase.getInstance().getReference();
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
            curUser = sharedPreferences.getString("KEY", "");

            thisItemName = findViewById(R.id.addItemName);
            thisItemPrice = findViewById(R.id.addItemPrice);

            findViewById(R.id.saveThisItem).setOnClickListener(v -> SaveThisItem());
        }

        private void SaveThisItem() {
            String name = thisItemName.getText().toString().trim();
            String price = thisItemPrice.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
                Toast.makeText(AddItem.this, "Please write your item name", Toast.LENGTH_LONG).show();
            } else if (TextUtils.isEmpty(price)) {
                Toast.makeText(AddItem.this, "Please write your price", Toast.LENGTH_LONG).show();
            } else {
                String uniqueID = UUID.randomUUID().toString();
                Item item = new Item(uniqueID, name, price, "1", "price");
                ref.child("Shopkeeper").child(curUser).child("Item Types").child(curItemType).child(name).setValue(item);
                ref.keepSynced(true);
                Toast.makeText(this,"Item successfully added", Toast.LENGTH_LONG).show();
            }
        }
    }
}