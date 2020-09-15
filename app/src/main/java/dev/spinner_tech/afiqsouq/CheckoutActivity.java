package dev.spinner_tech.afiqsouq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import meow.bottomnavigation.MeowBottomNavigation;

public class CheckoutActivity extends AppCompatActivity {

    private ImageButton backIB,addToCartIB,shippingExpandIB,deleteIB,editIB,select_payment_methode_expandIB;
    private ImageView activeStatusIV;
    private TextView cartItemCountTV,nameTV,addressTV,phoneTV;
    private Button addNewAddressBtn,placeOrderBtn;
    private RelativeLayout bankTransferRL,cash_on_deliveryRL,paypalRL,masterCardRL;
    private MeowBottomNavigation bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        //image Button
        backIB=findViewById(R.id.backIB);
        addToCartIB=findViewById(R.id.addTocartIB);
        shippingExpandIB=findViewById(R.id.expand_icon_IB);
        deleteIB=findViewById(R.id.delete_IB);
        editIB=findViewById(R.id.edit_IB);
        select_payment_methode_expandIB=findViewById(R.id.select_payment_methode_expandIB);

        //textview
        cartItemCountTV=findViewById(R.id.countCartItemTV);
        nameTV=findViewById(R.id.name_TV);
        addressTV=findViewById(R.id.address_TV);
        phoneTV=findViewById(R.id.phone_number_tv_id);

        //imageView
        activeStatusIV=findViewById(R.id.active_status_IV);

       //button
        addNewAddressBtn=findViewById(R.id.add_a_new_address_Btn);
        placeOrderBtn=findViewById(R.id.place_order_id);

        //realative Layout
        bankTransferRL=findViewById(R.id.bankTransferRL);
        cash_on_deliveryRL=findViewById(R.id.cash_on_delivery_RL);
        paypalRL=findViewById(R.id.paypalRL);
        masterCardRL=findViewById(R.id.master_card_RL);

        //bottomNavigation
        bottomNavigationView=findViewById(R.id.bottomNavigation);


    }
}