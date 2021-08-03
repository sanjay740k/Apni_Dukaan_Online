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

        //holder.setListener(shop.getId());
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView cityName;
        //private final String context;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            cityName = itemView.findViewById(R.id.citiesCityName);
            TextView cityDistance = itemView.findViewById(R.id.citiesCitydistance);
            float x = (float) 0.0;
            String s = Float.toString(x);
            cityDistance.setText("Distance = "+ s +"KM");

            itemView.setOnClickListener(v -> {

                Intent intent = new Intent(context, ShowShopsActivity.class);
                //intent.putExtra("city name", cityName.getText().toString());
                context.startActivity(intent);
            });
        }

        //public void setListener(String id){
        //    deleteItem.setOnClickListener(v -> {
        //        SharedPreferences sharedPreferences = context.getSharedPreferences("sharedNotes", Context.MODE_PRIVATE);
        //        sharedPreferences.edit().remove(id).apply();
        //        position = getAdapterPosition();
        //        removeItemFromList(position);
        //    });
        //}
    }

    //private void removeItemFromList(int position) {

    //    shops.remove(position);
    //    notifyItemRemoved(position);
    //}

    //public static Bitmap decodeBase64(String input) {
    //    byte[] decodedByte = Base64.decode(input, 0);
    //    return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    //}
}
