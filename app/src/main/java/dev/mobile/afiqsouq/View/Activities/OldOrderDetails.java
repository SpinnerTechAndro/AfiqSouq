package dev.mobile.afiqsouq.View.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dev.mobile.afiqsouq.Adapter.CartListAdapter;
import dev.mobile.afiqsouq.Adapter.OldCartListAdapter;
import dev.mobile.afiqsouq.Models.CartDbModel;
import dev.mobile.afiqsouq.Models.UpdateOrderModel;
import dev.mobile.afiqsouq.Models.oldOrderModel;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    MaterialButton cancelOrder;
    AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_order_details);
        rv_shoppingCart = findViewById(R.id.recyclerview_shoppingcart_cartItem); //shows cart item
        cancelOrder = findViewById(R.id.button_cancel);


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


        if(oldOrderModel.getStatus().equals("cancelled")){
                cancelOrder.setVisibility(View.INVISIBLE);
        }
        else {
            cancelOrder.setVisibility(View.VISIBLE);
        }
        cancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TriggerDilogue();
            }
        });
    }

    private void TriggerDilogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You Are about To Cancel This Order !!!")
                .setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                })
                .setPositiveButton("Cancel Order", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        try {
                            alert.dismiss();
                            proceedToCancelOrder();

                        } catch (Exception r) {

                        }
                    }
                });
        alert = builder.create();
        alert.show();
    }

    private void proceedToCancelOrder() {
        ProgressDialog dialog = new ProgressDialog(OldOrderDetails.this);
        dialog.setMessage("Cancelling Your Order ...");
        dialog.setCancelable(false);
        dialog.show();
        UpdateOrderModel updateOrderModel = new UpdateOrderModel("cancelled");
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);

        int order_id = oldOrderModel.getId();
        // now request to update the status
        Call<oldOrderModel> oldOrderModelCall = RetrofitClient.getInstance().getApi()
                .updateOrderStatus(authHeader, updateOrderModel, order_id);

        oldOrderModelCall.enqueue(new Callback<dev.mobile.afiqsouq.Models.oldOrderModel>() {
            @Override
            public void onResponse(Call<dev.mobile.afiqsouq.Models.oldOrderModel> call, Response<dev.mobile.afiqsouq.Models.oldOrderModel> response) {
                if (response.code() == 200) {
                    try {
                        oldOrderModel receivedModel = response.body() ;
                        if(receivedModel!= null){
                            dialog.dismiss();
                            Toasty.success(getApplicationContext(), "Order Was Cancelled !!", 1).show();
                            finish();
                        }
                        else {
                            dialog.dismiss();
                            Toasty.error(getApplicationContext(), "Error : " , 1).show();
                        }
                    } catch (Exception r) {
                        dialog.dismiss();
                        Toasty.error(getApplicationContext(), "Error : " + r.getMessage(), 1).show();
                    }
                } else {
                    dialog.dismiss();
                    Toasty.error(getApplicationContext(), "Error : " + response.code(), 1).show();
                }
            }

            @Override
            public void onFailure(Call<dev.mobile.afiqsouq.Models.oldOrderModel> call, Throwable t) {
                dialog.dismiss();
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), 1).show();
            }
        });

    }
}