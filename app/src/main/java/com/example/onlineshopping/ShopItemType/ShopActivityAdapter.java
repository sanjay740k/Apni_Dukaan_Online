package com.example.onlineshopping.ShopItemType;

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
import com.example.onlineshopping.ShopItem.ShopItemForUser;

import java.util.List;

public class ShopActivityAdapter extends RecyclerView.Adapter<ShopActivityAdapter.ListViewHolder> {

    private final Context context;
    private final List<String> itemType;
    public ShopActivityAdapter(Context context, List<String> itemType) {
        this.context = context;
        this.itemType = itemType;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_shop_adapter, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        String string = itemType.get(position);
        int x = string.indexOf("*");
        holder.itemName.setText(string.substring(x+1));

        holder.setListener(string.substring(0, x), string.substring(x+1));
    }

    @Override
    public int getItemCount() {
        return itemType.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.youritemNameType);
        }

        public void setListener(String shopid, String curTtemType) {
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ShopItemForUser.class);
                intent.putExtra("id",shopid);
                intent.putExtra("item type", curTtemType);
                context.startActivity(intent);
            });
        }
    }
}
