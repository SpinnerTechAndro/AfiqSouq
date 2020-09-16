package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class ProductsDetails extends AppCompatActivity {

    //bottom navigation
    private final static int ID_HOME = 1;
    private final static int ID_FAVOURITE = 2;
    private final static int ID_MIDDLE = 3;
    private final static int ID_SEARCH = 4;
    private final static int ID_ACCOUNT = 5;

    //toolbar & content
    ImageView back,enlarge,share,favourite,image1,image2,image3;
    TextView cart_number,status,price,S,M,XL,XXL;
    Button additional_info,review,about_brand,add_to_cart;
    ElegantNumberButton quantity;
    RelativeLayout dashboard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_details);

        //Bottom Navigation
        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation_filter);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_FAVOURITE, R.drawable.heart_icon_outline));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MIDDLE, R.drawable.ic_outline_widgets_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SEARCH, R.drawable.ic_baseline_search_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.user));

        //toolbar
        back = findViewById(R.id.imageview_productDetail_back);
        cart_number = findViewById(R.id.textview_productDetail_cartNumber);

        //dashboard
        dashboard = findViewById(R.id.Relativelayout_productdetail_dashboard);
        enlarge = findViewById(R.id.Imageview_productdetail_enlarge);
        share = findViewById(R.id.Imageview_productdetail_share);
        favourite = findViewById(R.id.Imageview_productdetail_favourite);
        image1 = findViewById(R.id.Imageview_productdetail_image1);
        image2 = findViewById(R.id.Imageview_productdetail_image2);
        image3 = findViewById(R.id.Imageview_productdetail_image3);

        status = findViewById(R.id.Textview_productDetail_status);
        additional_info = findViewById(R.id.Button_productDetail_additionalInfo);
        review = findViewById(R.id.Button_productDetail_review);
        about_brand = findViewById(R.id.Button_productDetail_aboutBrand);

        price = findViewById(R.id.Textview_productDetail_price);

        //sizes
        S = findViewById(R.id.textview_productDetail_size_S);
        M = findViewById(R.id.textview_productDetail_size_M);
        XL = findViewById(R.id.textview_productDetail_size_XL);
        XXL = findViewById(R.id.textview_productDetail_size_XXL);

        add_to_cart = findViewById(R.id.button_productDetail_addToCart);
    }
}