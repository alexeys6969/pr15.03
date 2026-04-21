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
    public iOnClickInterface AddBasket = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            Basket Item = BasketList.stream()
                    .filter(item -> item.Item.Id == position)
                        .findAny()
                            .orElse(null);
            Item FindItem = Items.stream().filter(item -> item.Id == position)
                    .findAny()
                    .orElse(null);
            if(Item == null) {
                Item = new Basket(FindItem, 1);
                BasketList.add(Item);
            } else {
                Item.Count++;
            }
            Toast.makeText(Context, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Context = this;
        init = this;
        ArrayList<Category> Categorys = CategoryContext.All();
        Items = ItemContext.All();

        RecyclerView CategoryList = findViewById(R.id.category_list);
        RecyclerView CardList = findViewById(R.id.card_list);

        CategoryAdapter CategoryAdapter = new CategoryAdapter(this, Categorys, Click);
        CategoryList.setAdapter(CategoryAdapter);

        ItemAdapter CardAdapter = new ItemAdapter(this, Items, AddBasket);
        CardList.setAdapter(CardAdapter);

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
}