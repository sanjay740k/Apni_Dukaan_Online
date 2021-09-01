package com.example.onlineshopping.YourShop;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.ManageShop.ManageShop;
import com.example.onlineshopping.R;
import com.example.onlineshopping.DataType.Shop;

import java.util.List;

public class YourShopsAdapter extends RecyclerView.Adapter<YourShopsAdapter.ListViewHolder> {

    private final Context context;
    private final List<Shop> shops;
    public YourShopsAdapter(Context context, List<Shop> shops) {
        this.context = context;
        this.shops = shops;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_your_shops_adapter, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Shop shop = shops.get(position);
        holder.shopName.setText(shop.getShopName());
        holder.mobileNo.setText(shop.getShopkeeperMobileNumber());
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

            shopName = itemView.findViewById(R.id.yourshopsShopName);
            mobileNo = itemView.findViewById(R.id.yourshopDistance);

            itemView.setOnClickListener(v -> {

                Intent intent = new Intent(context, ManageShop.class);
                intent.putExtra("shop name", shopName.getText().toString());
                context.startActivity(intent);
            });
        }
    }
}
