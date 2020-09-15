package dev.spinner_tech.afiqsouq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import meow.bottomnavigation.MeowBottomNavigation;

public class SettingsActivity extends AppCompatActivity {


    private ImageButton backIB,addToCartIB;
    private TextView cartItemCountTV,generalTV,privacyAndSecurityTV,paymentTV,notificationTV,logOutTV;

    private MeowBottomNavigation bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //imageButton
        backIB=findViewById(R.id.backIB);
        addToCartIB=findViewById(R.id.addTocartIB);

        //textView
        cartItemCountTV=findViewById(R.id.countCartItemTV);
        generalTV=findViewById(R.id.generalTV);
        privacyAndSecurityTV=findViewById(R.id.privacy_and_security_TV);
        paymentTV=findViewById(R.id.paymentTV);
        notificationTV=findViewById(R.id.notificationTV);
        logOutTV=findViewById(R.id.logOutTV);

        //bottomNavigation
        bottomNavigationView=findViewById(R.id.bottomNavigation);

    }
}