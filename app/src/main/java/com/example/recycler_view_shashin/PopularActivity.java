package com.example.recycler_view_shashin;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class PopularActivity extends AppCompatActivity {
    RecyclerView CategoryList, CardList;
    TextView TvNamePage;
    CategoryAdapter CategoryAdapter;
    ArrayList<Category> Categorys = new ArrayList<>();
    ArrayList<Item> Items = new ArrayList<>();
    public Context Context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        Context = this;
        Categorys = CategoryContext.All();

        Bundle arguments = getIntent().getExtras();

        Integer IdCategory = Integer.valueOf(arguments.get("Category").toString());

        CategoryList = findViewById(R.id.category_list);
        CardList = findViewById(R.id.card_list);
        TvNamePage = findViewById(R.id.tv_name_page);

        if(IdCategory != -1) {
            Category SelectCategory = Categorys.stream()
                    .filter(item -> item.Id == IdCategory)
                    .findAny()
                    .orElse(null);
            SelectCategory.Active = true;
            TvNamePage.setText(SelectCategory.Name);

            CategoryAdapter = new CategoryAdapter(this, Categorys, CategoryClick);
            CategoryList.setAdapter(CategoryAdapter);
        } else {
            CategoryList.setVisibility(View.GONE);

            TextView TvNameCategory = findViewById(R.id.tv_name_category);
            TvNameCategory.setVisibility(View.GONE);
        }

        Items = IdCategory == -1 ? ItemContext.All() : ItemContext.GetByCategory(IdCategory);
        CardList.setLayoutManager(new GridLayoutManager(this, 2));
        ItemAdapter CardAdapter = new ItemAdapter(this, Items);
        CardList.setAdapter(CardAdapter);
    }
    iOnClickInterface CategoryClick = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            for(Category Item : Categorys)
                Item.Active = false;
            Category SelectCategory = Categorys.get(position);
            SelectCategory.Active = true;
            CategoryList.setAdapter(CategoryAdapter);

            TvNamePage.setText(SelectCategory.Name);

            Items = ItemContext.GetByCategory(SelectCategory.Id);
            ItemAdapter CardAdapter = new ItemAdapter(Context, Items);
            CardList.setAdapter(CardAdapter);
        }
    };
    public void OnClosePopularActivity(View view) { finish(); }
}

// !!!sddd