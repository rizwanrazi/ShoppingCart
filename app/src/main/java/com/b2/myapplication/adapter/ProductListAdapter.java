package com.b2.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.b2.myapplication.DetailActivity;
import com.b2.myapplication.R;
import com.b2.myapplication.helper.Constants;
import com.b2.myapplication.model.ProductModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Rizwan Ahmed on 09/10/2020.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.CategoryViewHolder> {

    Context mCtx;
    List<ProductModel> productList;

    public ProductListAdapter(Context mCtx, List<ProductModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;

    }

    @NonNull
    @Override
    public ProductListAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_product, parent, false);
        return new ProductListAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.CategoryViewHolder holder, int position) {

        ProductModel productModel = productList.get(position);

        holder.tv_title.setText(productModel.getTitle());
        holder.tv_sale_price.setText(Html.fromHtml("<small>" + "$  " + "</small>" + productModel.getPrice()));
        Glide.with(mCtx).load(productList.get(position).getImage()).placeholder(R.drawable.empty_img).into(holder.img_product);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView img_product;
        TextView tv_title, tv_sale_price;
        CardView cardView;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            img_product = itemView.findViewById(R.id.img_product);
            tv_title = itemView.findViewById(R.id.tv_title);
            cardView = itemView.findViewById(R.id.cardView);
            tv_sale_price = itemView.findViewById(R.id.tv_sale_price);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ProductModel productModel = productList.get(getLayoutPosition());
                    Gson gson = new Gson();
                    String json = gson.toJson(productModel);
                    Intent intent = new Intent(mCtx, DetailActivity.class);
                    intent.putExtra(Constants.KEY_DETAIL_PRODUCT, json);
                    mCtx.startActivity(intent);
                }
            });
        }
    }
}
