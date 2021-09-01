package com.example.onlineshopping.ShopItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.DataType.Item;
import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.UUID;

public class ShopItemForUserAdapter extends RecyclerView.Adapter<ShopItemForUserAdapter.ListViewHolder> {

    private final Context context;
    private final List<Item> items;
    public ShopItemForUserAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_shop_item_for_user_adapter, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice());
        holder.quantity.setText(item.getCount());

        String string = item.getItemPrice();
        int y = string.indexOf("/");
        holder.itemTotalPrice.setText(string.substring(0,y));

        String str = item.getIdi();
        int x = str.indexOf("*");

        holder.setListener(str.substring(0,x),item.getItemName(),item.getCount(),item.getItemPrice(),str.substring(x+1));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName, itemPrice, itemCount, itemTotalPrice, addToCart, orderNow;
        private final EditText quantity;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.manageItemName);
            itemPrice = itemView.findViewById(R.id.manageItemPrice);
            quantity = itemView.findViewById(R.id.quantity);
            itemTotalPrice = itemView.findViewById(R.id.manageItemtotalPrice);
            addToCart = itemView.findViewById(R.id.manageItemAddToCart);
            orderNow = itemView.findViewById(R.id.manageItemOrderNow);
            itemCount = itemView.findViewById(R.id.quantityText);
        }

        @SuppressLint("SetTextI18n")
        public void setListener(String curUser, String itemName, String count, String itemPrice, String shopId) {

            quantity.setOnClickListener(v -> {
                itemCount.setVisibility(View.VISIBLE);
                quantity.setVisibility(View.INVISIBLE);
                String string = quantity.getText().toString();
                int x = Integer.parseInt(string);
                x *= Integer.parseInt(itemPrice.substring(0,itemPrice.indexOf('/')));
                itemCount.setText(string);
                itemTotalPrice.setText(Integer.toString(x));
                //Toast.makeText(context,string, Toast.LENGTH_LONG).show();
            });

            itemCount.setOnClickListener(v -> {
                itemCount.setVisibility(View.INVISIBLE);
                quantity.setVisibility(View.VISIBLE);
                quantity.setText(count);
            });

            addToCart.setOnClickListener(v -> {
                final String fcount = (String) itemCount.getText();
                String uniqueID = UUID.randomUUID().toString();
                int x = itemPrice.indexOf("/");
                int y = (Integer.parseInt(fcount))*(Integer.parseInt(itemPrice.substring(0,x)));
                Item item = new Item(uniqueID, itemName, itemPrice, fcount, Integer.toString(y));

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("Users").child(curUser).child("My Cart").child(shopId).child(itemName).setValue(item);
                Toast.makeText(context , "Successfully added to cart" , Toast.LENGTH_LONG).show();
            });

            orderNow.setOnClickListener(v -> {
                final String fcount = (String) itemCount.getText();
                String uniqueID = UUID.randomUUID().toString();
                int x = itemPrice.indexOf("/");
                int y = (Integer.parseInt(fcount))*(Integer.parseInt(itemPrice.substring(0,x)));
                Item item = new Item(uniqueID, itemName, itemPrice, fcount, Integer.toString(y));

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                ref.child("Users").child(curUser).child("My Order").child(shopId).child(itemName).setValue(item);
                ref.child("Shopkeeper").child(shopId).child("Orders").child(curUser).child(itemName).setValue(item);
                Toast.makeText(context , "Order placed" , Toast.LENGTH_LONG).show();
            });
        }
    }
}
