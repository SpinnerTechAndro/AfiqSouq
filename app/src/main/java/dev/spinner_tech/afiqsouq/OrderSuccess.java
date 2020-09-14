package dev.spinner_tech.afiqsouq;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderSuccess extends AppCompatActivity {

    ImageView cancel;
    Button backToShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);

        //cross the layout
        cancel=findViewById(R.id.imageview_ordersuccess_cancel);

        //back to shop button
        backToShop=findViewById(R.id.button_ordersuccess_backToShop);
    }
}