package dev.mobile.afiqsouq.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import dev.mobile.afiqsouq.database.CartDatabase;

@Entity(tableName = CartDatabase.TABLE_NAME_RECENT)
public class recentDbModel {

    @PrimaryKey(autoGenerate = true) public int id;

    public Integer product_recent_id;
    public String title ;
    public String image ;
    public String price ;


}
