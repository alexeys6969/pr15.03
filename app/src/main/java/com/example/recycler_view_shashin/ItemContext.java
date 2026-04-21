package com.example.recycler_view_shashin;

import java.util.ArrayList;

public class ItemContext {
    public static ArrayList<Item> All() {
        ArrayList<Item> Items = new ArrayList<>();

        Items.add(new Item(1, "STREETBEAT", "Universum", 11499, 0));
        Items.add(new Item(2, "STREETBEAT", "Jogo", 9999, 0));
        Items.add(new Item(3, "PUMA", "Suede XL", 13999, 0));
        Items.add(new Item(4, "STREETBEAT", "Solid Mid Concrete Pack", 10999, 1));
        Items.add(new Item(5, "New Balance", "574", 18999, 1));
        Items.add(new Item(6, "New Balance", "2002", 24999, 1));
        Items.add(new Item(7, "Nike", "P-6000", 18999, 2));
        Items.add(new Item(8, "PUMA", "Morphic Base", 14999, 2));
        Items.add(new Item(9, "Nike", "Air Max 97", 27999, 2));
        Items.add(new Item(10, "Nike", "Air Force 1", 21999, 3));
        Items.add(new Item(11, "Hiker", "Sound Freelock", 9499, 3));
        Items.add(new Item(12, "PUMA", "CA Pro Classic II", 14999, 3));
        Items.add(new Item(13, "Nike", "KD17", 27999, 3));
        Items.add(new Item(14, "Nike", "Air Flight 89 Low", 22499, 3));

        return Items;
    }
    public static ArrayList<Item> GetByCategory(Integer idCategory) {
        if(idCategory == 0) return All();
        ArrayList<Item> Items = new ArrayList<>();
        for(Item item : All())
            if(item.IdCategory.equals(idCategory))
                Items.add(item);
        return Items;
    }
}
