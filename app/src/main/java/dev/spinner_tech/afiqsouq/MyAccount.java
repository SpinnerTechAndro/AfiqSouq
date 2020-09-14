package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.mikhaellopez.circularimageview.CircularImageView;

public class MyAccount extends AppCompatActivity {


    //bottom navigation
    private final static int ID_HOME = 1;
    private final static int ID_FAVOURITE = 2;
    private final static int ID_MIDDLE = 3;
    private final static int ID_SEARCH = 4;
    private final static int ID_ACCOUNT = 5;


    //toobar & content
    TextView cartnumber,name,email,profile,wishlist,order,payment,address,gift,setting,logout;
    ImageView back,cart;
    CircularImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        //Bottom Navigation
        MeowBottomNavigation bottomNavigation = findViewById(R.id.bottomNavigation_filter);

        bottomNavigation.add(new MeowBottomNavigation.Model(ID_HOME, R.drawable.home));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_FAVOURITE, R.drawable.heart_icon_outline));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_MIDDLE, R.drawable.ic_outline_widgets_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_SEARCH, R.drawable.ic_baseline_search_24));
        bottomNavigation.add(new MeowBottomNavigation.Model(ID_ACCOUNT, R.drawable.user));


        //toolbar
        back=findViewById(R.id.imageview_myacc_back);
        cart=findViewById(R.id.imageView_myacc_cart);
        profileImage=findViewById(R.id.circularImageView_myacc_profile_image);

        //content
        name=findViewById(R.id.textview_myacc_name);
        email=findViewById(R.id.textview_myacc_email);
        profile=findViewById(R.id.textview_myacc_myProfile);
        wishlist=findViewById(R.id.textview_myacc_wishlist);
        order=findViewById(R.id.textview_myacc_myOrder);
        payment=findViewById(R.id.textview_myacc_payment);
        address=findViewById(R.id.textview_myacc_address);
        gift=findViewById(R.id.textview_myacc_giftcard);
        setting=findViewById(R.id.textview_myacc_setting);
        logout=findViewById(R.id.textview_myacc_logout);

    }
}