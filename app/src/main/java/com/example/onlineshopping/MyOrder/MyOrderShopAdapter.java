package com.example.onlineshopping.MyOrder;

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

import java.util.List;

public class MyOrderShopAdapter extends RecyclerView.Adapter<MyOrderShopAdapter.ListViewHolder> {

    private final Context context;
    private final List<String> shopName;
    public MyOrderShopAdapter(Context context, List<String> shop) {
        this.context = context;
        this.shopName = shop;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.my_order_shop_adapter, null);
        return new ListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        String string = shopName.get(position);
        int x = string.indexOf("*");
        holder.shopname.setText("Shop Name - " + string.substring(x+1));

        holder.setListener(string.substring(0,x));
    }

    @Override
    public int getItemCount() {
        return shopName.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView shopname;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            shopname = itemView.findViewById(R.id.cartShopName);
        }

        public void setListener(String shopId) {
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, MyOrders.class);
                intent.putExtra("id", shopId);
                context.startActivity(intent);
            });
        }
    }
}
