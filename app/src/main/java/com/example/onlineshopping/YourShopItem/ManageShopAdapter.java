package com.example.onlineshopping.YourShopItem;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.DataType.Item;
import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.UUID;

public class ManageShopAdapter extends RecyclerView.Adapter<ManageShopAdapter.ListViewHolder> {

    private final Context context;
    private final List<Item> items;
    public ManageShopAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_manage_shop_adapter, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemPrice.setText(item.getItemPrice());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName, itemPrice;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.manageItemName);
            itemPrice = itemView.findViewById(R.id.manageItemPrice);
        }
    }
}
