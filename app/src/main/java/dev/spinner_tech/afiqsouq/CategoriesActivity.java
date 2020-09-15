package dev.spinner_tech.afiqsouq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import meow.bottomnavigation.MeowBottomNavigation;

public class CategoriesActivity extends AppCompatActivity {

    private ImageButton backIB,cartIB;
    private TextView cartCountTV;
    private RecyclerView categoriesRV;

    private MeowBottomNavigation bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        //image button
        backIB=findViewById(R.id.backIB);
        cartIB=findViewById(R.id.addTocartIB);

        //textview
        cartCountTV=findViewById(R.id.countCartItemTV);

        //recyclerveiw
        categoriesRV=findViewById(R.id.categories_RV);

        //bottomNavigation
        bottomNavigationView=findViewById(R.id.bottomNavigation);
    }
}