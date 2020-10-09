package com.b2.myapplication.network.networkinterface;

import com.b2.myapplication.model.ProductModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
/**
 * Created by Rizwan Ahmed on 08/10/2020.
 */
public interface GetService {

    @GET("products")
    Call<ArrayList<ProductModel>> getAllProducts();
}
