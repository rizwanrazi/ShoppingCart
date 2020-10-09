package com.b2.myapplication.model;

/**
 * Created by Rizwan Ahmed on 08/10/2020.
 */
public class ProductModel {
    public Integer id;
    public String title;
    public Float price;
    public String description;
    public String category;
    public String image;
    public int qtyBuy;

    public int getQtybuy() {
        return qtyBuy;
    }

    public void setQtybuy(int qtybuy) {
        this.qtyBuy = qtybuy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
