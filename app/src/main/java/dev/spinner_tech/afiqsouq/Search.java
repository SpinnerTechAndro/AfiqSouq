package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Search extends AppCompatActivity {

    //bottom navigation
    private final static int ID_HOME = 1;
    private final static int ID_FAVOURITE = 2;
    private final static int ID_MIDDLE = 3;
    private final static int ID_SEARCH = 4;
    private final static int ID_ACCOUNT = 5;

    //toolbar & content
    ImageView back,cart;
    TextView cart_number,viewall,found_product;
    EditText search;
    RecyclerView search_item;

    //fragment search
    ImageView favourite,cancel,cartfr;
    TextView status,price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Bottom Navigation
        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation_filter);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_FAVOURITE, R.drawable.heart_icon_outline));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MIDDLE, R.drawable.ic_outline_widgets_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SEARCH, R.drawable.ic_baseline_search_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.user));

        //toolbar
        back=findViewById(R.id.imageview_search_back);
        cart=findViewById(R.id.imageView__search_cart);
        cart_number=findViewById(R.id.textview_search_cartNumber);

        //content search
        search=findViewById(R.id.edittext_search_searchitem);
        found_product=findViewById(R.id.textview_search_amount_foundproduct); //amount of searched item
        viewall=findViewById(R.id.textview_search_viewall);   //view all the searched item
        search_item=findViewById(R.id.recyclerView_search_searcheditem);

        //fragment search
        //recycler item
        favourite=findViewById(R.id.imageview_search_favourite);
        status=findViewById(R.id.textview_search_status); //status of item like new/hot
        cancel=findViewById(R.id.imageview_search_cancel);
        price=findViewById(R.id.textview_search_price); //price of the specific searched item
        cartfr=findViewById(R.id.imageview_search_cart_fr);//add to cart the searched item

    }
}