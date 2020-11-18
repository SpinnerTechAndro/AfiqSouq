package dev.mobile.afiqsouq.View.SSLGateWay;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.TransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.CurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.TransactionResponseListener;

import dev.mobile.afiqsouq.Models.PaymentDone;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import dev.mobile.afiqsouq.api.api;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class sslgateWayPage extends AppCompatActivity implements TransactionResponseListener {
    String TAG = "PAY";
    TextView tv;
    EditText et;
    double oldCoin, newCoin;
    Boolean isCoinAvailable;
    String oldCOIN, time, point;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sslgate_way_page);
        tv = (TextView) findViewById(R.id.textView);
        et = (EditText) findViewById(R.id.editText);

        findViewById(R.id.GoBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();


            }
        });

        ((AppCompatButton) findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    float amount = Float.parseFloat(et.getText().toString());
                    if (amount > 0.0) {
                        doPay(amount);
                        // uploadItToTheServer("10");
                        // sentToTheTransPage(String.valueOf(amount));
                    } else {
                        Toast.makeText(sslgateWayPage.this, "Enter Amount!", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

    }


    private void doPay(Float f) {

        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization("afiqsouqlive", "5FA907E4DA48684914"
                , f, CurrencyType.BDT, "SSL : " + System.currentTimeMillis(), "Package", SdkType.LIVE);


        IntegrateSSLCommerz.getInstance(sslgateWayPage.this)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .buildApiCall(sslgateWayPage.this);
    }

    @Override
    public void transactionSuccess(TransactionInfoModel transactionInfoModel) {

        // If payment is success and risk label is 0.
        if (transactionInfoModel.getRiskLevel().equals("0")) {

            Log.d(TAG, "Transaction Successfully completed");
            Log.d(TAG, transactionInfoModel.getTranId());
            Log.d(TAG, transactionInfoModel.getBankTranId());
            Log.d(TAG, transactionInfoModel.getAmount());
            Log.d(TAG, transactionInfoModel.getStoreAmount());
            Log.d(TAG, transactionInfoModel.getTranDate());
            Log.d(TAG, transactionInfoModel.getStatus());
            //
            //    tv.setText("Transaction Successfully completed");
            et.setText(null);


            uploadItToTheServer(transactionInfoModel.getAmount(), transactionInfoModel.getTranId());

        }
        // Payment is success but payment is not complete yet. Card on hold now.
        else {
            Log.d(TAG, "Transaction in risk. Risk Title : " + transactionInfoModel.getRiskTitle().toString());
            tv.setText("Transaction in risk. Try Again ");
            Toasty.error(getApplicationContext(), "Transaction in risk. Try Again", Toasty.LENGTH_LONG).show();
            et.setText(null);
        }
    }


    @Override
    public void transactionFail(String s) {
        Log.e(TAG, "Transaction Failed");
        tv.setText("Transaction Failed " + s);
        Toasty.error(getApplicationContext(), " Try Again . " + s, Toasty.LENGTH_LONG).show();

        et.setText(null);
    }

    @Override
    public void merchantValidationError(String s) {
        Toast.makeText(getApplicationContext(), "Error Msg : " + s, Toast.LENGTH_SHORT)
                .show();

    }

    private void uploadItToTheServer(String amount, String getTranId) {
        ProgressDialog progressDialog = new ProgressDialog(sslgateWayPage.this);
        progressDialog.setMessage("Adding Balance ...");
        progressDialog.show();
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://afiqsouq.com/wp-json/wp/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api api = retrofit.create(api.class);

        Call<PaymentDone> paymentDoneCall = api.deductBalance(authHeader, SharedPrefManager.getInstance(getApplicationContext()).getUser().getId()
                , "credit", " Wallet Added Via SSL . Transaction ID : " + getTranId, Double.parseDouble(amount));

        paymentDoneCall.enqueue(new Callback<PaymentDone>() {
            @Override
            public void onResponse(Call<PaymentDone> call, Response<PaymentDone> response) {
                if (response.code() == 200) {
                    if (response.body().getResponse().toString().toLowerCase().equals("success")) {
                        // product
                        progressDialog.dismiss();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toasty.error(getApplicationContext(), "Payment Did Not Completed Error :  " + response.body().getResponse(), 1).show();

                    }
                } else {
                    progressDialog.dismiss();
                    Toasty.error(getApplicationContext(), "Payment Did Not Completed Error :  " + response.code(), 1).show();

                }
            }

            @Override
            public void onFailure(Call<PaymentDone> call, Throwable t) {
                progressDialog.dismiss();
                Toasty.error(getApplicationContext(), "Payment Did not Completed Error :  " + t.getMessage(), 1).show();
            }
        });


    }
}