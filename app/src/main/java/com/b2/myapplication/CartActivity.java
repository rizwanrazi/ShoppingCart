package com.b2.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2.myapplication.adapter.CartAdapter;
import com.b2.myapplication.database.DBHelper;
import com.b2.myapplication.helper.Constants;
import com.b2.myapplication.helper.Utils;
import com.b2.myapplication.model.CartModel;
import com.b2.myapplication.model.order.Order;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rizwan Ahmed on 09/10/2020.
 */
public class CartActivity extends AppCompatActivity implements CartNotifier {

    double shippingPrice = 10.0;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.rv_cart_items)
    RecyclerView rViewCart;

    @BindView(R.id.rl_empty)
    RelativeLayout rl_empty;

    @BindView(R.id.rl_fullCart)
    RelativeLayout rl_fullCart;

    @BindView(R.id.tv_total_price)
    TextView tv_total_price;

    @BindView(R.id.tv_subtotal)
    TextView tv_subtotal;

    @BindView(R.id.tv_shipping)
    TextView tv_shipping;

    @BindView(R.id.textView1)
    TextView textView1;

    @BindView(R.id.textView2)
    TextView textView2;

    @BindView(R.id.textView3)
    TextView textView3;

    @BindView(R.id.btn_addCart)
    Button btn_addCart;

    @BindView(R.id.btn_shop_continue)
    Button btn_shop_continue;

    @BindView(R.id.ll_layout_progressbar)
    LinearLayout progressLayout;

    CartAdapter cartListAdapter;

    ArrayList<String> productsId;

    List<CartModel> cartModelList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.drawable.chevron_right);
        toolbar.setTitle(getString(R.string.title_my_Cart));
        setSupportActionBar(toolbar);

        //Initialize Recyclerview ,LayoutManager
        initialize();

    }

    public void initialize() {
        rViewCart.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.setAutoMeasureEnabled(true);
        rViewCart.setLayoutManager(layoutManager);
        rViewCart.setNestedScrollingEnabled(false);

        cartModelList = new DBHelper().getCartFromDB();
        updateEmptyView();
        cartListAdapter = new CartAdapter(CartActivity.this, cartModelList, this);
        rViewCart.setAdapter(cartListAdapter);
    }

    public void updateEmptyView() {
        if (cartModelList.size() > 0) {
            btn_shop_continue.setVisibility(View.VISIBLE);
            btn_addCart.setText("Proceed to checkout");
            btn_addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callOrderActivity();
                }
            });

            rl_empty.setVisibility(View.GONE);
            rl_fullCart.setVisibility(View.VISIBLE);
            tv_total_price.setVisibility(View.VISIBLE);
            tv_subtotal.setVisibility(View.VISIBLE);
            tv_shipping.setVisibility(View.VISIBLE);
            textView1.setVisibility(View.VISIBLE);
            textView2.setVisibility(View.VISIBLE);
            textView3.setVisibility(View.VISIBLE);

            getSubtotal(cartModelList);
            setPrice(getSubtotal(cartModelList), shippingPrice);

            btn_shop_continue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartActivity.this.finish();
                }
            });

            productsId = new ArrayList<>();
            for (int i = 0; i < cartModelList.size(); i++) {
                productsId.add(cartModelList.get(i).getId() + "");
            }

        } else {

            btn_addCart.setText("Continue shopping");
            btn_shop_continue.setVisibility(View.GONE);

            btn_addCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartActivity.this.finish();

                }
            });
            tv_total_price.setVisibility(View.GONE);
            tv_subtotal.setVisibility(View.GONE);
            tv_shipping.setVisibility(View.GONE);
            textView1.setVisibility(View.GONE);
            textView2.setVisibility(View.GONE);
            textView3.setVisibility(View.GONE);

            rl_empty.setVisibility(View.VISIBLE);

            getSubtotal(cartModelList);
            setPrice(getSubtotal(cartModelList), 0.0);
        }
    }

    public void setPrice(double subTotal, double shipping) {
        tv_shipping.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) shipping));
        tv_subtotal.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) subTotal));
        tv_total_price.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) (shipping + subTotal)));
    }

    public double getSubtotal(List<CartModel> cartModelList) {
        double total = 0.0;
        for (int i = 0; i < cartModelList.size(); i++) {
            total = total + (cartModelList.get(i).getQtybuy() * cartModelList.get(i).getPrice());
        }
        return total;
    }

    public double getTotal(double shipping, double subtotal) {
        return shipping + subtotal;
    }


    public void callOrderActivity() {

        //Setting up order details for pass to order screen
        Order order = new Order();
        order.setShipping(shippingPrice);
        order.setDiscount(0.0);
        order.setPayment_mode(2);
        order.setSubtotal(getSubtotal(cartModelList));
        order.setTotal(getTotal(order.getShipping(), order.getSubtotal()));
        order.setProducts(cartModelList);

        Intent intent = new Intent(this, OrderActivity.class);
        intent.putExtra(Constants.KEY_ORDER_, new Gson().toJson(order));
        startActivity(intent);

    }


    @Override
    public void updateCart(int position) {
        boolean isDeleted = new DBHelper().deleteCartInDB(cartModelList.get(position).getId());
        if (isDeleted) {
            cartModelList.remove(position);
            cartListAdapter.notifyDataSetChanged();
            updateEmptyView();
        }
    }

    @Override
    public void updateQty(int qtyBuy, int pos) {
        cartModelList.get(pos).setQtybuy(qtyBuy);
        getSubtotal(cartModelList);
        setPrice(getSubtotal(cartModelList), shippingPrice);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                CartActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
