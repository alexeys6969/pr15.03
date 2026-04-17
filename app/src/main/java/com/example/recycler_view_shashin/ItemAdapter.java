package com.example.recycler_view_shashin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{
    private LayoutInflater Inflater;
    private List<Item> Items;
    ItemAdapter(Context context, List<Item> items) {
        this.Inflater = LayoutInflater.from(context);
        this.Items = items;
    }
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = Inflater.inflate(R.layout.item_card, parent, false);
        return new ItemAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder holder, int position) {
        Item Item = Items.get(position);
        holder.TvName.setText(Item.Name);
        holder.TvModel.setText(Item.Model);
        holder.TvPrice.setText("₽" + String.valueOf(Item.Price));
    }
    @Override
    public int getItemCount() { return Items.size(); }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView TvName, TvModel, TvPrice;
        ViewHolder(View view) {
            super(view);
            TvName = view.findViewById(R.id.tv_name);
            TvModel = view.findViewById(R.id.tv_model);
            TvPrice = view.findViewById(R.id.tv_price);
        }
    }
}
