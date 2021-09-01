package com.example.onlineshopping;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopping.Cart.MyCartShop;
import com.example.onlineshopping.DataType.Shop;
import com.example.onlineshopping.Login.LoginActivity;
import com.example.onlineshopping.Login.RegisterAsShopkeeperActivity;
import com.example.onlineshopping.MyOrder.MyOrderShop;
import com.example.onlineshopping.ShopInfo.AddShopActivity;
import com.example.onlineshopping.YourShop.YourShopsActivity;
import com.example.onlineshopping.YourShopOrder.Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;
    private FirebaseUser firebaseUser;
    private String flag = "000", curUser = "+917389695730";
    private RecyclerView recyclerView;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.background));
        }

        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void GetData() {
        DatabaseReference getData = ref.child("Cities");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Shop> shop = new ArrayList<>();
                for (DataSnapshot CityName: snapshot.getChildren()) {
                    shop.add(new Shop(curUser, "", "", "", CityName.getKey(), ""));
                    recyclerView.setAdapter(new ShopCitiesActivity(MainActivity.this, shop));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }

    private void HideMenu() {
        DatabaseReference hideMenu = ref.child("Shopkeeper");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(curUser)) {
                    flag = "111";
                    invalidateOptionsMenu();
                }
                else {
                    flag = "104";
                    invalidateOptionsMenu();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        hideMenu.addListenerForSingleValueEvent(eventListener);
    }

    private void GetUId() {
        String useremail = null;
        if(this.firebaseUser != null) {
            useremail = this.firebaseUser.getEmail();
            int x = useremail.indexOf("@");
            useremail = useremail.substring(0, x);
        }

        DatabaseReference getData = ref.child("Mail").child(useremail);
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String value = snapshot.child("mobile").getValue(String.class);
                SetUserMobileAsId(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        getData.addListenerForSingleValueEvent(eventListener);
    }

    private void SetUserMobileAsId(String value) {
        //Toast.makeText(this, value, Toast.LENGTH_LONG).show();
        curUser = value;
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY", value);
        editor.apply();
        HideMenu();
        GetData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseUser == null){
            SendUserToLoginWithMobileNumberActivity();
        }
        else {
            GetUId();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);

        if(flag.equals("111")) {
            MenuItem item = menu.findItem(R.id.Register_as_shopkeeper_op);
            item.setVisible(false);
            item = menu.findItem(R.id.add_shop_op);
            item.setVisible(true);
            item = menu.findItem(R.id.your_shops_op);
            item.setVisible(true);
            item = menu.findItem(R.id.your_shop_orders_op);
            item.setVisible(true);
            item = menu.findItem(R.id.your_shop_orders_op);
            item.setVisible(true);
        }
        else if(flag.equals("104")){
            MenuItem item = menu.findItem(R.id.Register_as_shopkeeper_op);
            item.setVisible(true);
        }
        flag = "000";
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.main_logout){
            firebaseAuth.signOut();
            //firebaseAuth.signOut();
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
        else if(item.getItemId() == R.id.my_orders_op){
            SendUserToMyOrdersActivity();
        }
        else if(item.getItemId() == R.id.your_shop_orders_op){
            SendUserToYourShopsOrdersActivity();
        }
        else if(item.getItemId() == R.id.profile_op){
            SendUserToProfileActivity();
        }
        else if(item.getItemId() == R.id.my_cart_op){
            SendUserToMyCartActivity();
        }
        return true;
    }

    private void SendUserToProfileActivity() {
        Intent profileIntent = new Intent(this, Profile.class);
        profileIntent.putExtra("id", curUser);
        startActivity(profileIntent);
    }

    private void SendUserToYourShopsOrdersActivity() {
        Intent yourShopsOrderIntent = new Intent(this, Customer.class);
        yourShopsOrderIntent.putExtra("id", curUser);
        startActivity(yourShopsOrderIntent);
    }

    private void SendUserToMyOrdersActivity() {
        Intent myOrderIntent = new Intent(this, MyOrderShop.class);
        myOrderIntent.putExtra("id", curUser);
        startActivity(myOrderIntent);
    }

    private void SendUserToMyCartActivity() {
        Intent myCartIntent = new Intent(this, MyCartShop.class);
        myCartIntent.putExtra("id", curUser);
        startActivity(myCartIntent);
    }

    private void SendUserToRegisterAsShopkeeperActivity() {
        Intent registerAsShopkeeperIntent = new Intent(MainActivity.this , RegisterAsShopkeeperActivity.class);
        registerAsShopkeeperIntent.putExtra("id", curUser);
        startActivity(registerAsShopkeeperIntent);
        //finish();
    }

    private void SendUserToYourShopsActivity() {
        Intent yourShopsIntent = new Intent(MainActivity.this , YourShopsActivity.class);
        yourShopsIntent.putExtra("id", curUser);
        startActivity(yourShopsIntent);
        //finish();
    }

    private void SendUserToAddShopActivity() {
        Intent addShopIntent = new Intent(MainActivity.this , AddShopActivity.class);
        addShopIntent.putExtra("id", curUser);
        startActivity(addShopIntent);
        //finish();
    }

    private void SendUserToLoginWithMobileNumberActivity() {
        Intent loginIntent = new Intent(MainActivity.this , LoginActivity.class);
        //loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        //finish();
    }
}