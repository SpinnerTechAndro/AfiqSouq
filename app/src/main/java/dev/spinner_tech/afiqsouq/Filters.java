package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.slider.RangeSlider;

public class Filters extends AppCompatActivity {

    //Bottom Navigation
    private final static int ID_HOME = 1;
    private final static int ID_FAVOURITE = 2;
    private final static int ID_MIDDLE = 3;
    private final static int ID_SEARCH = 4;
    private final static int ID_ACCOUNT = 5;

    //toolbar & content
    ImageView back,price_arrow,colors_arrow,sortby_arrow,size_arrow,brand_arrow;
    TextView cart_number,men,women,kids,shoes,accessories,tv_price_range,sortBy,size,brand;
    Button apply_filter,clear_all;
    RangeSlider rs_price_range;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filters);

        //Bottom Navigation
        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation_filter);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_FAVOURITE, R.drawable.heart_icon_outline));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MIDDLE, R.drawable.ic_outline_widgets_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SEARCH, R.drawable.ic_baseline_search_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.user));

        //toolbar
        back = findViewById(R.id.imageview_filter_back);
        cart_number = findViewById(R.id.textView_filter_cartNumber);

        //content

        //category
        men = findViewById(R.id.textView_filter_men);
        women = findViewById(R.id.textView_filter_women);
        kids = findViewById(R.id.textView_filter_kids);
        shoes = findViewById(R.id.textView_filter_shoes);
        accessories = findViewById(R.id.textView_filter_accessories);

        //price
        rs_price_range = findViewById(R.id.rangeseekbar_price);
        tv_price_range = findViewById(R.id.textview_filter_price_range);
        price_arrow = findViewById(R.id.imageview_filter_price_arrow);

        //sortby
        sortBy = findViewById(R.id.textview_filter_sortby);
        sortby_arrow = findViewById(R.id.imageview_filter_sortBy_arrow);

        //size
        size = findViewById(R.id.textview_filter_size);
        size_arrow = findViewById(R.id.imageview_filter_size_arrow);

        //brand
        brand = findViewById(R.id.textview_filter_brand);
        brand_arrow = findViewById(R.id.imageview_filter_brand_arrow);

        //button
        apply_filter = findViewById(R.id.button_filter_applyfilter);
        clear_all = findViewById(R.id.button_filter_clear_all);

    }
}