package com.b2.myapplication;

/**
 * Created by Rizwan Ahmed on 09/10/2020.
 */
public interface CartNotifier {
    // you can define any parameter as per your requirement
    public void updateCart(int position);

    public void updateQty(int qtyBuy, int pos);
}
