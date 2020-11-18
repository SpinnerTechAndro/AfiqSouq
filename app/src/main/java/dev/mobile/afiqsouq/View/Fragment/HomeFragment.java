package dev.mobile.afiqsouq.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.afiqsouq.Adapter.HorizontralCateGoryAdapter;
import dev.mobile.afiqsouq.Adapter.HorizontralProductListAdapter;
import dev.mobile.afiqsouq.Adapter.ProductListDifferAdapter;
import dev.mobile.afiqsouq.Adapter.SliderAdapterExample;
import dev.mobile.afiqsouq.Models.CategoryResp;
import dev.mobile.afiqsouq.Models.ProductModel;
import dev.mobile.afiqsouq.Models.sliderItem;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;
import dev.mobile.afiqsouq.View.Activities.CategoryViewPage;
import dev.mobile.afiqsouq.View.Activities.Category_List;
import dev.mobile.afiqsouq.View.Activities.ProductDetails;
import dev.mobile.afiqsouq.View.Activities.ProductList;
import dev.mobile.afiqsouq.View.Activities.Top_Recent_List;
import dev.mobile.afiqsouq.View.Home_Activity;
import dev.mobile.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements HorizontralProductListAdapter.ItemClickListener, ProductListDifferAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    TextView search, viewAllFeature, viewAllPopular, AllProduct , AllElectronics , AllHealth ;
    RecyclerView popularRecyclerViewList, featuredList, recyclerView, electronicRcv , healthRcv;
    Context context;
    boolean isScrolling = false, isEnd = false;
    LinearLayoutManager manager;
    int currentPage = 1, currentItems, totalItems, scrollOutItems;
    ProductListDifferAdapter adapter;
    List<ProductModel> pList = new ArrayList<>();
    NestedScrollView nestedScrollView;
    ProgressBar proGressBar;
    HorizontralCateGoryAdapter.ItemClickListener categoryItemClickListener;
    List<CategoryResp> CateGoryResp = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        context = view.getContext();
        search = view.findViewById(R.id.edittext_search_searchitem);
        viewAllFeature = view.findViewById(R.id.textview_viewall_category);
        viewAllPopular = view.findViewById(R.id.textview_viewall_newarrival);
        popularRecyclerViewList = view.findViewById(R.id.recyclerview_new_arrival);
        featuredList = view.findViewById(R.id.recyclerview_featured);
        recyclerView = view.findViewById(R.id.recyclerview_Product);
       // recyclerView.setHasFixedSize(true);
        setUpSlider(view)  ;

        nestedScrollView = view.findViewById(R.id.nestedScrollView);
        AllProduct = view.findViewById(R.id.textview_viewall_all_product);
        electronicRcv =view.findViewById(R.id.recyclerview_electronics) ;
        AllElectronics = view.findViewById(R.id.textview_viewall_electronics) ;
        AllHealth = view.findViewById(R.id.textview_viewall_health)  ;
        healthRcv = view.findViewById(R.id.recyclerview_health) ;
        healthRcv.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , false));
        electronicRcv.setLayoutManager(new LinearLayoutManager(context , RecyclerView.HORIZONTAL , false));
        
        manager = new GridLayoutManager(context, 2);
        manager.setAutoMeasureEnabled(false);
        recyclerView.setLayoutManager(manager);
        proGressBar = view.findViewById(R.id.spin_kit);
        proGressBar.setVisibility(View.GONE);

        popularRecyclerViewList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        featuredList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // activity.changeThePage();
                ((Home_Activity) getActivity()).getViewPager();


            }
        });

        viewAllFeature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent p = new Intent(context, Category_List.class);
                startActivity(p);
            }
        });

        AllHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = "Health & Beauty";
                Intent p = new Intent(context, CategoryViewPage.class);
                p.putExtra("cat_name", name);
                p.putExtra("parent_id",  "330");
                startActivity(p);
            }
        });

        AllElectronics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = "Electronic Accessories";
                Intent p = new Intent(context, CategoryViewPage.class);
                p.putExtra("cat_name", name);
                p.putExtra("parent_id",  "440");
                startActivity(p);
            }
        });

        viewAllPopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(context, Top_Recent_List.class);
                p.putExtra("TYPE", "POPULAR");
                startActivity(p);
            }
        });

        AllProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(context, ProductList.class);
                p.putExtra("CATEGORY", "NULL");
                startActivity(p);
            }
        });

        categoryItemClickListener = new HorizontralCateGoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                String name = CateGoryResp.get(pos).getName();
                if (name.contains("amp;")) {

                    name = CateGoryResp.get(pos).getName().replace("amp;", "");

                }
                Intent p = new Intent(context, CategoryViewPage.class);
                p.putExtra("cat_name", name);
                p.putExtra("parent_id", CateGoryResp.get(pos).getId() + "");
                startActivity(p);
            }
        };

        adapter = new ProductListDifferAdapter(context, HomeFragment.this);
      //  adapter.setHasStableIds(true);
        recyclerView.setAdapter(adapter);
      //  adapter.submitList(pList);
        loadAllPopularProducts();
        loadAllCategory();
        loadAllElectronics() ;
        loadAllHealth() ;
        loadAllProducts(currentPage);
        initScrollListener();
        return view;
    }

    private void setUpSlider(View v) {
        SliderView sliderView;
        sliderView = v.findViewById(R.id.imageSlider);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.startAutoCycle();
        ArrayList<sliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new sliderItem(R.drawable.mobile_app_final_01));
        sliderItems.add(new sliderItem(R.drawable.mobile_app_final_02));
        sliderItems.add(new sliderItem(R.drawable.mobile_app_final_03));
        sliderItems.add(new sliderItem(R.drawable.mobile_app_final_04));
        sliderItems.add(new sliderItem(R.drawable.mobile_app_final_05));
        sliderItems.add(new sliderItem(R.drawable.mobile_app_final_06));

        SliderAdapterExample adapter = new SliderAdapterExample(context, sliderItems);
        sliderView.setSliderAdapter(adapter);

    }

    private void loadAllHealth() {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<ProductModel>> calla = RetrofitClient.getInstance()
                .getApi()
                .getAllProductViaCategory(authHeader, "330" , "15" ,1);
        calla.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                if(response.code()== 200){
                    // now load the data
                    List<ProductModel> productModelList = response.body();
                    //load the data
                    healthRcv.setAdapter(new HorizontralProductListAdapter(getContext(), productModelList, HomeFragment.this));

                }
                else {

                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toasty.error(context, "Something Went Wrong !! Msg : " + t.getMessage(), Toasty.LENGTH_LONG).show();

            }
        });
    }

    private void loadAllElectronics() {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
         Call<List<ProductModel>> call = RetrofitClient.getInstance()
                .getApi()
                .getAllProductViaCategory(authHeader, "440" , "15" ,1);
         call.enqueue(new Callback<List<ProductModel>>() {
             @Override
             public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                 if(response.code()== 200){
                     // now load the data
                     List<ProductModel> productModelList = response.body();
                     //load the data
                     electronicRcv.setAdapter(new HorizontralProductListAdapter(getContext(), productModelList, HomeFragment.this));

                 }
                 else {

                 }
             }

             @Override
             public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                 Toasty.error(context, "Something Went Wrong !! Msg : " + t.getMessage(), Toasty.LENGTH_LONG).show();

             }
         });
    }
    public void loadAllPopularProducts() {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<ProductModel>> popularProduct = RetrofitClient.getInstance()
                .getApi()
                .getAllFeaturedProducts(authHeader, 10);

        popularProduct.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.code() == 200) {
                    // now load the data
                    List<ProductModel> productModelList = response.body();
                    //load the data
                    popularRecyclerViewList.setAdapter(new HorizontralProductListAdapter(getContext(), productModelList, HomeFragment.this));

                } else {
                    Toasty.error(context, "Something Went Wrong !! Code : " + response.code(), Toasty.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                Toasty.error(context, "Something Went Wrong !! ", Toasty.LENGTH_LONG).show();
            }
        });

    }
    public void loadAllCategory() {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<CategoryResp>> popularProduct = RetrofitClient.getInstance()
                .getApi()
                .getAllParentCategory(authHeader);

        popularProduct.enqueue(new Callback<List<CategoryResp>>() {
            @Override
            public void onResponse(Call<List<CategoryResp>> call, Response<List<CategoryResp>> response) {
                if (response.code() == 200) {
                    // now load the data
                    CateGoryResp = response.body();
                    //load the data
                    featuredList.setAdapter(new HorizontralCateGoryAdapter(getContext(), CateGoryResp, categoryItemClickListener));

                } else {
                    Toasty.error(context, "Something Went Wrong !! Code : " + response.code(), Toasty.LENGTH_LONG).show();

                }
            }

            @Override
            public void onFailure(Call<List<CategoryResp>> call, Throwable t) {
                Toasty.error(context, "Something Went Wrong !! ", Toasty.LENGTH_LONG).show();
            }
        });

    }
    public void loadAllProducts(int page) {
        String order = "desc" ;
        if(page%2!=0){
            order = "asc" ;
        }
        else {
            order = "desc" ;
        }
        proGressBar.setVisibility(View.VISIBLE);
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<ProductModel>> popularProduct = RetrofitClient.getInstance()
                .getApi()
                .getAllProducts(authHeader, "24"  , page , "title" ,order );

        popularProduct.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.code() == 200) {
//                List<ProductModel> list =  new ArrayList<>() ;
//                list = response.body() ;
                    try {
                        if (response.code() == 200) {
                            int oldSize = pList.size();
                            // try for it
                            pList.addAll(response.body());

                            if (oldSize == pList.size()) {
                                isEnd = true;

                            } else {
                                isEnd = false;
                            }

                            getActivity().runOnUiThread(new Runnable() {

                                @Override
                                public void run() {

                                    // Stuff that updates the UI
                                  //  proGressBar.clearAnimation();
                                    proGressBar.setVisibility(View.GONE);
                                    adapter.submitlist(pList);
                                 //   Toasty.success(context, "Error : " + adapter.getItemCount(), 1).show();
                                   // adapter.notifyDataSetChanged();


                                }
                            });



                        } else {
                            proGressBar.setVisibility(View.GONE);
                            Toasty.error(context, "Error : " + response.code(), 1).show();
                            //progress.setVisibility(View.GONE);
                        }
                    } catch (Exception r) {
                        proGressBar.setVisibility(View.GONE);
                        Toasty.error(context, "Error :  Something Went Wrong !! ", 1).show();
                    }
                } else {
                    proGressBar.setVisibility(View.GONE);
                    Toasty.error(context, "Error :  Code " + response.code(), 1).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                proGressBar.setVisibility(View.GONE);
                Toasty.error(context, "Error : " + t.getMessage(), 1).show();
            }
        });


    }

    private void initScrollListener() {
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
//                    isScrolling = true;
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (dy > 0) { // scroll down
//                    currentItems = manager.getChildCount();
//                    totalItems = manager.getItemCount();
//                    scrollOutItems = manager.findFirstVisibleItemPosition();
//
//                    Toasty.warning(context, "d" , Toasty.LENGTH_LONG)
//                            .show();
//                    if (isScrolling && (currentItems + scrollOutItems == totalItems)) {
//                        isScrolling = false;
//                        Toasty.warning(context, "d" + currentPage, Toasty.LENGTH_LONG)
//                                .show();
//                        loadMore();
//                    }
//                }
//
//
//            }
//        });re

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
            Toasty.warning(context, "Your At The Last Page.", Toasty.LENGTH_SHORT)
                    .show();
        } else {
//            Toasty.warning(context, "Loading Start", Toasty.LENGTH_SHORT)
//                    .show();
            currentPage++;
            loadAllProducts(currentPage);
        }


    }

    @Override
    public void onItemClick(ProductModel model) {
        Intent p = new Intent(getContext(), ProductDetails.class);
        p.putExtra("MODEL", model);
        startActivity(p);
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            ((Home_Activity) getActivity()).setHeaderTitle("Discover");
        } catch (Exception e) {

        }
    }

    @Override
    public void onItemClick(ProductModel model, int id) {

        if (id == R.id.imageview_search_cart_fr) {
            try {
                ((Home_Activity) getActivity()).countCartItemNumber();
            } catch (Exception e) {

            }
        } else {
            Intent p = new Intent(getContext(), ProductDetails.class);
            p.putExtra("MODEL", model);
            startActivity(p);
        }
    }
}