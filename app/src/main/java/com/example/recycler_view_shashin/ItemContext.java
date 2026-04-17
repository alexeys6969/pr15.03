package com.example.recycler_view_shashin;

import java.util.ArrayList;

public class ItemContext {
    public static ArrayList<Item> All() {
        ArrayList<Item> Items = new ArrayList<>();

        Items.add(new Item("Universum", "STREETBEAT", 11499, 0));
        Items.add(new Item("Jogo", "STREETBEAT", 9999, 0));
        Items.add(new Item("Suede XL", "PUMA", 13999, 0));
        Items.add(new Item("Solid Mid Concrete Pack", "STREETBEAT", 10999, 1));
        Items.add(new Item("574", "New Balance", 18999, 1));
        Items.add(new Item("2002", "New Balance", 24999, 1));
        Items.add(new Item("P-6000", "Nike", 18999, 2));
        Items.add(new Item("Morphic Base", "PUMA", 14999, 2));
        Items.add(new Item("Air Max 97", "Nike", 27999, 2));
        Items.add(new Item("Air Force 1", "Nike", 21999, 3));
        Items.add(new Item("Sound Freelock", "Hiker", 9499, 3));
        Items.add(new Item("CA Pro Classic II", "PUMA", 14999, 3));
        Items.add(new Item("KD17", "Nike", 27999, 3));
        Items.add(new Item("Air Flight 89 Low", "Nike", 22499, 3));

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
