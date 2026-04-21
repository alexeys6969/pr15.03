package com.example.recycler_view_shashin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter>.ViewHolder {
    public iOnClickInterface Delete, Cost;
    public LayoutInflater Inflater;
    public ArrayList<Basket> BasketItems;

    public BasketAdapter(Context context, ArrayList<Basket> basketItems, iOnClickInterface delete, iOnClickInterface cost) {
        this.Inflater = LayoutInflater.from(context);
        this.BasketItems = basketItems;
        this.Delete = delete;
        this.Cost = cost;
    }
    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = Inflater.inflate(R.layout.item_basket, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(BasketAdapter.ViewHolder holder, int position) {
        Basket Item = BasketItems.get(position);
        holder.tvName.setText(Item.Item.Name);
        holder.tvPrice.setText("₽" + String.valueOf(Item.Item.Price));
        holder.tvCount.setText(String.valueOf(Item.Count));

        holder.bthPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item.Count++;
                holder.tvCount.setText(String.valueOf(Item.Count));
                Cost.setClick(view, position);
            }
        });
        holder.bthMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item.Count--;
                holder.tvCount.setText(String.valueOf(Item.Count));
                Cost.setClick(view, position);
            }
        });
        holder.bthDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Delete.setClick(view, position);
                Cost.setClick(view, position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return BasketItems.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvPrice, tvCount;
        public ImageView bthPlus, bthMinus;
        public LinearLayout bthDelete;
        ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvPrice = view.findViewById(R.id.tv_price);
            tvCount = view.findViewById(R.id.tv_count);
            bthPlus = view.findViewById(R.id.bthPlus);
            bthMinus = view.findViewById(R.id.bthMinus);
            bthDelete = view.findViewById(R.id.ll_delete);
        }
    }
}
