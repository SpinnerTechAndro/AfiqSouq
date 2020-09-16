package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Shop extends AppCompatActivity {

    //bottom navigation
    private final static int ID_HOME = 1;
    private final static int ID_FAVOURITE = 2;
    private final static int ID_MIDDLE = 3;
    private final static int ID_SEARCH = 4;
    private final static int ID_ACCOUNT = 5;

    //toolbar &content
    ImageView back,bar,forwardArrow,cart,favourite;
    TextView cartnumber,price,name,status;
    RatingBar rate_shopitem;
    EditText search;
    RecyclerView reecycler_searchItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //Bottom Navigation
        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation_filter);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_FAVOURITE, R.drawable.heart_icon_outline));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MIDDLE, R.drawable.ic_outline_widgets_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SEARCH, R.drawable.ic_baseline_search_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.user));

        //content
        search=findViewById(R.id.edittext_shop_search);
        reecycler_searchItem=findViewById(R.id.recyclerview_shop_searchItem);

        //toolbar
        back=findViewById(R.id.imageview_shop_back);
        bar=findViewById(R.id.imageView_shop_barIcon);
        cartnumber=findViewById(R.id.textview_shop_cartNumber);

        //fragment shop
        favourite=findViewById(R.id.imageview_shopfr_favourite);
        status=findViewById(R.id.textview_shopfr_status); //status of shop item,for example new/hot
        forwardArrow=findViewById(R.id.imageview_shopfr_arrow); //go to next page by clicking
        price=findViewById(R.id.textview_shopfr_price);
        rate_shopitem=findViewById(R.id.ratingbar_shopfr_rating);


    }
}