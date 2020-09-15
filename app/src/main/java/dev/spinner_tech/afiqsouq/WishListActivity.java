package dev.spinner_tech.afiqsouq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import meow.bottomnavigation.MeowBottomNavigation;

public class WishListActivity extends AppCompatActivity {
    private ImageButton backBtn,cartIB;
    private TextView countCartItemTV;

    private RecyclerView wishListRV;
    private MeowBottomNavigation bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);

        //imageButton
        backBtn=findViewById(R.id.backIB);
        cartIB=findViewById(R.id.addTocartIB);

        //textview
        countCartItemTV=findViewById(R.id.countCartItemTV);

        //recyclerview
        wishListRV=findViewById(R.id.wish_list_rv);

        //bottomNavigation
        bottomNavigationView=findViewById(R.id.bottomNavigation);
    }
}