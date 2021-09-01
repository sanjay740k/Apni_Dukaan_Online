package com.example.onlineshopping.MyOrder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.DataType.Item;
import com.example.onlineshopping.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.ListViewHolder> {

    private final Context context;
    private final List<Item> items;
    public MyOrdersAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.my_orders_adapter, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice());
        holder.itemCount.setText(item.getCount());
        holder.itemTotalPrice.setText(item.getTotalPrice());

        String str = item.getIdi();
        int x = str.indexOf("*");

        holder.setListener(str.substring(x+1),item.getItemName(),item.getCount(),item.getItemPrice(),str.substring(0,x));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName, itemPrice, itemCount, itemTotalPrice, cancelOrder;
        private final EditText quantity;
        private int position;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.manageItemName);
            itemPrice = itemView.findViewById(R.id.manageItemPrice);
            quantity = itemView.findViewById(R.id.quantity);
            itemCount = itemView.findViewById(R.id.quantityText);
            itemTotalPrice = itemView.findViewById(R.id.manageItemtotalPrice);
            cancelOrder = itemView.findViewById(R.id.myCartOrderCancel);
        }

        @SuppressLint("SetTextI18n")
        public void setListener(String shopId, String itemName, String count, String itemPrice, String curUser) {

            quantity.setOnClickListener(v -> {
                itemCount.setVisibility(View.VISIBLE);
                quantity.setVisibility(View.INVISIBLE);
                String string = quantity.getText().toString();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                        .child("Users").child(shopId).child("My Cart").child(curUser).child(itemName);
                ref.child("count").setValue(string);
                int x = Integer.parseInt(string);
                x *= Integer.parseInt(itemPrice.substring(0,itemPrice.indexOf('/')));
                ref.child("totalPrice").setValue(Integer.toString(x));
                itemCount.setText(string);
                itemTotalPrice.setText(Integer.toString(x));
                //Toast.makeText(context,string, Toast.LENGTH_LONG).show();
            });

            itemCount.setOnClickListener(v -> {
                itemCount.setVisibility(View.INVISIBLE);
                quantity.setVisibility(View.VISIBLE);
                quantity.setText(count);
            });

            cancelOrder.setOnClickListener(v -> {
                position = getAdapterPosition();
                removeItemFromList(position);
                removeItemFromFirebase(shopId, itemName, curUser);
                Toast.makeText(context , "Order Canceled" , Toast.LENGTH_LONG).show();
            });
        }
    }

    private void removeItemFromFirebase(String shopId, String itemName, String curUser) {

        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(curUser).child("My Order").child(shopId).child(itemName);
        ref1.removeValue();
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference()
                .child("Shopkeeper").child(shopId).child("Orders").child(curUser).child(itemName);
        ref2.removeValue();
    }

    private void removeItemFromList(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }
}
