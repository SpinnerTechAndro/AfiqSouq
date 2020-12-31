package dev.mobile.afiqsouq.Models;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import dev.mobile.afiqsouq.database.CartDatabase;


@Entity(tableName = CartDatabase.TABLE_NAME_CART)
public class CartDbModel {


    @PrimaryKey(autoGenerate = true)
    public int cart_id;

    public String title;
    public Double unit_price;
    public Double sub_total;
    public Integer stock_qty ;
    public Integer qty;
    public Integer product_id;
    public Integer customer_id;
    public String product_image;
    public String color;
    public String size;
    public Integer cat_id ;
    public int variation_id ;


    @Ignore
    public String priority;


    /*
        this.title = title;
        this.unit_price = unit_price;
        this.sub_total = sub_total;
        this.qty = qty;
        this.product_id = product_id;
        this.shop_id = shop_id;
        this.customer_id = customer_id;
        this.order_id = order_id;
        this.product_image = product_image;
        this.invoice_no = invoice_no;
        this.color = color;
        this.size = size;
     */


}
