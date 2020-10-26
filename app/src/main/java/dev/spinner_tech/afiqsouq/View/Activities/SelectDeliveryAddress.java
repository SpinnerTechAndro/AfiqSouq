package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import dev.spinner_tech.afiqsouq.Models.CreateOrderResp;
import dev.spinner_tech.afiqsouq.Models.OrderResp;
import dev.spinner_tech.afiqsouq.Models.PrefUserModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.Utils.SharedPrefManager;
import dev.spinner_tech.afiqsouq.database.CartDatabase;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDeliveryAddress extends AppCompatActivity {

    private ImageButton addToCartIB,shippingExpandIB,deleteIB,editIB,select_payment_methode_expandIB;
    private ImageView activeStatusIV ,backIB;
    private TextView cartItemCountTV ;
    EditText nameTV,addressTV,phoneTV;
    private Button addNewAddressBtn,placeOrderBtn;
    private RelativeLayout bankTransferRL,cash_on_deliveryRL,paypalRL,masterCardRL;
    CreateOrderResp orderResp ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delivery_address);

        // get the order resp
        orderResp = (CreateOrderResp) getIntent().getSerializableExtra("MODEL")  ;
        //image Button
        backIB=findViewById(R.id.backIB);
   //     addToCartIB=findViewById(R.id.addTocartIB);
        shippingExpandIB=findViewById(R.id.expand_icon_IB);
        deleteIB=findViewById(R.id.delete_IB);
        editIB=findViewById(R.id.edit_IB);
        select_payment_methode_expandIB=findViewById(R.id.select_payment_methode_expandIB);

        //textview
     //  cartItemCountTV=findViewById(R.id.countCartItemTV);
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


        backIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(orderResp== null){
                    Toasty.error(getApplicationContext(), "Something went Wrong" , 1 ).show();
                }
                else {
                    String name = nameTV.getText().toString() ;
                    String address = addressTV.getText().toString() ;
                    String phone = phoneTV.getText().toString()  ;
                    if(TextUtils.isEmpty(name)|| TextUtils.isEmpty(phone) ||TextUtils.isEmpty(address)){
                        Toasty.error(getApplicationContext(), "Please Enter The Value Correctly!!" , 1 ).show();
                    }

                    else {

                        CreateOrder(orderResp);
                    }
                }

            }
        });


        loadUserDataFromCache() ;
    }

    private void loadUserDataFromCache() {
        PrefUserModel model =  SharedPrefManager.getInstance(getApplicationContext()).getUser() ;
        nameTV.setText(model.getName());
        addressTV.setText(model.getDeliveryAddress());
        phoneTV.setText(model.getPh());

    }

    private void CreateOrder(CreateOrderResp orderModel) {
        ProgressDialog dialog = new ProgressDialog(SelectDeliveryAddress.this) ;
        dialog.setMessage("Placing Your Order...");
        dialog.show();
        orderModel.getShipping().setAddress1(addressTV.getText().toString());
        orderModel.getShipping().setFirstName(nameTV.getText().toString()) ;

        orderModel.getBilling().setAddress1(addressTV.getText().toString());
        orderModel.getBilling().setFirstName(nameTV.getText().toString()) ;
        orderModel.getBilling().setPhone(phoneTV.getText().toString()) ;

        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<OrderResp> createOrderCall = RetrofitClient.getInstance()
                .getApi()
                .CreateOrder(authHeader, orderModel);

        createOrderCall.enqueue(new Callback<OrderResp>() {
            @Override
            public void onResponse(Call<OrderResp> call, @NotNull Response<OrderResp> response) {

                Log.d("TAG", "onResponse: " + response.code());
                if(response.code() == 201){

                    CartDatabase.databaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            CartDatabase.getDatabase(getApplicationContext()).dao().nukeCartTable();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    Intent p = new Intent(getApplicationContext()  , OrderDone.class) ;
                                    startActivity(p);
                                    finish();
                                }
                            });
                        }

                    });


                }
                else {
                    dialog.dismiss();
                    Toasty.error(getApplicationContext()  , "Something Went Wrong Try Again!! Code " + response.code() , 1 )
                            .show();
                }

            }

            @Override
            public void onFailure(Call<OrderResp> call, Throwable t) {
                dialog.dismiss();
                Toasty.error(getApplicationContext() , "Error "+  t.getMessage()  , 1 ).show();
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }


}