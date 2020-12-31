package dev.mobile.afiqsouq.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.mobile.afiqsouq.Models.CartDbModel;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insertCartList(List<CartDbModel> cartList);
    @Insert
    long insertCartItem(CartDbModel cart);

    @Query("SELECT * FROM " + CartDatabase.TABLE_NAME_CART)
    List<CartDbModel> fetchAllTodos();

    @Query("SELECT * FROM " + CartDatabase.TABLE_NAME_CART + " WHERE product_id = :product_ID LIMIT 1 ")
    CartDbModel fetchCartByID(Integer product_ID);

    @Query("SELECT * FROM " + CartDatabase.TABLE_NAME_CART )
    List<CartDbModel>  fetchCartCountByCatgoryID();


    @Update
    int updateCart(CartDbModel cart);

    @Delete
    int deleteCartItem(CartDbModel cart);

    @Query("DELETE FROM  " + CartDatabase.TABLE_NAME_CART)
    void   nukeCartTable();
}