package com.example.onlineshopping;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddShopActivity extends AppCompatActivity {

    private EditText shopName, shopkeeperName, mobileNumber, city, address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        shopName = findViewById(R.id.addShopShopName);
        shopkeeperName = findViewById(R.id.addShopShopkeeperName);
        mobileNumber = findViewById(R.id.addShopShopkeeperMobileNumber);
        city = findViewById(R.id.addShopCityName);
        address = findViewById(R.id.addShopPostalAddress);
        TextView saveInfo = findViewById(R.id.save);
        saveInfo.setOnClickListener(v -> SavesInfo());
    }

    private void SavesInfo() {
        String saveShopName = shopName.getText().toString().trim();
        String saveShopkeeperName = shopkeeperName.getText().toString().trim();
        String saveMobileNumber = mobileNumber.getText().toString().trim();
        String saveCity = city.getText().toString().trim();
        String saveAddress = address.getText().toString().trim();

        if(TextUtils.isEmpty(saveShopName)){
            Toast.makeText(AddShopActivity.this ,"Please write your Shop name", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(saveShopkeeperName)){
            Toast.makeText(AddShopActivity.this ,"Please write your Shopkeeper name", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(saveMobileNumber)){
            Toast.makeText(AddShopActivity.this ,"Please write your Mobile number", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(saveCity)){
            Toast.makeText(AddShopActivity.this ,"Please write your City name", Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(saveAddress)){
            Toast.makeText(AddShopActivity.this ,"Please write your address", Toast.LENGTH_LONG).show();
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("shop name", saveShopName);
            intent.putExtra("shopkeeper name", saveShopkeeperName);
            intent.putExtra("mobile number", saveMobileNumber);
            intent.putExtra("city name", saveCity);
            intent.putExtra("address", saveAddress);
            startActivity(intent);
        }
    }
}