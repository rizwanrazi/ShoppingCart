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
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.b2.myapplication.adapter.OrderConfirmAdapter;
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
public class OrderConfirmationActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler)
    RecyclerView recycler;

    @BindView(R.id.btn_shop_continue)
    Button btn_shop_continue;


    @BindView(R.id.ll_layout_progressbar)
    LinearLayout progressLayout;

    @BindView(R.id.rl_empty_view)
    RelativeLayout rl_empty_view;


    @BindView(R.id.tv_Address)
    TextView tv_Address;


    @BindView(R.id.tv_order_number)
    TextView tv_order_number;

    @BindView(R.id.tv_order_date)
    TextView tv_order_date;


    @BindView(R.id.tv_total_price)
    TextView tv_total_price;
    @BindView(R.id.tv_subtotal)
    TextView tv_subtotal;
    @BindView(R.id.tv_shipping)
    TextView tv_shipping;
    @BindView(R.id.rl_0)
    RelativeLayout rl_0;


    @BindView(R.id.tv_cod)
    TextView tv_cod;

    @BindView(R.id.tv_tracking_number)
    TextView tv_tracking_number;
    @BindView(R.id.tv_deliver_Date)
    TextView tv_deliver_Date;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_payment_method)
    TextView tv_payment_method;


    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;


    OrderConfirmAdapter orderConfirmAdapter;

    List<CartModel> productList;


    Order order;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_order_confirmation);
        ButterKnife.bind(this);

        String object = getIntent().getStringExtra(Constants.KEY_ORDER_);
        order = new Gson().fromJson(object, Order.class);

        toolbar.setNavigationIcon(R.drawable.ic_action_clear);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        initialize();
    }

    public void initialize() {

        productList = new ArrayList<>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

        setValues(order);

        btn_shop_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToMainActivity();
            }
        });
    }

    private void setValues(Order orderDetailModel) {

        productList = new ArrayList<>();
        productList = orderDetailModel.getProducts();
        if (productList.size() > 0) {
            orderConfirmAdapter = new OrderConfirmAdapter(OrderConfirmationActivity.this, productList);
            recycler.setAdapter(orderConfirmAdapter);
            //   SharedPreferenceManager.getSharedInstance().saveCategoryListModel(categoryModel.getPayloadModel());
            rl_empty_view.setVisibility(View.GONE);
            recycler.setVisibility(View.VISIBLE);
        } else {
            rl_empty_view.setVisibility(View.VISIBLE);
            recycler.setVisibility(View.INVISIBLE);
        }
        tv_order_date.setText("Placed on 26-08-2020");

        tv_Address.setText("34 b" + ", " + "Marina, Dubai, UAE");

        tv_order_number.setText("ORDER# " + (Math.random() * 49 + 1));
        tv_shipping.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) orderDetailModel.getShipping()));
        tv_total_price.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) ((orderDetailModel.getTotal() + (orderDetailModel.getCod_charges()) - orderDetailModel.getDiscount()))));
        tv_subtotal.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) orderDetailModel.getSubtotal()));

        tv_deliver_Date.setText("Schedule delivery : 27-08-2020");
        if (orderDetailModel.getPayment_mode() == 2) {
            rl_0.setVisibility(View.VISIBLE);
            tv_cod.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) orderDetailModel.getCod_charges()));
        } else {
            rl_0.setVisibility(View.GONE);
        }

    }


    public void moveToMainActivity() {
        Intent i = new Intent(OrderConfirmationActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        OrderConfirmationActivity.this.finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                moveToMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        moveToMainActivity();

    }
}
