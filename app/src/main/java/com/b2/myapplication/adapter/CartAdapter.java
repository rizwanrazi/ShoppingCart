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

import com.b2.myapplication.CartNotifier;
import com.b2.myapplication.R;
import com.b2.myapplication.database.DBHelper;
import com.b2.myapplication.model.CartModel;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Rizwan Ahmed on 09/10/2020.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CategoryViewHolder> {

    Context mCtx;
    List<CartModel> cartModelList;
    CartNotifier notifier;

    public CartAdapter(Context mCtx, List<CartModel> cartModelList, CartNotifier notifier) {
        this.mCtx = mCtx;
        this.cartModelList = cartModelList;
        this.notifier = notifier;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.item_cart, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        CartModel cartModel = cartModelList.get(position);
        holder.tv_name.setText(cartModel.getTitle());
        holder.tv_sale_price.setTextSize(13);
        holder.tv_sale_price.setText(Html.fromHtml("<small>" + mCtx.getResources().getString(R.string.dollar_sign)  + "</small>" + cartModel.getPrice()));
        holder.tv_counter.setText(cartModel.getQtybuy() + "");
        Glide.with(mCtx).load(cartModel.getImage()).placeholder(R.drawable.empty_img).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView, img_minus, img_plus, img_cross;
        TextView tv_name, tv_counter, tv_sale_price;

        public CategoryViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            img_minus = itemView.findViewById(R.id.img_minus);
            img_plus = itemView.findViewById(R.id.img_plus);
            img_cross = itemView.findViewById(R.id.img_cross);
            tv_sale_price = itemView.findViewById(R.id.tv_sale_price);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_counter = itemView.findViewById(R.id.tv_counter);

            img_plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int current = Integer.parseInt(tv_counter.getText().toString());
                    int updatedCurrent = increase(current, cartModelList.get(getLayoutPosition()).getQtybuy());
                    cartModelList.get(getLayoutPosition()).setQtybuy(updatedCurrent);
                    new DBHelper().saveCartInDB(cartModelList.get(getLayoutPosition()));
                    tv_counter.setText(updatedCurrent + "");
                    notifier.updateQty(updatedCurrent, getLayoutPosition());
                }
            });

            img_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int current = Integer.parseInt(tv_counter.getText().toString());
                    int updatedCurrent = decrease(current);
                    cartModelList.get(getLayoutPosition()).setQtybuy(updatedCurrent);
                    new DBHelper().saveCartInDB(cartModelList.get(getLayoutPosition()));
                    tv_counter.setText(updatedCurrent + "");
                    notifier.updateQty(updatedCurrent, getLayoutPosition());
                }
            });

            img_cross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifier.updateCart(getLayoutPosition());
                }
            });


        }
    }

    private int increase(int current, int total_qty) {
        current++;
        return Math.min(current, 25);
    }

    private int decrease(int current) {
        current--;
        if (current == 0)
            return 1;
        else
            return current;
    }
}
