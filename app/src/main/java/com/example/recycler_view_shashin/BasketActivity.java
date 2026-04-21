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
import androidx.recyclerview.widget.LinearLayoutManager; // ✅ Добавлен импорт
import androidx.recyclerview.widget.RecyclerView;

public class BasketActivity extends AppCompatActivity {

    public iOnClickInterface Delete = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            // ✅ Удаляем элемент из списка
            MainActivity.init.BasketList.remove(position);
            // ✅ Уведомляем адаптер об удалении
            BasketAdapter.notifyItemRemoved(position);
            // ✅ Обновляем остальные позиции
            BasketAdapter.notifyItemRangeChanged(position, MainActivity.init.BasketList.size());
            // ✅ Пересчитываем стоимость
            CostCalculation();
        }
    };

    public iOnClickInterface EventCost = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            // ✅ Уведомляем адаптер об изменении данных
            BasketAdapter.notifyItemChanged(position);
            CostCalculation();
        }
    };

    public RecyclerView BasketRV;
    public TextView tvSum, tvAllSum;
    BasketAdapter BasketAdapter;
    Context Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        Context = this;
        BasketRV = findViewById(R.id.basket_list);

        // ✅ Важно: Установите LayoutManager
        BasketRV.setLayoutManager(new LinearLayoutManager(this));

        tvSum = findViewById(R.id.tv_sum);
        tvAllSum = findViewById(R.id.tv_all_sum);

        // ✅ Сначала создаём адаптер
        BasketAdapter = new BasketAdapter(this, MainActivity.init.BasketList, Delete, EventCost);
        BasketRV.setAdapter(BasketAdapter);

        // ✅ Потом привязываем ItemTouchHelper
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(SwipeAdapter);
        itemTouchHelper.attachToRecyclerView(BasketRV);

        CostCalculation();
    }

    ItemTouchHelper.SimpleCallback SwipeAdapter = new ItemTouchHelper.SimpleCallback(0,
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
            // ✅ Получаем позицию свайпнутого элемента
            int position = viewHolder.getAdapterPosition();

            if (swipeDir == ItemTouchHelper.LEFT) {
                // Свайп влево - удаляем элемент
                MainActivity.init.BasketList.remove(position);
                BasketAdapter.notifyItemRemoved(position);
                BasketAdapter.notifyItemRangeChanged(position, MainActivity.init.BasketList.size());
            } else if (swipeDir == ItemTouchHelper.RIGHT) {
                // Свайп вправо - можно добавить свою логику
                // Например, увеличить количество
                Basket item = MainActivity.init.BasketList.get(position);
                item.Count++;
                BasketAdapter.notifyItemChanged(position);
            }

            // ✅ Пересчитываем стоимость
            CostCalculation();
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
                    bthDelete.setVisibility(View.VISIBLE);
                    bthCount.setVisibility(View.GONE);
                } else if(dX > px) {
                    bthDelete.setVisibility(View.GONE);
                    bthCount.setVisibility(View.VISIBLE);
                } else {
                    // ✅ Сбрасываем видимость при малом смещении
                    bthDelete.setVisibility(View.GONE);
                    bthCount.setVisibility(View.GONE);
                }
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }

        // ✅ Добавлен метод для сброса состояния при очистке
        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            LinearLayout bthDelete = viewHolder.itemView.findViewById(R.id.ll_delete);
            LinearLayout bthCount = viewHolder.itemView.findViewById(R.id.ll_count);
            bthDelete.setVisibility(View.GONE);
            bthCount.setVisibility(View.GONE);
        }
    };

    public void CostCalculation() {
        float ItemPrice = 0;
        for(Basket Item: MainActivity.init.BasketList)
            ItemPrice += Item.Item.Price * Item.Count;
        tvSum.setText("₽" + String.format("%.2f", ItemPrice));
        ItemPrice += 60.20;
        tvAllSum.setText("₽" + String.format("%.2f", ItemPrice));
    }

    public void ClosePopularActivity(View view) {
        finish();
    }
}