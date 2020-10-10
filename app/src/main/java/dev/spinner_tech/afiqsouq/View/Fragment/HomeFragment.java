package dev.spinner_tech.afiqsouq.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.HorizontralProductListAdapter;
import dev.spinner_tech.afiqsouq.Adapter.ProductListAdapter;
import dev.spinner_tech.afiqsouq.Models.ProductModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.View.Activities.ProductDetails;
import dev.spinner_tech.afiqsouq.View.Activities.ProductList;
import dev.spinner_tech.afiqsouq.View.Activities.Top_Recent_List;
import dev.spinner_tech.afiqsouq.View.Home_Activity;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements HorizontralProductListAdapter.ItemClickListener {

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
    TextView search, viewAllFeature, viewAllRecent;
    RecyclerView popularRecyclerViewList, featuredList;
    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        search = view.findViewById(R.id.edittext_search_searchitem);
        viewAllFeature = view.findViewById(R.id.textview_viewall_featured);
        viewAllRecent = view.findViewById(R.id.textview_viewall_newarrival);
        popularRecyclerViewList = view.findViewById(R.id.recyclerview_new_arrival);
        featuredList = view.findViewById(R.id.recyclerview_featured);
        popularRecyclerViewList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        featuredList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        context = view.getContext();

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

                Intent p = new Intent(context, Top_Recent_List.class);
                p.putExtra("TYPE", "TOP");
                startActivity(p);
            }
        });

        viewAllRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(context, Top_Recent_List.class);
                p.putExtra("TYPE", "RECENT");
                startActivity(p);
            }
        });

        loadAllPopularProducts();
        loadFeatureProducts();
        return view;
    }

    public void loadAllPopularProducts() {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<ProductModel>> popularProduct = RetrofitClient.getInstance()
                .getApi()
                .getAllRecentProducts(authHeader, 10);

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

    public void loadFeatureProducts() {
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
                    featuredList.setAdapter(new HorizontralProductListAdapter(getContext(), productModelList, HomeFragment.this));

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

    @Override
    public void onItemClick(ProductModel model) {
        Intent p = new Intent(getContext(), ProductDetails.class);
        p.putExtra("MODEL", model);
        startActivity(p);
    }
}