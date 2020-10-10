package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.CateGoryAdapter;
import dev.spinner_tech.afiqsouq.Adapter.ProductListAdapter;
import dev.spinner_tech.afiqsouq.Adapter.SubCateGoryAdapter;
import dev.spinner_tech.afiqsouq.Models.CartDbModel;
import dev.spinner_tech.afiqsouq.Models.CategoryResp;
import dev.spinner_tech.afiqsouq.Models.ProductModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.database.CartDatabase;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewPage extends AppCompatActivity implements ProductListAdapter.ItemClickListener {

    String parent_id, cat_name;
    RecyclerView catList, productList;
    List<CategoryResp> Sub_Cat_list = new ArrayList<>();
    List<ProductModel> ProductList = new ArrayList<>();
    SubCateGoryAdapter.ItemClickListener itemClickListener;
    TextView headerTitle, cartCount;
    ImageView CartIcon;
    CartDatabase cartDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view_page);

        cartDatabase = Room.databaseBuilder(getApplicationContext(),
                CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

        // get the parent id
        parent_id = getIntent().getStringExtra("parent_id");
        cat_name = getIntent().getStringExtra("cat_name");

        Log.d("TAG", "onCreate: " + parent_id);

        cartCount = findViewById(R.id.textview_discover_cartNumber);
        CartIcon = findViewById(R.id.imageView_discover_cart);
        catList = findViewById(R.id.SubCateogryList);
        headerTitle = findViewById(R.id.header_title);
        productList = findViewById(R.id.productList);
        catList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        productList.setLayoutManager(new GridLayoutManager(this, 2));

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        CartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(), CartListPage.class);
                startActivity(p);
            }
        });


        headerTitle.setText(cat_name);
        // 1st we load the sub category list
        loadSubCategory();
        loadTheProducts();

        itemClickListener = new SubCateGoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                // send the  parent id
                // to the product list
                Intent o = new Intent(getApplicationContext(), ProductList.class);
                o.putExtra("CATEGORY", Sub_Cat_list.get(pos).getId() + "");
                startActivity(o);


            }
        };

    }

    private void loadTheProducts() {

        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<ProductModel>> call = RetrofitClient.getInstance()
                .getApi()
                .getAllProductViaCategory(authHeader, parent_id);

        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {


                if (response.code() == 200) {
                    // here get the list
                    ProductList = response.body();

                    productList.setAdapter(new ProductListAdapter(getApplicationContext(), ProductList, CategoryViewPage.this));


                } else {
                    Toasty.error(getApplicationContext(), "Error : " + response.code(), Toasty.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }

    private void loadSubCategory() {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<CategoryResp>> call = RetrofitClient.getInstance()
                .getApi()
                .getAllSubCategory(authHeader, parent_id, "100");

        call.enqueue(new Callback<List<CategoryResp>>() {
            @Override
            public void onResponse(Call<List<CategoryResp>> call, Response<List<CategoryResp>> response) {

                Log.d("TAG", "onResponse: " + response.raw());
                if (response.code() == 200) {
                    // here get the list
                    Sub_Cat_list = response.body();

                    catList.setAdapter(new SubCateGoryAdapter(getApplicationContext(), Sub_Cat_list, itemClickListener));

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

    @Override
    public void onItemClick(ProductModel model) {

        Intent p = new Intent(getApplicationContext(), ProductDetails.class);
        p.putExtra("MODEL", model);
        //Toasty.error(getApplicationContext(), "T " + model.getId(), 1).show();
        startActivity(p);

    }

    public void countCartItemNumber() {

        try {
            List<CartDbModel> models = cartDatabase.dao().fetchAllTodos();
            cartCount.setText(models.size() + "");
        } catch (Exception e) {

        }
    }

    @Override
    protected void onResume() {
        countCartItemNumber();
        super.onResume();

    }
}