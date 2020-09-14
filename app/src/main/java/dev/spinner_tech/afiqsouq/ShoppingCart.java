package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.sax.ElementListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class ShoppingCart extends AppCompatActivity {

    //bottom navigation
    private final static int ID_HOME = 1;
    private final static int ID_FAVOURITE = 2;
    private final static int ID_MIDDLE = 3;
    private final static int ID_SEARCH = 4;
    private final static int ID_ACCOUNT = 5;

    //toolbar & content
    ImageView back,cancel;
    TextView cartNumber,coupon_no,sub_total,shipping_fee,total,discount,paid,product_name,product_price;
    Button checkoutout,apply_couppon;
    RecyclerView rv_shoppingCart;
    CardView product_image;
    ElementListener product_quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        //Bottom Navigation
        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation_filter);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_FAVOURITE, R.drawable.heart_icon_outline));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MIDDLE, R.drawable.ic_outline_widgets_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SEARCH, R.drawable.ic_baseline_search_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.user));


        //toolbar
        back = findViewById(R.id.imageview_shoppingcart_back);
        cartNumber = findViewById(R.id.textview_shoppingcart_cartNumber);

        //content
        //coupon apply
        coupon_no = findViewById(R.id.textview_shoppingcart_couponNo);
        apply_couppon = findViewById(R.id.button_shoppingcart_applyCoupon);

        rv_shoppingCart = findViewById(R.id.recyclerview_shoppingcart_cartItem); //shows cart item

        sub_total = findViewById(R.id.textview_shoppingcart_subtotal_amount);
        shipping_fee = findViewById(R.id.textview_shoppingcart_shippingfee_amount);
        total = findViewById(R.id.textview_shoppingcart_total_amount);
        discount = findViewById(R.id.textview_shoppingcart_discount_amount);
        paid = findViewById(R.id.textview_shoppingcart_tobe_paid_amount);
        checkoutout = findViewById(R.id.button_shoppingcart_checkoutd);

        //fragment shopping cart
        product_name = findViewById(R.id.textView_shoppingcartfr_productName);
        product_image = findViewById(R.id.cardview_shoppcartfr_productImage);
        product_price = findViewById(R.id.textView_shoppingcartfr_price);
        product_quantity = findViewById(R.id.elegantNumberButton_shoppingcartfr_quantity);
        cancel = findViewById(R.id.Imageview_shoppingcartfr_cancel);
    }
}