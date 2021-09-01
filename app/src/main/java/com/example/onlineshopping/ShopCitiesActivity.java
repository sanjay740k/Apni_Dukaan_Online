package com.example.onlineshopping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.DataType.Shop;
import com.example.onlineshopping.ShowShop.ShowShopsActivity;

import java.util.List;

public class ShopCitiesActivity extends RecyclerView.Adapter<ShopCitiesActivity.ListViewHolder> {

    private final Context context;
    private final List<Shop> shops;
    public ShopCitiesActivity(Context context, List<Shop> shops) {
        this.context = context;
        this.shops = shops;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_shop_cities, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        Shop shop = shops.get(position);
        holder.cityName.setText(shop.getCityName());

        holder.setListener(shop.getId());
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView cityName;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.citiesCityName);
        }

        public void setListener(String id) {
            itemView.setOnClickListener(v ->{
                Intent intent = new Intent(context, ShowShopsActivity.class);
                intent.putExtra("uid", id);
                intent.putExtra("city name", cityName.getText().toString());
                context.startActivity(intent);
            });
        }
    }
}
