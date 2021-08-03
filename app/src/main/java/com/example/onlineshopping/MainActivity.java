package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Intent addListIntent;
    private FirebaseAuth firebaseAuth;
    //private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    SharedPreferences.Editor editor;
    private String name, shopkeeperName, shopkeeperMobileNumber, cityName, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        String currentUId = GetUserId();
        //DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        //ref.keepSynced(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        List<Shop> shop = new ArrayList<>();
        recyclerView.setAdapter(new ShopCitiesActivity(this, shop));

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("sharedNotes", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("shop name");
            shopkeeperName = extras.getString("shopkeeper name");
            shopkeeperMobileNumber = extras.getString("mobile number");
            cityName = extras.getString("city name");
            address = extras.getString("address");

            String uniqueID = UUID.randomUUID().toString();
            Set<String> hash_Set = new HashSet<>();

            hash_Set.add("a"+name);
            hash_Set.add("b"+shopkeeperName);
            hash_Set.add("c"+shopkeeperMobileNumber);
            hash_Set.add("d"+cityName);
            hash_Set.add("e"+address);
            editor.putStringSet(uniqueID, hash_Set);
            editor.apply();
        }

        Map<String, ?> prefsMap = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry: prefsMap.entrySet()) {
            String  uniqueID = entry.getKey();
            Set<String> hash_Set = sharedPreferences.getStringSet(uniqueID, new HashSet<>());

            for (String s : hash_Set) {
                if(s.charAt(0)=='a')name = s.substring(1);
                else if(s.charAt(0)=='b')shopkeeperName = s.substring(1);
                else if(s.charAt(0)=='c')shopkeeperMobileNumber = s.substring(1);
                else if(s.charAt(0)=='d')cityName = s.substring(1);
                else if(s.charAt(0)=='e')address = s.substring(1);
            }

            shop.add(new Shop(uniqueID, name, shopkeeperName, shopkeeperMobileNumber, cityName, address));

            //String id = ref.push().getKey();
            //ref.child(id).setValue(shop);
        }
    }

    protected String GetUserId() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userMobileNumber = null;
        if(this.firebaseUser != null) {
            userMobileNumber = this.firebaseUser.getPhoneNumber();
        }
        return userMobileNumber;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser == null){
            SendUserToLoginWithMobileNumberActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout){
            firebaseAuth.signOut();
            SendUserToLoginWithMobileNumberActivity();
        }
        else if(item.getItemId() == R.id.Register_as_shopkeeper_op){
            SendUserToRegisterAsShopkeeperActivity();
        }
        else if(item.getItemId() == R.id.add_shop_op){
            SendUserToAddShopActivity();
        }
        else if(item.getItemId() == R.id.your_shops_op){
            SendUserToYourShopsActivity();
        }
        else if(item.getItemId() == R.id.search_op){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("message");

            myRef.setValue("Hello, World!");
        }
        return true;
    }

    private void SendUserToRegisterAsShopkeeperActivity() {
        Intent registerAsShopkeeperIntent = new Intent(MainActivity.this , RegisterAsShopkeeperActivity.class);
        startActivity(registerAsShopkeeperIntent);
        //finish();
    }

    private void SendUserToYourShopsActivity() {
        Intent yourShopsIntent = new Intent(MainActivity.this , YourShopsActivity.class);
        startActivity(yourShopsIntent);
        //finish();
    }

    private void SendUserToAddShopActivity() {
        Intent addShopIntent = new Intent(MainActivity.this , AddShopActivity.class);
        startActivity(addShopIntent);
        //finish();
    }

    private void SendUserToLoginWithMobileNumberActivity() {
        Intent loginIntent = new Intent(MainActivity.this , LoginWithMobileNumberActivity.class);
        //loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        //finish();
    }
}