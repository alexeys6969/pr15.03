package com.example.recycler_view_shashin;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private iOnClickInterface OnClickInterface;
    private final LayoutInflater Inflater;
    private final List<Category> Categorys;
    private Drawable BackgroundSelect;
    CategoryAdapter(Context context, List<Category> categorys, iOnClickInterface onClickInterface) {
        this.Inflater = LayoutInflater.from(context);
        this.Categorys = categorys;

        BackgroundSelect = ContextCompat.getDrawable(context, R.drawable.item_category_background_select);
        this.OnClickInterface = onClickInterface;
    }
    @Override
    public void onBindViewHolder(CategoryAdapter.ViewHolder holder, int position) {
        Category Category = Categorys.get(position);
        holder.tvName.setText(Category.Name);
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClickInterface.setClick(holder.parent, position);
            }
        });
        if(Category.Active) {
            holder.parent.setBackground(BackgroundSelect);
            holder.tvName.setTextColor(Color.RED);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = Inflater.inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return Categorys.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ConstraintLayout parent;
        final TextView tvName;
        ViewHolder(View view) {
            super(view);
            parent = view.findViewById(R.id.parent);
            tvName = view.findViewById(R.id.tv_name);
        }
    }
}
