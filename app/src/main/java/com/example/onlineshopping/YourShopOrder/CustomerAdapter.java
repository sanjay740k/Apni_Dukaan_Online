package com.example.onlineshopping.YourShopOrder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.DataType.User;
import com.example.onlineshopping.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ListViewHolder> {

    private final Context context;
    private final List<User> customer;
    public CustomerAdapter(Context context, List<User> customer) {
        this.context = context;
        this.customer = customer;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.customer_adapter, null);
        return new ListViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        User user = customer.get(position);
        holder.customerName.setText("Name - " + user.getUserName());
        holder.customerCity.setText("City - " + user.getCityName());
        holder.customerAddress.setText("Address - " + user.getAddress());

        holder.setListener(user.getUserMobileNumber(), user.getUserName());
    }

    @Override
    public int getItemCount() {
        return customer.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView customerName, customerCity, customerAddress;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            customerName = itemView.findViewById(R.id.customerName);
            customerAddress = itemView.findViewById(R.id.customerAddress);
            customerCity = itemView.findViewById(R.id.customerCity);
        }

        public void setListener(String customer, String curcustomername) {
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, YourShopOrder.class);
                intent.putExtra("customer", customer);
                intent.putExtra("name", curcustomername);
                context.startActivity(intent);
            });
        }
    }
}
