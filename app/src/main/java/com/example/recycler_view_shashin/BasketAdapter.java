package com.example.recycler_view_shashin;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    public iOnClickInterface Delete, Cost;
    public LayoutInflater Inflater;
    public ArrayList<Basket> BasketItems;

    private ViewHolder currentOpenHolder = null;

    public BasketAdapter(Context context, ArrayList<Basket> basketItems,
                         iOnClickInterface delete, iOnClickInterface cost) {
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
        Basket item = BasketItems.get(position);

        holder.tvName.setText(item.Item.Name + " " + item.Item.Model);
        holder.tvPrice.setText("₽" + item.Item.Price);
        holder.tvCount.setText(String.valueOf(item.Count));

        holder.content.setTranslationX(0);
        holder.swipeState = 0;

        holder.bthPlus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {
                BasketItems.get(pos).Count++;
                notifyItemChanged(pos);
                Cost.setClick(v, pos);
            }
        });

        holder.bthMinus.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {
                Basket basket = BasketItems.get(pos);

                if (basket.Count > 1) {
                    basket.Count--;
                    notifyItemChanged(pos);
                    Cost.setClick(v, pos);
                }
            }
        });

        holder.bthDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION && pos < BasketItems.size()) {
                BasketItems.remove(pos);
                notifyItemRemoved(pos);
                notifyItemRangeChanged(pos, BasketItems.size());

                currentOpenHolder = null;

                Delete.setClick(v, pos);
                Cost.setClick(v, pos);
            }
        });

        holder.itemView.setOnTouchListener(new View.OnTouchListener() {
            float startX;
            float startTranslationX;
            boolean swiping = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float maxSwipe = dpToPx(v.getContext(), 70);
                float threshold = dpToPx(v.getContext(), 35);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getRawX();
                        startTranslationX = holder.content.getTranslationX();
                        swiping = false;

                        if (currentOpenHolder != null && currentOpenHolder != holder) {
                            closeSwipe(currentOpenHolder, true);
                            currentOpenHolder = null;
                        }

                        return true;

                    case MotionEvent.ACTION_MOVE:
                        float diffX = event.getRawX() - startX;

                        if (Math.abs(diffX) > 10) {
                            swiping = true;
                        }

                        if (swiping) {
                            float newX = startTranslationX + diffX;

                            if (newX > maxSwipe) {
                                newX = maxSwipe;
                            }

                            if (newX < -maxSwipe) {
                                newX = -maxSwipe;
                            }

                            holder.content.setTranslationX(newX);
                            return true;
                        }

                        return true;

                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        float currentX = holder.content.getTranslationX();

                        if (currentX > threshold) {
                            animateTo(holder.content, maxSwipe);
                            holder.swipeState = 1;
                            currentOpenHolder = holder;
                        } else if (currentX < -threshold) {
                            animateTo(holder.content, -maxSwipe);
                            holder.swipeState = -1;
                            currentOpenHolder = holder;
                        } else {
                            closeSwipe(holder, true);
                            currentOpenHolder = null;
                        }

                        return true;
                }

                return false;
            }
        });
    }

    private void closeSwipe(ViewHolder holder, boolean animate) {
        if (holder == null || holder.content == null) return;

        if (animate) {
            animateTo(holder.content, 0);
        } else {
            holder.content.setTranslationX(0);
        }

        holder.swipeState = 0;
    }

    private void animateTo(View view, float targetX) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(
                view,
                "translationX",
                view.getTranslationX(),
                targetX
        );

        animator.setDuration(180);
        animator.start();
    }

    private float dpToPx(Context context, int dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    @Override
    public int getItemCount() {
        return BasketItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvPrice, tvCount;
        public ImageView bthPlus, bthMinus;
        public LinearLayout bthDelete, bthCount;
        public View content;

        public int swipeState = 0;

        ViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_name);
            tvPrice = view.findViewById(R.id.tv_price);
            tvCount = view.findViewById(R.id.tv_count);

            bthPlus = view.findViewById(R.id.bthPlus);
            bthMinus = view.findViewById(R.id.bthMinus);

            bthDelete = view.findViewById(R.id.ll_delete);
            bthCount = view.findViewById(R.id.ll_count);

            content = view.findViewById(R.id.item_content);
        }
    }
}