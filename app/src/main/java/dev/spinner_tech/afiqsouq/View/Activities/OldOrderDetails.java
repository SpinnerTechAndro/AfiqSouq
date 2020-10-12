package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.CartListAdapter;
import dev.spinner_tech.afiqsouq.Adapter.OldCartListAdapter;
import dev.spinner_tech.afiqsouq.Models.CartDbModel;
import dev.spinner_tech.afiqsouq.Models.oldOrderModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;

public class OldOrderDetails extends AppCompatActivity {
    public TextView cartNumber, tax_fee, coupon_no, sub_total, deliveryChargeTV, total, discount, paid, product_name, product_price;
    Button checkoutout, apply_couppon;
    RecyclerView rv_shoppingCart;
    List<CartDbModel> cartList = new ArrayList<>();
    List<CartDbModel> orderList = new ArrayList<>();
    CartListAdapter adapter;
    double toatalAmount;
    double rate = 0.0;
    String method_title = "Flat rate", method_Id = "flat_rate", deliveryCharge = "50";
    DecimalFormat dec = new DecimalFormat("#0.0");
    int delivery_charge = 50;
    oldOrderModel oldOrderModel = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_order_details);
        rv_shoppingCart = findViewById(R.id.recyclerview_shoppingcart_cartItem); //shows cart item

        // we have the model

        oldOrderModel = (oldOrderModel) getIntent().getSerializableExtra("OLD_MODEL");


        sub_total = findViewById(R.id.textview_shoppingcart_subtotal_amount);
        tax_fee = findViewById(R.id.textview_shoppingcart_shippingfee_amount);
        total = findViewById(R.id.textview_shoppingcart_total_amount);
        discount = findViewById(R.id.textview_shoppingcart_discount_amount);
        paid = findViewById(R.id.textview_shoppingcart_tobe_paid_amount);
        checkoutout = findViewById(R.id.button_shoppingcart_checkoutd);
        deliveryChargeTV = findViewById(R.id.deliveryCharge);

        rv_shoppingCart.setLayoutManager(new LinearLayoutManager(this));

        List<oldOrderModel.LineItem> itemList = oldOrderModel.getLineItems();
        rv_shoppingCart.setAdapter(new OldCartListAdapter(itemList, getApplicationContext()));

        // set up Views
        String subtotalStr = oldOrderModel.getTotal();
        String totalTax = oldOrderModel.getTotalTax();

        int subtotal = (int) (Integer.parseInt(subtotalStr) - (Integer.parseInt(totalTax)));
        sub_total.setText(Constants.BDT_SIGN + " " + subtotal);
        tax_fee.setText(Constants.BDT_SIGN + " " + totalTax);
        deliveryChargeTV.setText(Constants.BDT_SIGN + " " + oldOrderModel.getShippingTotal());
        total.setText(Constants.BDT_SIGN + " " + subtotalStr);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}