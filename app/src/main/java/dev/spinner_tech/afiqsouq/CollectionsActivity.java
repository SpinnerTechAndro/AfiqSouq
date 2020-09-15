package dev.spinner_tech.afiqsouq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import meow.bottomnavigation.MeowBottomNavigation;

public class CollectionsActivity extends AppCompatActivity {

    private ImageButton backBtn,cartIB;
    private TextView countCartItemTV,viewAllFeatrueTV,viewAllNewArrivalTV;
    private RecyclerView averageProductRV,featuredRV,newArrivalsRV;
    MeowBottomNavigation bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collections);

        //ImageButton
        backBtn=findViewById(R.id.backIB);
        cartIB=findViewById(R.id.addTocartIB);

        //textview
        viewAllFeatrueTV=findViewById(R.id.viewall_featured_textview);
        countCartItemTV=findViewById(R.id.countCartItemTV);
        viewAllNewArrivalTV=findViewById(R.id.viewall_newarrival_textview);

        //recyclerview
        averageProductRV=findViewById(R.id.average_product_RV);
        featuredRV=findViewById(R.id.featured_RV);
        newArrivalsRV=findViewById(R.id.new_arrivals_RV);

        //navigationBottom..
        bottomNavigationView=findViewById(R.id.bottomNavigation);

    }
}