package com.example.onlineshopping.ManageShop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.R;
import com.example.onlineshopping.YourShopItem.ShopItems;

import java.util.List;

public class ManageShopItemType extends RecyclerView.Adapter<ManageShopItemType.ListViewHolder> {

    private final Context context;
    private final List<String> itemType;
    public ManageShopItemType(Context context, List<String> itemType) {
        this.context = context;
        this.itemType = itemType;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_manage_shop_item_type, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        String string = itemType.get(position);
        holder.itemName.setText(string);

        holder.setListener(string);
    }

    @Override
    public int getItemCount() {
        return itemType.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName;
        //private final String context;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.youritemNameType);
        }

        public void setListener(String itemType) {
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ShopItems.class);
                intent.putExtra("item type", itemType);
                context.startActivity(intent);
            });
        }
    }
}
