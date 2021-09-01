package com.example.onlineshopping.ShowShop;

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
import com.example.onlineshopping.DataType.Shop;
import com.example.onlineshopping.ShopItemType.ShopActivity;

import java.util.List;

public class ShowShopsAdapter extends RecyclerView.Adapter<ShowShopsAdapter.ListViewHolder> {

    private final Context context;
    private final List<Shop> shops;
    public ShowShopsAdapter(Context context, List<Shop> shops) {
        this.context = context;
        this.shops = shops;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_show_shops_adapter, null);
        return new ListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Shop shop = shops.get(position);
        holder.shopName.setText("Shop Name - " + shop.getShopName());
        holder.mobileNo.setText("Mobile Number - " + shop.getShopkeeperMobileNumber());

        holder.setListener(shop.getShopName(),shop.getShopkeeperMobileNumber());
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView shopName, mobileNo;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            shopName = itemView.findViewById(R.id.shopsShopName);
            mobileNo = itemView.findViewById(R.id.shopDistance);
        }

        public void setListener(String shopName, String shopkeeperMobileNumber){
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ShopActivity.class);
                intent.putExtra("id", shopkeeperMobileNumber);
                intent.putExtra("shopname", shopName);
                context.startActivity(intent);
            });
        }
    }
}
