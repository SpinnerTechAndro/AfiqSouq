package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;

public class Home extends AppCompatActivity {

    //bottom navigation
    private final static int ID_HOME = 1;
    private final static int ID_FAVOURITE = 2;
    private final static int ID_MIDDLE = 3;
    private final static int ID_SEARCH = 4;
    private final static int ID_ACCOUNT = 5;

    //toolbar & content
    ImageView toolbar_left,cart;
    TextView cartNumber,off_quantity,feature_viewall,newArrival_viewall;
    EditText search;
    LinearLayout dashboard;
    RecyclerView feature,new_arrival;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Bottom Navigation
        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation_filter);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_FAVOURITE, R.drawable.heart_icon_outline));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MIDDLE, R.drawable.ic_outline_widgets_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SEARCH, R.drawable.ic_baseline_search_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.user));

        //toolbar
        toolbar_left = findViewById(R.id.imageview_discover_toolbar_left);
        cart=findViewById(R.id.imageView_discover_cart);
        cartNumber=findViewById(R.id.textview_discover_cartNumber);

        //search item
        search=findViewById(R.id.edittext_discover_search);

        //Dashboard & offer for new customer
        dashboard= findViewById(R.id.linearlayout_dashboard);
        off_quantity=findViewById(R.id.textview_discover_off_quantity);

        //Feature & New Arrival
        feature_viewall=findViewById(R.id.textview_viewall_featured);
        newArrival_viewall=findViewById(R.id.textview_viewall_newarrival);

        //Recyclerview for feature & new arrival
        feature=findViewById(R.id.recyclerview_featured);
        new_arrival=findViewById(R.id.recyclerview_new_arrival);

    }
}