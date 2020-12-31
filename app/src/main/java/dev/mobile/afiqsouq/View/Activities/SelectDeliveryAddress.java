package dev.mobile.afiqsouq.View.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
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

import androidx.appcompat.app.AppCompatActivity;

import com.sslwireless.sslcommerzlibrary.model.initializer.CustomerInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.TransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.CurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.TransactionResponseListener;

import org.jetbrains.annotations.NotNull;

import dev.mobile.afiqsouq.Models.CreateOrderResp;
import dev.mobile.afiqsouq.Models.OrderResp;
import dev.mobile.afiqsouq.Models.PaymentDone;
import dev.mobile.afiqsouq.Models.PrefUserModel;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import dev.mobile.afiqsouq.api.api;
import dev.mobile.afiqsouq.database.CartDatabase;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelectDeliveryAddress extends AppCompatActivity implements TransactionResponseListener {

    EditText nameTV, addressTV, phoneTV;
    CreateOrderResp orderResp;
    String total;
    double subTotal;
    AlertDialog Paymnetdialog;
    boolean isWallet = false;
    private ImageButton addToCartIB, shippingExpandIB, deleteIB, editIB, select_payment_methode_expandIB;
    private ImageView activeStatusIV, backIB;
    private TextView cartItemCountTV;
    private Button addNewAddressBtn, placeOrderBtn;
    private RelativeLayout bankTransferRL, cash_on_deliveryRL, paypalRL, masterCardRL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_delivery_address);

        // get the order resp
        orderResp = (CreateOrderResp) getIntent().getSerializableExtra("MODEL");
        total = getIntent().getStringExtra("TOTAL");
        subTotal = Double.parseDouble(total);

        //image Button
        backIB = findViewById(R.id.backIB);
        //addToCartIB=findViewById(R.id.addTocartIB);
        shippingExpandIB = findViewById(R.id.expand_icon_IB);
        deleteIB = findViewById(R.id.delete_IB);
        editIB = findViewById(R.id.edit_IB);
        select_payment_methode_expandIB = findViewById(R.id.select_payment_methode_expandIB);

        //textview
        //  cartItemCountTV=findViewById(R.id.countCartItemTV);
        nameTV = findViewById(R.id.name_TV);
        addressTV = findViewById(R.id.address_TV);
        phoneTV = findViewById(R.id.phone_number_tv_id);

        //imageView
        activeStatusIV = findViewById(R.id.active_status_IV);

        //button
        addNewAddressBtn = findViewById(R.id.add_a_new_address_Btn);
        placeOrderBtn = findViewById(R.id.place_order_id);

        //realative Layout
        bankTransferRL = findViewById(R.id.bankTransferRL);
        cash_on_deliveryRL = findViewById(R.id.cash_on_delivery_RL);
        paypalRL = findViewById(R.id.bkashRl);
        masterCardRL = findViewById(R.id.master_card_RL);

        cash_on_deliveryRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderResp == null) {
                    Toasty.error(getApplicationContext(), "Something went Wrong", 2).show();
                } else {
                    String name = nameTV.getText().toString();
                    String address = addressTV.getText().toString();
                    String phone = phoneTV.getText().toString();
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
                        Toasty.error(getApplicationContext(), "Please Enter The Value Correctly!!", 2).show();
                    } else {

                        CreateOrder(orderResp);
                    }
                }
            }
        });

        bankTransferRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPay(subTotal);
            }
        });

        paypalRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPay(subTotal);
            }
        });

        backIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (orderResp == null) {
                    Toasty.error(getApplicationContext(), "Something went Wrong", 1).show();
                } else {
                    String name = nameTV.getText().toString();
                    String address = addressTV.getText().toString();
                    String phone = phoneTV.getText().toString();
                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(address)) {
                        Toasty.error(getApplicationContext(), "Please Enter The Value Correctly!!", 2).show();
                    } else {

                        CreateOrder(orderResp);
                    }
                }

            }
        });


        masterCardRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // use the wallet
                // first check  his wallet
                createCallToKnowBalance();

            }
        });


        loadUserDataFromCache();
    }

    private void loadUserDataFromCache() {
        PrefUserModel model = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        nameTV.setTextColor(Color.BLACK);
        addressTV.setTextColor(Color.BLACK);
        phoneTV.setTextColor(Color.BLACK);
        nameTV.setText(model.getName());
        addressTV.setText(model.getDeliveryAddress());
        phoneTV.setText(model.getPh());

    }

    private void CreateOrder(CreateOrderResp orderModel) {
        ProgressDialog dialog = new ProgressDialog(SelectDeliveryAddress.this);
        dialog.setMessage("Placing Your Order...");
        dialog.setCancelable(false);
        dialog.show();
        orderModel.getShipping().setAddress1(addressTV.getText().toString());
        orderModel.getShipping().setFirstName(nameTV.getText().toString());

        orderModel.getBilling().setAddress1(addressTV.getText().toString());
        orderModel.getBilling().setFirstName(nameTV.getText().toString());
        orderModel.getBilling().setPhone(phoneTV.getText().toString());

        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<OrderResp> createOrderCall = RetrofitClient.getInstance()
                .getApi()
                .CreateOrder(authHeader, orderModel);

        createOrderCall.enqueue(new Callback<OrderResp>() {
            @Override
            public void onResponse(Call<OrderResp> call, @NotNull Response<OrderResp> response) {

                Log.d("TAG", "onResponse: " + response.code());
                if (response.code() == 201) {

                    CartDatabase.databaseWriteExecutor.execute(new Runnable() {
                        @Override
                        public void run() {
                            CartDatabase.getDatabase(getApplicationContext()).dao().nukeCartTable();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                    Intent p = new Intent(getApplicationContext(), OrderDone.class);
                                    startActivity(p);
                                    finish();
                                }
                            });
                        }

                    });


                } else {
                    dialog.dismiss();
                    Toasty.error(getApplicationContext(), "Something Went Wrong Try Again!! Code " + response.code(), 2)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<OrderResp> call, Throwable t) {
                dialog.dismiss();
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), 2).show();
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void createCallToKnowBalance() {
        ProgressDialog progressDialog = new ProgressDialog(SelectDeliveryAddress.this);
        progressDialog.setMessage("Getting Your Account Details ...");
        progressDialog.show();
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://afiqsouq.com/wp-json/wp/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api api = retrofit.create(api.class);

        Call<String> apiCall = api.getCurrentBal(authHeader,
                SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());
        apiCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.code() == 200) {
                    progressDialog.dismiss();
                    // WalletTv.setText(Constants.BDT_SIGN + " " + response.body().toString());
                    CreatePaymentDialouge(response.body().toString());

                    //  Toasty.success(getApplicationContext(),"Success : "+  response.body().toString(), Toasty.LENGTH_LONG).show();

                } else {
                    progressDialog.dismiss();
                    Toasty.error(getApplicationContext(), "Error : " + response.code(), Toasty.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }

    private void CreatePaymentDialouge(String s) {
        // setup the alert builder

        AlertDialog.Builder builder = new AlertDialog.Builder(SelectDeliveryAddress.this);
        builder.setTitle("Payment Procedure");
        builder.setMessage("Would you like to Pay Total Of " + subTotal + " Bdt \n Your Current Balance is " + s + " Bdt");

        // add the buttons
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    double currentBalance = Double.parseDouble(s);
                    if (currentBalance >= subTotal) {
                        // now set the method as paid
                        orderResp.setPaymentMethodTitle("Wallet");
                        orderResp.setSetPaid(true);
                        isWallet = true;
                        //  CreateOrder(orderResp);
                        // 1st deduct the balance from wallet 

                        deductBallanceFromWallet(orderResp);

                    } else {
                        dialog.dismiss();
                        Toasty.error(getApplicationContext(), "Your Balance is Low !! ", Toasty.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toasty.error(getApplicationContext(), "Error : " + e.getMessage(), 2).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Paymnetdialog.dismiss();
            }
        });

        // create and show the alert dialog
        Paymnetdialog = builder.create();
        Paymnetdialog.show();
    }

    private void deductBallanceFromWallet(CreateOrderResp orderResp) {
        ProgressDialog progressDialog = new ProgressDialog(SelectDeliveryAddress.this);
        progressDialog.setMessage("Deducting Balance ...");
        progressDialog.show();
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://afiqsouq.com/wp-json/wp/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api api = retrofit.create(api.class);

        Call<PaymentDone> paymentDoneCall = api.deductBalance(authHeader, SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()
                , "debit", "Product Purchase", subTotal);

        paymentDoneCall.enqueue(new Callback<PaymentDone>() {
            @Override
            public void onResponse(Call<PaymentDone> call, Response<PaymentDone> response) {
                if (response.code() == 200) {
                    if (response.body().getResponse().toString().toLowerCase().equals("success")) {
                        // product
                        progressDialog.dismiss();
                        CreateOrder(orderResp);
                    } else {
                        progressDialog.dismiss();
                        Toasty.error(getApplicationContext(), "Payment Did Not Completed Error :  " + response.body().getResponse(), 1).show();

                    }
                } else {
                    progressDialog.dismiss();
                    Toasty.error(getApplicationContext(), "Payment Did Not Completed Error :  " + response.code(), 2).show();

                }
            }

            @Override
            public void onFailure(Call<PaymentDone> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(getApplicationContext(), "Payment Did not Completed Error :  " + t.getMessage(), 2).show();
            }
        });


    }

    private void doPay(double f) {
      PrefUserModel model =   SharedPrefManager.getInstance(getApplicationContext())
                .getUser() ;
//        SSLCCustomerInfoInitializer(&quot;customer name&quot;, &quot;customer email&quot;,
//&quot;address&quot;, &quot;dhaka&quot;, &quot;1214&quot;, &quot;Bangladesh&quot;, “phoneNumber”);
        final CustomerInfoInitializer customerInfoInitializer = new CustomerInfoInitializer(
              model.getName() ,
              model.getMail(),
              model.getDeliveryAddress(),
              model.getState() ,
              "1",
                model.getCountry() ,
                model.getPh()
        ) ;
                                                                      //metac5c8299fbcbb15 metac5c8299fbcbb15@ssl

//        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization("afiqsouqlive", "5FA907E4DA48684914"
//                , f, CurrencyType.BDT, "SSL : " + System.currentTimeMillis(), "Package", SdkType.LIVE);
        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization("afiqsouqlive", "5FA907E4DA48684914"
                , f, CurrencyType.BDT, "SSL : " + System.currentTimeMillis(), "Package", SdkType.LIVE);


        IntegrateSSLCommerz.getInstance(SelectDeliveryAddress.this)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addCustomerInfoInitializer(customerInfoInitializer)
                .buildApiCall(SelectDeliveryAddress.this);
    }

    @Override
    public void transactionSuccess(TransactionInfoModel transactionInfoModel) {
        // If payment is success and risk label is 0.
        if (transactionInfoModel.getRiskLevel().equals("0")) {

            Log.d("TAG", "Transaction Successfully completed");
            Log.d("TAG", transactionInfoModel.getTranId());
            Log.d("TAG", transactionInfoModel.getBankTranId());
            Log.d("TAG", transactionInfoModel.getAmount());
            Log.d("TAG", transactionInfoModel.getStoreAmount());
            Log.d("TAG", transactionInfoModel.getTranDate());
            Log.d("TAG", transactionInfoModel.getStatus());
            //
            //    tv.setText("Transaction Successfully completed");
            orderResp.setSetPaid(true);
            orderResp.setPaymentMethodTitle("SSL");
            CreateOrder(orderResp);

        }
        // Payment is success but payment is not complete yet. Card on hold now.
        else {
            Log.d("TAG", "Transaction in risk. Risk Title : " + transactionInfoModel.getRiskTitle().toString());
            Toasty.error(getApplicationContext(), "Transaction in risk. Try Again ", 2).show();

        }
    }

    @Override
    public void transactionFail(String s) {
        Toasty.error(getApplicationContext(), "Transaction Failed " + s , 2).show();

    }

    @Override
    public void merchantValidationError(String s) {
        Toasty.error(getApplicationContext(), "Transaction Failed " + s , 2).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUserDataFromCache();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUserDataFromCache();
    }
}