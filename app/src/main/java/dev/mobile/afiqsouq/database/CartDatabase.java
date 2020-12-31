package dev.mobile.afiqsouq.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dev.mobile.afiqsouq.Models.CartDbModel;
import dev.mobile.afiqsouq.Models.recentDbModel;


@Database(entities = {CartDbModel.class, recentDbModel.class}, version = 20, exportSchema = false)

public abstract class CartDatabase extends RoomDatabase {


    public static final String DB_NAME = "one_shop_cart_db_2";
    public static final String TABLE_NAME_CART = "cart";
    public static final String TABLE_NAME_RECENT = "recent_table";
    public static final String TABLE_NAME_MY_CHOICE = "recent_table";

    public abstract Dao dao();

    public abstract RecentDao recentDao();

    private static volatile CartDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 2;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static CartDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (CartDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            CartDatabase.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
