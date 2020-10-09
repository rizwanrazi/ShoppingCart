package com.b2.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.b2.myapplication.database.DBHelper;
import com.b2.myapplication.helper.Constants;
import com.b2.myapplication.helper.Utils;
import com.b2.myapplication.model.CartModel;
import com.b2.myapplication.model.ProductModel;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Rizwan Ahmed on 09/10/2020.
 */
public class DetailActivity extends AppCompatActivity {


    @BindView(R.id.img_minus)
    ImageView img_minus;

    @BindView(R.id.img_plus)
    ImageView img_plus;

    @BindView(R.id.tv_counter)
    TextView tv_counter;

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.btn_addCart)
    Button btn_addCart;

    @BindView(R.id.text_title)
    TextView text_title;

    @BindView(R.id.text_detail)
    TextView text_detail;

    @BindView(R.id.text_price)
    TextView text_price;

    @BindView(R.id.text_calculation)
    TextView text_calculation;

    @BindView(R.id.text_price_calculation)
    TextView text_price_calculation;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ProductModel productModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        toolbar.setNavigationIcon(R.drawable.chevron_right);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //Get Extra
        String object = getIntent().getStringExtra(Constants.KEY_DETAIL_PRODUCT);
        productModel = new Gson().fromJson(object, ProductModel.class);

        initialize();
    }


    public void initialize() {

        text_title.setText(productModel.getTitle());
        text_detail.setText(productModel.getDescription());

        text_price.setText("$" + Utils.roundValue(productModel.getPrice()));
        text_price_calculation.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((0 * productModel.getPrice())));


        Glide.with(this).load(productModel.getImage()).placeholder(R.drawable.empty_img).into(imageView);

        img_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = Integer.valueOf(tv_counter.getText().toString());
                current = current + 1;
                tv_counter.setText(current + "");

                img_plus.setBackground(getResources().getDrawable(R.drawable.counter_circle_red));
                img_plus.setImageResource(R.drawable.plus_white);

                img_minus.setBackground(getResources().getDrawable(R.drawable.counter_circle_red));
                img_minus.setImageResource(R.drawable.ic_minus_white);
                updateCalculation(current);
            }
        });

        img_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = Integer.valueOf(tv_counter.getText().toString());
                if (current > 0) {
                    current = current - 1;
                    tv_counter.setText(current + "");
                    updateCalculation(current);
                }

                if (current == 0) {
                    img_plus.setBackground(getResources().getDrawable(R.drawable.counter_circle));
                    img_plus.setImageResource(R.drawable.plus);

                    img_minus.setBackground(getResources().getDrawable(R.drawable.counter_circle));
                    img_minus.setImageResource(R.drawable.minus);
                } else {

                    img_plus.setBackground(getResources().getDrawable(R.drawable.counter_circle_red));
                    img_plus.setImageResource(R.drawable.plus_white);

                    img_minus.setBackground(getResources().getDrawable(R.drawable.counter_circle_red));
                    img_minus.setImageResource(R.drawable.ic_minus_white);
                }
            }
        });

        btn_addCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current = Integer.valueOf(tv_counter.getText().toString());
                if (current == 0) {
                    Toast.makeText(DetailActivity.this, "Please Select Quantity", Toast.LENGTH_LONG).show();

                } else {
                    updateCart(Integer.valueOf(tv_counter.getText().toString()));
                }
            }
        });

    }

    private void updateCalculation(int qty) {
        text_calculation.setText(qty + " * " + productModel.getPrice());
        text_price_calculation.setText(getResources().getString(R.string.dollar_sign) + Utils.roundValue((qty * productModel.getPrice())));
    }

    private void updateCart(int current_qty) {
        CartModel cartModel = new CartModel(productModel);
        cartModel.setQtybuy(current_qty);
        new DBHelper().saveCartInDB(cartModel);
        Toast.makeText(DetailActivity.this, "Added to cart", Toast.LENGTH_LONG).show();
        invalidateOptionsMenu();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_detail, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        MenuItem cartItem = menu.findItem(R.id.action_cart);
        Utils.setCount(this, cartItem, new DBHelper().getCartFromDB());

        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_cart:
                startActivity(new Intent(this, CartActivity.class));
                return true;
            case android.R.id.home:
                DetailActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

}
