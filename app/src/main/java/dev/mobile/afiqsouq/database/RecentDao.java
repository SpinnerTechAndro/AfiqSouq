package dev.mobile.afiqsouq.database;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dev.mobile.afiqsouq.Models.recentDbModel;


@androidx.room.Dao
public interface RecentDao {

    @Insert
    long insertRecentItem(recentDbModel cart);

    @Query("SELECT * FROM " + CartDatabase.TABLE_NAME_RECENT)
    List<recentDbModel> fetchAllTodos();

    @Query("SELECT * FROM " + CartDatabase.TABLE_NAME_RECENT + " WHERE product_recent_id = :product_ID LIMIT 1 ")
    recentDbModel fetchCartByID(Integer product_ID);


    @Update
    int updateCart(recentDbModel cart);

    @Delete
    int deleteCartItem(recentDbModel cart);
}