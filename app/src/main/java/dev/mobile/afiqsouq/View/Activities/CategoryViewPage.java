package dev.mobile.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
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

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.afiqsouq.Adapter.ProductListDifferAdapter;
import dev.mobile.afiqsouq.Adapter.SubCateGoryAdapter;
import dev.mobile.afiqsouq.Models.CartDbModel;
import dev.mobile.afiqsouq.Models.CategoryResp;
import dev.mobile.afiqsouq.Models.ProductModel;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.database.CartDatabase;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewPage extends AppCompatActivity implements ProductListDifferAdapter.ItemClickListener {

    String parent_id, cat_name;
    RecyclerView catList, productList;
    List<CategoryResp> Sub_Cat_list = new ArrayList<>();
    List<ProductModel> pList = new ArrayList<>();
    SubCateGoryAdapter.ItemClickListener itemClickListener;
    TextView headerTitle, cartCount;
    ImageView CartIcon;
    CartDatabase cartDatabase;
    NestedScrollView nestedScrollView;
    SpinKitView categoryViewLoad ;
    int currentPage = 1, currentItems, totalItems, scrollOutItems;
    boolean isScrolling = false, isEnd = false;
    GridLayoutManager manager ;
    ProductListDifferAdapter adapter;
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
        categoryViewLoad = findViewById(R.id.spin_kit_review1) ;
        productList = findViewById(R.id.productList);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        catList.setLayoutManager(new LinearLayoutManager(this  , RecyclerView.HORIZONTAL , false));

        categoryViewLoad.setVisibility(View.VISIBLE);
        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       //productList.setHasFixedSize(true);
        CartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(), CartListPage.class);
                startActivity(p);
            }
        });


        headerTitle.setText(cat_name);
        // 1st we load the sub category list


        initScrollListener();
        loadSubCategory();
        manager=new GridLayoutManager(this, 2) ;
        productList.setLayoutManager(manager);
        adapter = new ProductListDifferAdapter(getApplicationContext(), CategoryViewPage.this);
        productList.setAdapter(adapter);
        loadTheProducts(1);

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

    private void loadTheProducts(int page ) {
        categoryViewLoad.setVisibility(View.VISIBLE);
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<ProductModel>> call = RetrofitClient.getInstance()
                .getApi()
                .getAllProductViaCategory(authHeader, parent_id , "30" , page);


        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {


                if (response.code() == 200) {
                    // here get the list
                  //  categoryViewLoad.setVisibility(View.GONE);
                  //  pList = response.body();

                    try {
                        if (response.code() == 200) {
                            int oldSize = pList.size();
                            // try for it
                            pList.addAll(response.body());
                            Log.d("TAG", "loadTheProducts: " + pList.size() );
                            if (oldSize == pList.size()) {
                                isEnd = true;

                            } else {
                                isEnd = false;
                            }
                         //   adapter = new ProductListAdapter(getApplicationContext(), ProductList, CategoryViewPage.this);
                         //  adapter.notifyDataSetChanged();
                           // productList.setAdapter(adapter);
                            adapter.submitlist(pList);
                         //   Toasty.success(getApplicationContext(), "Error : " + pList.size(), 1).show();

                            categoryViewLoad.setVisibility(View.GONE);


                        } else {
                            categoryViewLoad.setVisibility(View.GONE);
                            Toasty.error(getApplicationContext(), "Error : " + response.code(), 1).show();
                            //progress.setVisibility(View.GONE);
                        }
                    } catch (Exception r) {
                        categoryViewLoad.setVisibility(View.GONE);
                        Toasty.error(getApplicationContext(), "Error :  Something Went Wrong !! ", 1).show();
                    }


                } else {
                    categoryViewLoad.setVisibility(View.GONE);
                    Toasty.error(getApplicationContext(), "Error : " + response.code(), Toasty.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                categoryViewLoad.setVisibility(View.GONE);
                Toasty.error(getApplicationContext(), "Error : " + t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }


    private void initScrollListener() {

        nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) &&
                        scrollY > oldScrollY) {
                    //code to fetch more data for endless scrolling

                    int test = (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight());
                    ;
                    Log.d("TAG", "initScrollListener: " + test + " old " + oldScrollY + "new " + scrollY);
                    loadMore();

                }
            }
        });


    }

    private void loadMore() {

        if (isEnd) {
            Toasty.warning(getApplicationContext(), "Your At The Last Page.", Toasty.LENGTH_SHORT)
                    .show();
        } else {
//            Toasty.warning(context, "Loading Start", Toasty.LENGTH_SHORT)
//                    .show();
            currentPage++;
            loadTheProducts(currentPage);
        }


    }

    private void loadSubCategory() {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<CategoryResp>> call = RetrofitClient.getInstance()
                .getApi()
                .getAllSubCategory(authHeader, parent_id, "100" ,"15");

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
    public void onItemClick(ProductModel model , int itemClicked ) {

        if(itemClicked!=R.id.imageview_search_cart_fr){
            Intent p = new Intent(getApplicationContext(), ProductDetails.class);
            p.putExtra("MODEL", model);
            //Toasty.error(getApplicationContext(), "T " + model.getId(), 1).show();
            startActivity(p);

        }
        else {
            countCartItemNumber();
        }


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
        productList.setAdapter(adapter);
        super.onResume();

    }

}