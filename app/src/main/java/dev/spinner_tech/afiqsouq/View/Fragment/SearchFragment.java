package dev.spinner_tech.afiqsouq.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.ProductListAdapter;
import dev.spinner_tech.afiqsouq.Models.ProductModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.View.Activities.ProductDetails;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment implements ProductListAdapter.ItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
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
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    Context context;
    TextView itemCountTv;
    SearchView searchView;
    List<ProductModel> pList = new ArrayList<>();
    ProductListAdapter adapter  ;
    int currentPage = 1;
    boolean isScrolling = false, isEnd = false;
    SpinKitView progress;
    int currentItems, totalItems, scrollOutItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
        itemCountTv = view.findViewById(R.id.textview_search_amount_foundproduct);
        itemCountTv.setVisibility(View.INVISIBLE);
        searchView = view.findViewById(R.id.edittext_search_searchitem);
        progress = (SpinKitView) view.findViewById(R.id.spin_kit);
        context = view.getContext();
        adapter = new ProductListAdapter(context , pList , this ) ;
        // load view
        recyclerView = view.findViewById(R.id.list);
        manager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchProduct(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }

    private void searchProduct(String query) {
        progress.setVisibility(View.VISIBLE);
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);

        Call<List<ProductModel>> searchCall = RetrofitClient.getInstance()
                .getApi()
                .SearchProduct(
                        authHeader,
                        query
                );

        searchCall.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {

                if(response.code() == 200){

                    // try for it
                    pList.clear();
                    pList.addAll(response.body());
                    try{
                       itemCountTv.setVisibility(View.VISIBLE);
                       itemCountTv.setText(pList.size()+" products found");
                    }
                    catch (Exception e){

                    }
                    adapter.notifyDataSetChanged();
                    progress.setVisibility(View.GONE);

                }
                else {
                    progress.setVisibility(View.GONE);
                    Toasty.error(context, "Error" + response.code(), Toasty.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                progress.setVisibility(View.GONE);
                Toasty.error(context, "Error" + t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onItemClick(ProductModel model) {

        Intent p = new Intent(context, ProductDetails.class) ;
        p.putExtra("MODEL" , model) ;
        //Toasty.error(getApplicationContext(), "T " + model.getId(), 1).show();
        startActivity(p);

    }
}