package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.ReviewAdapter;
import dev.spinner_tech.afiqsouq.Models.ReviewResp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class reviewActivity extends AppCompatActivity {
    ProgressBar one_p_bar, two_p_bar, three_p_bar, four_p_bar, five_p_bar, total_rating;
    RatingBar ratingbar;
    TextView total_rating_tv, one_star_text, two_star_text, three_start_text, four_star_text, five_start_text, reviewText;
    RecyclerView recyclerView;
    ReviewAdapter.ItemClickListener itemClickListener;
    SpinKitView spinKitView;
    int pid ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        // getting product id
        pid = getIntent().getIntExtra("PID" , 0 ) ;
        // back - btn
        findViewById(R.id.backIB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        setupview();
        //  loadAllTheReview();
        findViewById(R.id.add_a_review_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(), Add_Review.class);
                p.putExtra("PID" , pid) ;
                startActivity(p);
            }
        });
    }

    private void setupview() {
        spinKitView = findViewById(R.id.spin_kit_review);
        ratingbar = findViewById(R.id.averageViewRatingBar);
        one_p_bar = findViewById(R.id.progressBar3);
        two_p_bar = findViewById(R.id.progressBar4);
        three_p_bar = findViewById(R.id.progressBar5);
        four_p_bar = findViewById(R.id.progressBar2);
        five_p_bar = findViewById(R.id.progressBar1);
        total_rating_tv = findViewById(R.id.averageRating_TV);
        one_star_text = findViewById(R.id.one_star_count_textview);
        two_star_text = findViewById(R.id.two_star_count_textview);
        three_start_text = findViewById(R.id.three_star_count_textview);
        four_star_text = findViewById(R.id.four_star_count_textview);
        five_start_text = findViewById(R.id.five_star_count_textview);
        reviewText = findViewById(R.id.total_review_count_TV);
        recyclerView = findViewById(R.id.reviews_RV);
        spinKitView.setVisibility(View.VISIBLE);
        // set up rcv
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void loadAllTheReview() {
        // here we get the list
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);

        Call<List<ReviewResp>> reviewCall = RetrofitClient.getInstance()
                .getApi()
                .getProductReview(authHeader, pid);

        reviewCall.enqueue(new Callback<List<ReviewResp>>() {
            @Override
            public void onResponse(Call<List<ReviewResp>> call, Response<List<ReviewResp>> response) {

                if (response.code() == 200) {
                    // we have the list here
                    List<ReviewResp> reviewRespList = response.body();

                    try {
                        if (reviewRespList.size() > 0) {

                            recyclerView.setAdapter(new ReviewAdapter(getApplicationContext(), reviewRespList, itemClickListener));
                            calculateReview(reviewRespList);
                            spinKitView.setVisibility(View.GONE);
                        }
                        else {
                            spinKitView.setVisibility(View.GONE);
                            Toasty.warning(getApplicationContext() , "There Is No Review On This Product, Feel Free To Add One !!" , 1 ).show();
                        }
                    } catch (Exception e) {

                    }


                } else {
                    Toasty.error(getApplicationContext(), "Error " + response.code(), 1).show();
                    spinKitView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<ReviewResp>> call, Throwable t) {
                Toasty.error(getApplicationContext(), "Error " + t.getMessage(), 1).show();
                spinKitView.setVisibility(View.GONE);
            }
        });
    }

    private void calculateReview(List<ReviewResp> reviewRespList) {
        // loop it for total review score
        int one = 0, two = 0, three = 0, four = 0, five = 0;
        float reviewTotal = 0, reviewPointAva = 0;
        int totalReviewer = reviewRespList.size();
        for (ReviewResp item : reviewRespList) {


            reviewTotal = reviewTotal + item.getRating();
            if (Math.round(item.getRating()) == 1) {
                one += 1;
            } else if (Math.round(item.getRating()) == 2) {
                two += 1;
            } else if (Math.round(item.getRating()) == 3) {
                three += 1;
            } else if (Math.round(item.getRating()) == 4) {
                four += 1;

            } else {
                five += 1;
            }


        }

        one_p_bar.setProgress(one);
        two_p_bar.setProgress(two);
        three_p_bar.setProgress(three);
        four_p_bar.setProgress(four);
        five_p_bar.setProgress(five);
        one_star_text.setText(one + "");
        two_star_text.setText(two + "");
        three_start_text.setText(three + "");
        four_star_text.setText(four + "");
        five_start_text.setText(five + "");

        reviewPointAva = reviewTotal / totalReviewer;
        total_rating_tv.setText(reviewPointAva + "");
        ratingbar.setRating(reviewPointAva);
        reviewText.setText("Based on " + totalReviewer + " reviews");


    }

    @Override
    protected void onResume() {
        loadAllTheReview();
        super.onResume();
    }
}