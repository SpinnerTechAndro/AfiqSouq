package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.HorizontralProductListAdapter;
import dev.spinner_tech.afiqsouq.Adapter.ProductListAdapter;
import dev.spinner_tech.afiqsouq.Models.CartDbModel;
import dev.spinner_tech.afiqsouq.Models.ProductModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.View.Fragment.HomeFragment;
import dev.spinner_tech.afiqsouq.database.CartDatabase;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top_Recent_List extends AppCompatActivity implements ProductListAdapter.ItemClickListener {
    RecyclerView recyclerView;
    String category;
    Call<List<ProductModel>> call;
    int currentPage = 1;
    boolean isScrolling = false, isEnd = false;
    SpinKitView progress;
    LinearLayoutManager manager;
    List<ProductModel> plist = new ArrayList<>();
    ProductListAdapter adapter;
    String type  ;
    Call<List<ProductModel>> popularProduct ;
    TextView titleView , countCartTv ;
    CartDatabase cartDatabase  ;
    ImageView cartIcon ;

    int currentItems, totalItems, scrollOutItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top__recent__list);
        recyclerView = findViewById(R.id.list);
        titleView = findViewById(R.id.header_title) ;
        progress = (SpinKitView) findViewById(R.id.spin_kit);
        countCartTv = findViewById(R.id.textview_discover_cartNumber);
        cartIcon = findViewById(R.id.imageView_discover_cart) ;
        manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        type = getIntent().getStringExtra("TYPE") ;
        cartDatabase = Room.databaseBuilder(getApplicationContext(),
                CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        loadAllPopularProducts(type);

        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p  = new Intent(getApplicationContext(), CartListPage.class) ;
                startActivity(p);
            }
        });

    }

    public void loadAllPopularProducts(String type) {
        progress.setVisibility(View.VISIBLE);
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);

        if(type.equals("RECENT")){
            titleView.setText("Recent Products");
            popularProduct = RetrofitClient.getInstance()
                    .getApi()
                    .getAllRecentProducts(authHeader  , 20);
        }
        else {
            titleView.setText("Top Deals");
            popularProduct = RetrofitClient.getInstance()
                    .getApi()
                    .getAllFeaturedProducts(authHeader , 20 );
        }



        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        popularProduct.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.code() == 200) {
                    // now load the data
                    List<ProductModel> productModelList = response.body();
                    //load the data
                    recyclerView.setAdapter(new ProductListAdapter(getApplicationContext(), productModelList, Top_Recent_List.this));
                    progress.setVisibility(View.GONE);
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
                    Toasty.error(getApplicationContext(), "Something Went Wrong !! Code : " + response.code(), Toasty.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Something Went Wrong !! ", Toasty.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onItemClick(ProductModel model , int itemClicked ) {

        if (itemClicked != R.id.imageview_search_cart_fr) {
            Intent p = new Intent(getApplicationContext(), ProductDetails.class);
            p.putExtra("MODEL", model);
            //Toasty.error(getApplicationContext(), "T " + model.getId(), 1).show();
            startActivity(p);

        } else {
            countCartItemNumber();
        }
    }

        @Override
    protected void onResume() {
        countCartItemNumber();
        super.onResume();

    }

    public void countCartItemNumber() {

        try{
            List<CartDbModel> models  =  cartDatabase.dao().fetchAllTodos() ;
            countCartTv.setText(models.size()+"");
        }
        catch (Exception e){

        }
    }



}