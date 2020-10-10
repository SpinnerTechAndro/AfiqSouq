package dev.spinner_tech.afiqsouq.View.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import dev.spinner_tech.afiqsouq.MainActivity;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.View.Home_Activity;

public class OrderDone extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_done);

        findViewById(R.id.imageview_ordersuccess_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent p = new Intent(getApplicationContext(), Home_Activity.class);
                startActivity(p);
            }
        });

        findViewById(R.id.button_ordersuccess_backToShop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent p = new Intent(getApplicationContext(), Home_Activity.class);
                startActivity(p);
            }
        });
    }
}