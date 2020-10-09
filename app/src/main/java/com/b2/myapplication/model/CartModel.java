package com.b2.myapplication.model;

import com.b2.myapplication.database.B2DataBase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Rizwan Ahmed on 08/10/2020.
 */
@Table(database = B2DataBase.class)
public class CartModel extends BaseModel {
    @PrimaryKey
    @Column
    public Integer id;
    @Column
    public String title;
    @Column
    public Float price;
    @Column
    public String description;
    @Column
    public String category;
    @Column
    public String image;
    @Column
    public int qtybuy = 0;

    CartModel() {

    }

    public CartModel(ProductModel productModel) {
        this.id = productModel.getId();
        this.title = productModel.getTitle();
        this.qtybuy = productModel.getQtybuy();
        this.price = productModel.getPrice();
        this.description = productModel.getDescription();
        this.category = productModel.getCategory();
        this.image = productModel.getImage();
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

    public int getQtybuy() {
        return qtybuy;
    }

    public void setQtybuy(int qtybuy) {
        this.qtybuy = qtybuy;
    }
}
