package dev.mobile.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.afiqsouq.Adapter.PastOrderListAdapter;
import dev.mobile.afiqsouq.Adapter.couponAdapter;
import dev.mobile.afiqsouq.Models.CouponModel;
import dev.mobile.afiqsouq.Models.oldOrderModel;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.Utils.SharedPrefManager;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VouchersList extends AppCompatActivity  {
    RecyclerView recyclerView;
    List<CouponModel> orderModelList = new ArrayList<>();
    SpinKitView spinKitView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vouchers_list);

        findViewById(R.id.backIB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setUPVIEWS();
    }

    private void setUPVIEWS() {
        recyclerView = findViewById(R.id.list);
        spinKitView = findViewById(R.id.spin_kit1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    @Override
    protected void onResume() {
        super.onResume();
        loadOrderList();
    }
    private void loadOrderList() {
        spinKitView.setVisibility(View.VISIBLE);
        String id = SharedPrefManager.getInstance(getApplicationContext()).getUser().getId().toString();
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<CouponModel>> call = RetrofitClient.getInstance().getApi()
                .getCoupons(authHeader);

        call.enqueue(new Callback<List<CouponModel>>() {
            @Override
            public void onResponse(Call<List<CouponModel>> call, Response<List<CouponModel>> response) {
                if (response.code() == 200) {
                    spinKitView.setVisibility(View.GONE);
                    orderModelList = response.body();
                    couponAdapter adapter = new couponAdapter( getApplicationContext() ,orderModelList);
                    recyclerView.setAdapter(adapter);
                } else {
                    spinKitView.setVisibility(View.GONE);
                    Toasty.error(getApplicationContext(), response.code() + " ", 1).show();
                }
            }

            @Override
            public void onFailure(Call<List<CouponModel>> call, Throwable t) {
                spinKitView.setVisibility(View.GONE);
                Toasty.error(getApplicationContext(), t.getMessage() + " ", 1).show();
            }
        });
    }
}