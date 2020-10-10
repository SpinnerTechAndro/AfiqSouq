package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.PastOrderListAdapter;
import dev.spinner_tech.afiqsouq.Models.oldOrderModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.Utils.SharedPrefManager;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class oldOrderList extends AppCompatActivity implements PastOrderListAdapter.ItemClickListener {
    RecyclerView recyclerView;
    List<oldOrderModel> orderModelList = new ArrayList<>();
    SpinKitView spinKitView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_order_list);

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
        Call<List<oldOrderModel>> call = RetrofitClient.getInstance().getApi()
                .oldOrderList(authHeader, id);

        call.enqueue(new Callback<List<oldOrderModel>>() {
            @Override
            public void onResponse(Call<List<oldOrderModel>> call, Response<List<oldOrderModel>> response) {
                if (response.code() == 200) {
                    spinKitView.setVisibility(View.GONE);
                    orderModelList = response.body();
                    PastOrderListAdapter adapter = new PastOrderListAdapter(orderModelList, getApplicationContext(), oldOrderList.this);
                    recyclerView.setAdapter(adapter);
                } else {
                    spinKitView.setVisibility(View.GONE);
                    Toasty.error(getApplicationContext(), response.code() + " ", 1).show();
                }
            }

            @Override
            public void onFailure(Call<List<oldOrderModel>> call, Throwable t) {
                spinKitView.setVisibility(View.GONE);
                Toasty.error(getApplicationContext(), t.getMessage() + " ", 1).show();
            }
        });
    }

    @Override
    public void onItemClick(oldOrderModel model) {
        Toasty.error(getApplicationContext(), "" + model.getId(), 1).show();
    }
}