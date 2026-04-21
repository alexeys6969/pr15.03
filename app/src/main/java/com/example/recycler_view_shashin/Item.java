package com.example.recycler_view_shashin;

public class Item {
    public int Id;
    public String Name;
    public String Model;
    public Integer Price;
    public Integer IdCategory;
    public Item(int id, String name, String model, Integer price, Integer idCategory) {
        this.Id = id;
        this.Name = name;
        this.Model = model;
        this.Price = price;
        this.IdCategory = idCategory;
    }
}
