package com.example.recycler_view_shashin;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BasketActivity extends AppCompatActivity {

    private RecyclerView basketList;
    private BasketAdapter basketAdapter;
    private TextView tvSum, tvAllSum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_basket);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Инициализация
        basketList = findViewById(R.id.basket_list);
        tvSum = findViewById(R.id.tv_sum);
        tvAllSum = findViewById(R.id.tv_all_sum);

        // БЕРЕМ СПИСОК ИЗ MainActivity (ГЛАВНЫЙ ИСТОЧНИК ДАННЫХ)
        ArrayList<Basket> basketItems = MainActivity.init.BasketList;

        // Обработчик удаления
        iOnClickInterface deleteClickListener = new iOnClickInterface() {
            @Override
            public void setClick(View view, int position) {
                int realPosition = position;
                if (realPosition >= 0 && realPosition < basketItems.size()) {
                    basketItems.remove(realPosition);
                    basketAdapter.notifyItemRemoved(realPosition);
                    updateSum();
                }
            }
        };

        // Обработчик изменения количества (плюс/минус)
        iOnClickInterface costClickListener = new iOnClickInterface() {
            @Override
            public void setClick(View view, int position) {
                updateSum();
            }
        };

        // Адаптер
        basketAdapter = new BasketAdapter(this, basketItems, deleteClickListener, costClickListener);
        basketList.setLayoutManager(new LinearLayoutManager(this));
        basketList.setAdapter(basketAdapter);

        // ✅ ДОБАВЛЯЕМ SWIPE (СВАЙП) ФУНКЦИОНАЛ
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(basketList);

        updateSum();
    }

    // ✅ ВОТ ЭТОТ КОД БЫЛ ПОТЕРЯН - ДОБАВЬТЕ ЕГО СЮДА
    ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            int position = viewHolder.getAdapterPosition();

            if (swipeDir == ItemTouchHelper.LEFT) {
                // Свайп влево - удаляем элемент
                if (position >= 0 && position < MainActivity.init.BasketList.size()) {
                    MainActivity.init.BasketList.remove(position);
                    basketAdapter.notifyItemRemoved(position);
                    basketAdapter.notifyItemRangeChanged(position, MainActivity.init.BasketList.size());
                }
            } else if (swipeDir == ItemTouchHelper.RIGHT) {
                // Свайп вправо - увеличиваем количество
                if (position >= 0 && position < MainActivity.init.BasketList.size()) {
                    Basket item = MainActivity.init.BasketList.get(position);
                    item.Count++;
                    basketAdapter.notifyItemChanged(position);
                }
            }

            updateSum();
        }

        @Override
        public void onChildDraw(
                Canvas c,
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                float dX,
                float dY,
                int actionState,
                boolean isCurrentlyActive) {

            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, r.getDisplayMetrics());

            LinearLayout bthDelete = viewHolder.itemView.findViewById(R.id.ll_delete);
            LinearLayout bthCount = viewHolder.itemView.findViewById(R.id.ll_count);

            if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                if(dX < -px) {
                    if (bthDelete != null) bthDelete.setVisibility(View.VISIBLE);
                    if (bthCount != null) bthCount.setVisibility(View.GONE);
                } else if(dX > px) {
                    if (bthDelete != null) bthDelete.setVisibility(View.GONE);
                    if (bthCount != null) bthCount.setVisibility(View.VISIBLE);
                } else {
                    // Сбрасываем видимость при малом смещении
                    if (bthDelete != null) bthDelete.setVisibility(View.GONE);
                    if (bthCount != null) bthCount.setVisibility(View.GONE);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            LinearLayout bthDelete = viewHolder.itemView.findViewById(R.id.ll_delete);
            LinearLayout bthCount = viewHolder.itemView.findViewById(R.id.ll_count);
            if (bthDelete != null) bthDelete.setVisibility(View.GONE);
            if (bthCount != null) bthCount.setVisibility(View.GONE);
        }
    };

    private void updateSum() {
        double sum = 0;
        // Считаем сумму по актуальному списку
        for (Basket basket : MainActivity.init.BasketList) {
            sum += basket.Item.Price * basket.Count;
        }

        double delivery = 60.20;
        double allSum = sum + delivery;

        tvSum.setText(String.format("%.2f ₽", sum));
        tvAllSum.setText(String.format("%.2f ₽", allSum));
    }

    public void OnClosePopularActivity(View view) {
        finish();
    }
}