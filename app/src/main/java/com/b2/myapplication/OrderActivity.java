package com.b2.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.b2.myapplication.database.DBHelper;
import com.b2.myapplication.helper.Constants;
import com.b2.myapplication.helper.Utils;
import com.b2.myapplication.model.order.Order;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Rizwan Ahmed on 09/10/2020.
 */

public class OrderActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_total_price)
    TextView tv_total_price;

    @BindView(R.id.tv_subtotal)
    TextView tv_subtotal;

    @BindView(R.id.tv_shipping)
    TextView tv_shipping;

    @BindView(R.id.tv_cod)
    TextView tv_cod;

    @BindView(R.id.btn_payNow)
    Button btn_payNow;

    Order order;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);


        toolbar.setNavigationIcon(R.drawable.chevron_right);
        toolbar.setTitle("Order");
        setSupportActionBar(toolbar);

        String object = getIntent().getStringExtra(Constants.KEY_ORDER_);
        order = new Gson().fromJson(object, Order.class);

        initialize();
    }


    public void initialize() {
        order.setCod_charges(4);
        tv_cod.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) order.getCod_charges()));
        tv_shipping.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) (order.getShipping())));
        tv_subtotal.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) order.getSubtotal()));
        tv_total_price.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((float) getTotal()));
        btn_payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                order_completed();
            }
        });
    }

    public double getTotal() {
        return ((float) (order.getTotal() + order.getCod_charges()));
    }

    public void order_completed() {
        Toast.makeText(OrderActivity.this, "Order Placed Successfully", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, OrderConfirmationActivity.class);
        new DBHelper().deleteFullCartInDB();
        intent.putExtra(Constants.KEY_ORDER_, new Gson().toJson(order));
        startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                OrderActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
