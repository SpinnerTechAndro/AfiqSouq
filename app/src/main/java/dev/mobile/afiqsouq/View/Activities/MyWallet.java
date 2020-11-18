package dev.mobile.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import dev.mobile.afiqsouq.Adapter.PastWalletTransactionListAdapter;
import dev.mobile.afiqsouq.Models.PreviousWalletTransModel;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import dev.mobile.afiqsouq.View.SSLGateWay.sslgateWayPage;
import dev.mobile.afiqsouq.api.api;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyWallet extends AppCompatActivity {

    TextView WalletTv;
    RecyclerView recyclerView;
    PastWalletTransactionListAdapter.ItemClickListener itemClickListener ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);
        WalletTv = findViewById(R.id.balance_tv);
        recyclerView = findViewById(R.id.transactionList) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.top_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext() , sslgateWayPage.class);
                startActivity(p);

            }
        });
    }

    private void createCallToKnowBalance() {
        ProgressDialog progressDialog = new ProgressDialog(MyWallet.this);
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
                    WalletTv.setText(Constants.BDT_SIGN + " " + response.body().toString());

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

    private void loadOldTarnsList() {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://afiqsouq.com/wp-json/wp/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api api = retrofit.create(api.class);

        Call<List<PreviousWalletTransModel>> loadCall =
                api
                .getTransList(authHeader,
                        SharedPrefManager.getInstance(getApplicationContext()).getUser().getId());

        loadCall.enqueue(new Callback<List<PreviousWalletTransModel>>() {
            @Override
            public void onResponse(Call<List<PreviousWalletTransModel>> call, Response<List<PreviousWalletTransModel>> response) {
                if(response.code()==200){
                    // load the list
                    List<PreviousWalletTransModel> list = response.body() ;
                    // now the adapter
                    // flood with rcv
                    recyclerView.setAdapter(new PastWalletTransactionListAdapter(
                            list,
                            getApplicationContext(),
                            itemClickListener
                    ));

                }else {
                    Toasty.error(getApplicationContext(), "Error : " + response.code(), Toasty.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<PreviousWalletTransModel>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), Toasty.LENGTH_LONG).show();

            }
        });

    }

    @Override
    protected void onResume() {
        createCallToKnowBalance();
        loadOldTarnsList();
        super.onResume();
    }
}