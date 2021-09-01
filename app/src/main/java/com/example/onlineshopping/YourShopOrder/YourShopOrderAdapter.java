package com.example.onlineshopping.YourShopOrder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.DataType.Item;
import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.UUID;

public class YourShopOrderAdapter extends RecyclerView.Adapter<YourShopOrderAdapter.ListViewHolder> {

    private final Context context;
    private final List<Item> items;
    public YourShopOrderAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.your_shop_order_adapter, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice());
        holder.itemCount.setText(item.getCount());
        holder.itemTotalPrice.setText(item.getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName, itemPrice, itemCount, itemTotalPrice;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.manageItemName);
            itemPrice = itemView.findViewById(R.id.manageItemPrice);
            itemCount = itemView.findViewById(R.id.manageItemCount);
            itemTotalPrice = itemView.findViewById(R.id.manageItemtotalPrice);
        }
    }
}