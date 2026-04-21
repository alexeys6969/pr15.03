package com.example.recycler_view_shashin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public Context Context;
    public static MainActivity init;
    public ArrayList<Basket> BasketList = new ArrayList<>();
    public ArrayList<Item> Items;

    // Обработчик добавления в корзину
    public iOnClickInterface AddBasket = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            Item selectedItem = Items.get(position);  // ✅ Теперь position правильная!

            // Ищем существующий товар в корзине
            Basket existingItem = null;
            for (Basket basket : BasketList) {
                if (basket.Item.Id == selectedItem.Id) {
                    existingItem = basket;
                    break;
                }
            }

            if (existingItem != null) {
                existingItem.Count++;
                Toast.makeText(Context, "Добавлена ещё одна единица: " + selectedItem.Name + " " + selectedItem.Model, Toast.LENGTH_SHORT).show();
            } else {
                Basket newBasketItem = new Basket(selectedItem, 1);
                BasketList.add(newBasketItem);
                Toast.makeText(Context, "Добавлен новый товар: " + selectedItem.Name + " " + selectedItem.Model, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Context = this;
        init = this; // Статическая ссылка для доступа из других классов

        // Загружаем данные
        ArrayList<Category> Categorys = CategoryContext.All();
        Items = ItemContext.All();

        // Настройка категорий
        RecyclerView CategoryList = findViewById(R.id.category_list);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, Categorys, Click);
        CategoryList.setAdapter(categoryAdapter);

        // Настройка товаров
        RecyclerView CardList = findViewById(R.id.card_list);
        ItemAdapter cardAdapter = new ItemAdapter(this, Items, AddBasket);
        CardList.setAdapter(cardAdapter);

        // Меню навигации
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MenuNavigation fragment = new MenuNavigation();
        ft.add(R.id.menu_navigation, fragment);
        ft.commit();
    }

    public void OpenPopularView(View view) {
        Intent newIntent = new Intent(this, PopularActivity.class);
        newIntent.putExtra("Category", -1);
        startActivity(newIntent);
    }

    iOnClickInterface Click = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            Intent newIntent = new Intent(Context, PopularActivity.class);
            newIntent.putExtra("Category", position);
            startActivity(newIntent);
        }
    };

    public void OpenBasketView(View view) {
        Intent newIntent = new Intent(this, BasketActivity.class);
        startActivity(newIntent);
    }
}