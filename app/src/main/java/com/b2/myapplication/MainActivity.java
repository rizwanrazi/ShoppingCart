package com.b2.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.b2.myapplication.adapter.ProductListAdapter;
import com.b2.myapplication.helper.Constants;
import com.b2.myapplication.helper.Utils;
import com.b2.myapplication.model.ProductModel;
import com.b2.myapplication.network.RestClient;
import com.b2.myapplication.network.networkinterface.GetService;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Rizwan Ahmed on 08/10/2020.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_product)
    RecyclerView rv_product;
    @BindView(R.id.ll_layout_progressbar)
    LinearLayout progressLayout;
    @BindView(R.id.rl_empty_view)
    RelativeLayout rl_empty_view;
    ProductListAdapter productsListAdapter;
    List<ProductModel> productModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        initialize();
        getProductsList();
    }

    public void initialize() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rv_product.setHasFixedSize(true);
        rv_product.setLayoutManager(layoutManager);
    }


    private void getProductsList() {
        if (new Utils().isOnline(this)) {

            rl_empty_view.setVisibility(View.GONE);
            progressLayout.setVisibility(View.VISIBLE);

            Call<ArrayList<ProductModel>> getProducts;
            getProducts = new RestClient().createService(GetService.class).getAllProducts();
            getProducts.enqueue(new Callback<ArrayList<ProductModel>>() {
                @Override
                public void onResponse(Call<ArrayList<ProductModel>> call, Response<ArrayList<ProductModel>> response) {
                    progressLayout.setVisibility(View.GONE);
                    String erro = response.message();
                    try {
                        if (response.code() == Constants.REQUEST_CODE_TWO_HUNDRED && response.body() != null) {
                            productModelList = response.body();

                            if (productModelList.size() > 0) {
                                rl_empty_view.setVisibility(View.GONE);
                                productsListAdapter = new ProductListAdapter(MainActivity.this, productModelList);
                                rv_product.setAdapter(productsListAdapter);
                                rv_product.setVisibility(View.VISIBLE);
                            } else {
                                rv_product.setVisibility(View.GONE);
                                rl_empty_view.setVisibility(View.VISIBLE);
                            }

                        } else {
                            rv_product.setVisibility(View.GONE);
                            rl_empty_view.setVisibility(View.VISIBLE);
                        }

                    } catch (Exception e) {
                        rv_product.setVisibility(View.GONE);
                        rl_empty_view.setVisibility(View.VISIBLE);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<ProductModel>> call, Throwable t) {
                    rv_product.setVisibility(View.GONE);
                    rl_empty_view.setVisibility(View.VISIBLE);
                    progressLayout.setVisibility(View.GONE);

                    try {

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {

            Snackbar.make(findViewById(android.R.id.content), R.string.check_internet, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.retry, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Respond to the click, such as by undoing the modification that caused
                            // this message to be displayed
                            getProductsList();
                        }
                    }).show();
        }
    }
}