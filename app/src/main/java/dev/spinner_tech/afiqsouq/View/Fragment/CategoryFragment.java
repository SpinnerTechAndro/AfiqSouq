package dev.spinner_tech.afiqsouq.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.CateGoryAdapter;
import dev.spinner_tech.afiqsouq.Models.CategoryResp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.View.Activities.CategoryViewPage;
import dev.spinner_tech.afiqsouq.View.Home_Activity;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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

    View view ;
    Context context ;
    RecyclerView recyclerView;
    List<CategoryResp> categoryResp = new ArrayList<>();
    CateGoryAdapter.ItemClickListener itemClickListener;
    SpinKitView spinKitView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_category, container, false) ;
        context = view.getContext() ;
        // SET UPVIEW
        recyclerView = view.findViewById(R.id.list);
        spinKitView = view.findViewById(R.id.spin_kit22) ;

        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        loadCategoryList();

        itemClickListener = new CateGoryAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int pos) {
                String name = categoryResp.get(pos).getName();
                if (name.contains("amp;")) {

                    name = categoryResp.get(pos).getName().replace("amp;", "");

                }
                Intent p = new Intent(context, CategoryViewPage.class);
                p.putExtra("cat_name", name);
                p.putExtra("parent_id", categoryResp.get(pos).getId() + "");
                startActivity(p);

            }
        } ;

        return view;
    }


    private void loadCategoryList() {
        spinKitView.setVisibility(View.VISIBLE);
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<CategoryResp>> call = RetrofitClient.getInstance()
                .getApi()
                .getAllParentCategory(authHeader);

        call.enqueue(new Callback<List<CategoryResp>>() {
            @Override
            public void onResponse(Call<List<CategoryResp>> call, Response<List<CategoryResp>> response) {

                if (response.code() == 200) {
                    spinKitView.setVisibility(View.GONE);
                    // here get the list
                    categoryResp = response.body();

                    recyclerView.setAdapter(new CateGoryAdapter(context, categoryResp, itemClickListener));

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
                    spinKitView.setVisibility(View.GONE);
                    Toasty.error(context, "Error : " + response.code(), Toasty.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryResp>> call, Throwable t) {
                spinKitView.setVisibility(View.GONE);
                Toasty.error(context, "Error : " + t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();

        try {
            ((Home_Activity) getActivity()).setHeaderTitle("Category");
        } catch (Exception e) {

        }
    }
}