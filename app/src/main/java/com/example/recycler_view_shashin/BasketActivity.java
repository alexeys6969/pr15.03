package com.example.recycler_view_shashin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

        basketList = findViewById(R.id.basket_list);
        tvSum = findViewById(R.id.tv_sum);
        tvAllSum = findViewById(R.id.tv_all_sum);

        ArrayList<Basket> basketItems = MainActivity.init.BasketList;

        iOnClickInterface deleteClickListener = new iOnClickInterface() {
            @Override
            public void setClick(View view, int position) {
                updateSum();
            }
        };

        iOnClickInterface costClickListener = new iOnClickInterface() {
            @Override
            public void setClick(View view, int position) {
                updateSum();
            }
        };

        basketAdapter = new BasketAdapter(this, basketItems, deleteClickListener, costClickListener);
        basketList.setLayoutManager(new LinearLayoutManager(this));
        basketList.setAdapter(basketAdapter);

        updateSum();
    }

    private void updateSum() {
        double sum = 0;

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