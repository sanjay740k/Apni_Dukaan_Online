package com.example.onlineshopping.AddItemType;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddItemType extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;
    private String curShop, item;
    public String curUser;
    private RecyclerView recyclerView;
    private EditText addNewItem;
    private TextView addItem, saveItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_type);

        addNewItem = findViewById(R.id.addNewItemType);
        addItem = findViewById(R.id.newItemType);
        saveItem = findViewById(R.id.saveItem);

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        curUser =  sharedPreferences.getString("KEY", "");

        //Bundle extras = getIntent().getExtras();
        //if (extras != null) curShop = extras.getString("shop name");

        addItem.setOnClickListener(v -> AddNewItemType());
        saveItem.setOnClickListener(v -> SaveNewItem());

        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.addItemrecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        GetData();
    }

    private void SaveNewItem() {

        item = addNewItem.getText().toString();
        ref.child("Item Types").child(item).setValue("");
        ref.child("Shopkeeper").child(curUser).child("Item Types").child(item).setValue("");
        addItem.setVisibility(View.VISIBLE);
        addNewItem.setVisibility(View.INVISIBLE);
        saveItem.setVisibility(View.INVISIBLE);
        GetData();
    }

    private void AddNewItemType() {
        addItem.setVisibility(View.INVISIBLE);
        addNewItem.setVisibility(View.VISIBLE);
        saveItem.setVisibility(View.VISIBLE);
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Item Types");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> itemType = new ArrayList<>();
                for (DataSnapshot Item: snapshot.getChildren()) {
                    itemType.add(curUser+"*"+Item.getKey());
                    recyclerView.setAdapter(new AddItemTypeAdapter(AddItemType.this, itemType));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }
}