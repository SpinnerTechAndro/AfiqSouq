package dev.spinner_tech.afiqsouq.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AbsListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.ProductListAdapter;
import dev.spinner_tech.afiqsouq.Models.ProductModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductList extends AppCompatActivity implements ProductListAdapter.ItemClickListener {

    RecyclerView recyclerView;
    String category;
    Call<List<ProductModel>> call;
    int currentPage = 1;
    boolean isScrolling = false, isEnd = false;
    SpinKitView progress;
    LinearLayoutManager manager;
    List<ProductModel> plist = new ArrayList<>();
    ProductListAdapter adapter;

    int currentItems, totalItems, scrollOutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        recyclerView = findViewById(R.id.list);
        progress = (SpinKitView) findViewById(R.id.spin_kit);
        manager = new GridLayoutManager(this ,2 );

        recyclerView.setLayoutManager(manager);
        adapter = new ProductListAdapter(getApplicationContext() , plist , this ) ;
        recyclerView.setAdapter(adapter);
        //reciver the cat and type ;

        category = getIntent().getStringExtra("CATEGORY");


        decideWhatToLoad(category, currentPage);
        initScrollListener();

    }

    private void decideWhatToLoad(String category, int page) {
        progress.setVisibility(View.VISIBLE);
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        if (category.isEmpty() || category == null) {
            // load the product list

            call = RetrofitClient.getInstance()
                    .getApi()
                    .getAllProducts(authHeader, "15", page);


        } else {
            // load with product via category
            call = RetrofitClient.getInstance()
                    .getApi()
                    .getAllProductViaCategoryID(authHeader, category, "15", page);

        }

        // load  the call
        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {


                if (response.code() == 200) {
                    int oldSize = plist.size();

                    // try for it
                    plist.addAll(response.body());

                    if (oldSize == plist.size()) {
                        isEnd = true;

                    } else {
                        isEnd = false;
                    }

                    adapter.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);

                } else {
                    Toasty.error(getApplicationContext(), "Error : " + response.code(), 1).show();
                progress.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), 1).show();

            }
        });


    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) { // scroll down
                    currentItems = manager.getChildCount();
                    totalItems = manager.getItemCount();
                    scrollOutItems = manager.findFirstVisibleItemPosition();

                    if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
                        isScrolling = false;
                        loadMore();
                    }
                }





            }
        });


    }

    private void loadMore() {

        if(isEnd){
            Toasty.warning(getApplicationContext(), "Your At The Last Page." , Toasty.LENGTH_LONG)
                    .show();
        }
        else {
            currentPage++;
            decideWhatToLoad(category, currentPage);
        }


    }

    @Override
    public void onItemClick(ProductModel model) {
        Intent p = new Intent(getApplicationContext(), ProductDetails.class) ;
        p.putExtra("MODEL" , model) ;

        startActivity(p);
    }
}