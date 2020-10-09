package com.b2.myapplication.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.MenuItem;

import com.b2.myapplication.R;
import com.b2.myapplication.model.CartModel;

import java.util.List;

/**
 * Created by Rizwan Ahmed on 09/10/2020.
 */
public class Utils {

    public boolean isOnline(Context ctx) {
        ConnectivityManager connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Network[] networks = connectivity.getAllNetworks();
            NetworkInfo networkInfo;
            for (Network mNetwork : networks) {
                networkInfo = connectivity.getNetworkInfo(mNetwork);
                if (networkInfo.getState().equals(NetworkInfo.State.CONNECTED)) {
                    return true;
                }
            }
        } else {
            if (connectivity != null) {
                NetworkInfo[] info = connectivity.getAllNetworkInfo();
                if (info != null) {
                    for (NetworkInfo networkInfo : info) {
                        if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String roundValue(float total) {
        return String.format("%.2f", total);
    }



    // Creating custom count
    public static void setCount(Context context, MenuItem menuItem, List<CartModel> list) {
        int count = 0;
        for (int i = 0; i < list.size(); i++) {
            count = count + list.get(i).getQtybuy();
        }
        // MenuItem menuItem = context.defaultMenu.findItem(R.id.ic_group);
        LayerDrawable icon = (LayerDrawable) menuItem.getIcon();

        CountDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_group_count);
        if (reuse != null && reuse instanceof CountDrawable) {
            badge = (CountDrawable) reuse;
        } else {
            badge = new CountDrawable(context);
        }

        badge.setCount(count + "");
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_group_count, badge);
    }

}
