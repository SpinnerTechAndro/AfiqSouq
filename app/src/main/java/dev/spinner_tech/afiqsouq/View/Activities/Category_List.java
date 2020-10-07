package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewTreeObserver;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.CateGoryAdapter;
import dev.spinner_tech.afiqsouq.Models.CategoryResp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Category_List extends AppCompatActivity {
    RecyclerView recyclerView;
    List<CategoryResp> categoryResp = new ArrayList<>();
    CateGoryAdapter.ItemClickListener itemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category__list);

        setUpUi();

        // back tbn
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        itemClickListener = (view, pos) -> {
            // Toasty.success(getApplicationContext(), "Name : " + categoryResp.get(pos).getName() , 1).show();

            String name = categoryResp.get(pos).getName();
            if (name.contains("amp;")) {

                name = categoryResp.get(pos).getName().replace("amp;", "");

            }
            Intent p = new Intent(getApplicationContext(), CategoryViewPage.class);
            p.putExtra("cat_name", name);
            p.putExtra("parent_id", categoryResp.get(pos).getId() + "");
            startActivity(p);


        };

    }

    private void setUpUi() {
        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        loadCategoryList();
    }

    private void loadCategoryList() {

        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<CategoryResp>> call = RetrofitClient.getInstance()
                .getApi()
                .getAllParentCategory(authHeader);

        call.enqueue(new Callback<List<CategoryResp>>() {
            @Override
            public void onResponse(Call<List<CategoryResp>> call, Response<List<CategoryResp>> response) {

                if (response.code() == 200) {
                    // here get the list
                    categoryResp = response.body();

                    recyclerView.setAdapter(new CateGoryAdapter(getApplicationContext(), categoryResp, itemClickListener));

                    recyclerView.getViewTreeObserver().addOnPreDrawListener(

                            new ViewTreeObserver.OnPreDrawListener() {
                                @Override
                                public boolean onPreDraw() {

                                    recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);
                                    for (int i = 0; i < recyclerView.getChildCount(); i++) {
                                        View v = recyclerView.getChildAt(i);
                                        v.setAlpha(0.0f);
                                        v.animate()
                                                .alpha(1.0f)
                                                .setDuration(300)
                                                .setStartDelay(i * 50)
                                                .start();
                                    }
                                    return true;
                                }
                            }
                    );

                } else {
                    Toasty.error(getApplicationContext(), "Error : " + response.code(), Toasty.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResp>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }
}