package com.b2.myapplication.model;

import java.util.List;

/**
 * Created by Rizwan Ahmed on 08/10/2020.
 */
public class ProductMainModel {

    public List<ProductModel> productModels;

    public List<ProductModel> getProductModels() {
        return productModels;
    }

    public void setProductModels(List<ProductModel> productModels) {
        this.productModels = productModels;
    }
}
