package com.b2.myapplication.database;

import android.util.Log;

import com.b2.myapplication.model.CartModel;
import com.b2.myapplication.model.CartModel_Table;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Created by Rizwan Ahmed on 08/10/2020.
 */
public class DBHelper {

    //save Cart data
    public void saveCartInDB(CartModel model) {
        FlowManager.getDatabase(B2DataBase.class)
                .beginTransactionAsync(
                        new ProcessModelTransaction.Builder<>(
                                new ProcessModelTransaction.ProcessModel<CartModel>() {
                                    @Override
                                    public void processModel(CartModel cartModel, DatabaseWrapper wrapper) {
                                        cartModel.save();
                                    }

                                }).addAll(model).build())
                .error(new Transaction.Error() {
                    @Override
                    public void onError(Transaction transaction, Throwable error) {
                        Log.d("inserting row error", error.toString());
                    }
                })
                .success(new Transaction.Success() {
                    @Override
                    public void onSuccess(@NotNull Transaction transaction) {
                        Log.d("inserting row success", transaction.success().toString());
                    }
                }).build().execute();


    }

    //Get Cart List
    public List<CartModel> getCartFromDB() {
        return SQLite.select().from(CartModel.class).queryList();
    }

    public boolean deleteCartInDB(int id) {
        return SQLite.delete(CartModel.class)
                .where(CartModel_Table.id.is(id))
                .executeUpdateDelete() != 0;
    }

    public boolean deleteFullCartInDB() {
        return SQLite.delete(CartModel.class)
                .executeUpdateDelete() != 0;
    }


}
