package com.example.onlineshopping.AddItemType;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlineshopping.MainActivity;
import com.example.onlineshopping.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class AddItemTypeAdapter extends RecyclerView.Adapter<AddItemTypeAdapter.ListViewHolder> {

    private final Context context;
    private final List<String> itemType;
    public AddItemTypeAdapter(Context context, List<String> itemType) {
        this.context = context;
        this.itemType = itemType;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.activity_add_item_type_adapter, null);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, int position) {
        String string = itemType.get(position);
        int x = string.indexOf("*");
        holder.itemName.setText(string.substring(x+1));

        holder.setListener(string.substring(x+1), string.substring(0,x));
    }

    @Override
    public int getItemCount() {
        return itemType.size();
    }

    class ListViewHolder extends RecyclerView.ViewHolder {
        private final TextView itemName, add;

        @SuppressLint("SetTextI18n")
        public ListViewHolder(View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemNameType);
            add = itemView.findViewById(R.id.itemTypeInfo);
        }

        public void setListener(String item, String curUser){
                add.setOnClickListener(v -> {

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Shopkeeper").child(curUser).child("Item Types").child(item).setValue("");
                });
            }
    }
}
