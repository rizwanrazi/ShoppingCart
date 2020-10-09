package com.b2.myapplication.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.b2.myapplication.R;
import com.b2.myapplication.model.CartModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Rizwan Ahmed on 09/10/2020.
 */

public class OrderConfirmAdapter extends RecyclerView.Adapter<OrderConfirmAdapter.CategoryViewHolder> {

    Context mCtx;
    List<CartModel> productList;


    public OrderConfirmAdapter(Context mCtx, List<CartModel> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_order_confirm, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        CartModel product = productList.get(position);
        holder.tv_name.setText(product.getTitle());
        holder.tv_price.setText(Html.fromHtml("<small>" + "$  " + "</small>" + product.getPrice()));
        holder.tv_counter.setText("QTY " + product.getQtybuy() + "");
        Glide.with(mCtx).load(product.getImage()).placeholder(R.drawable.empty_img).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView tv_name, tv_price, tv_counter;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_counter = itemView.findViewById(R.id.tv_counter);

        }
    }


}
