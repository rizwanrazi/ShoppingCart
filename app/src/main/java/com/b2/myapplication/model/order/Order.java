package com.b2.myapplication.model.order;

import com.b2.myapplication.model.CartModel;

import java.util.List;

/**
 * Created by Rizwan Ahmed on 09/10/2020.
 */
public class Order {
    public Integer qty;
    public double total;
    public double subtotal;
    public double shipping;
    public Integer payment;
    public Integer status;
    public double cod_charges;
    public int payment_mode;
    public double discount;
    public String couponId;
    public List<CartModel> products = null;

    public double getCod_charges() {
        return cod_charges;
    }

    public void setCod_charges(double cod_charges) {
        this.cod_charges = cod_charges;
    }

    public int getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(int payment_mode) {
        this.payment_mode = payment_mode;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }


    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }



    public List<CartModel> getProducts() {
        return products;
    }

    public void setProducts(List<CartModel> products) {
        this.products = products;
    }
}
