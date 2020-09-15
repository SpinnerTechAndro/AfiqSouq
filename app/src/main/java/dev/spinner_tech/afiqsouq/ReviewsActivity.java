package dev.spinner_tech.afiqsouq;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import meow.bottomnavigation.MeowBottomNavigation;

public class ReviewsActivity extends AppCompatActivity {

    private ImageButton backBtn,cartIB;
    private TextView  countCartItemTV,averageRatingTV,totalReviewCountTV,fiveStarCountTV,fourStarCountTV,threeStarCountTV,twoStarCountTV,oneStarCountTV;
    private RatingBar averageRatingBar;
    private ProgressBar progressBar1,progressBar2,progressBar3,progressBar4,progressBar5;
    private Button addA_ReviewBtn;

    private RecyclerView reviewsRV;

    private MeowBottomNavigation bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        //imageButton
        backBtn=findViewById(R.id.backIB);
        cartIB=findViewById(R.id.addTocartIB);

        //textView
        countCartItemTV=findViewById(R.id.countCartItemTV);
        averageRatingTV=findViewById(R.id.averageRating_TV);
        totalReviewCountTV=findViewById(R.id.total_review_count_TV);
        fiveStarCountTV=findViewById(R.id.five_star_count_textview);
        fourStarCountTV=findViewById(R.id.four_star_count_textview);
        threeStarCountTV=findViewById(R.id.three_star_count_textview);
        twoStarCountTV=findViewById(R.id.two_star_count_textview);
        oneStarCountTV=findViewById(R.id.one_star_count_textview);

        //ratingBar
        averageRatingBar=findViewById(R.id.averageViewRatingBar);


        //progressBar
        progressBar1=findViewById(R.id.progressBar1);
        progressBar2=findViewById(R.id.progressBar2);
        progressBar3=findViewById(R.id.progressBar3);
        progressBar4=findViewById(R.id.progressBar4);
        progressBar5=findViewById(R.id.progressBar5);

        //recyclerView
        reviewsRV=findViewById(R.id.reviews_RV);

        //button
        addA_ReviewBtn=findViewById(R.id.add_a_review_btn);

        //navigationBottom
        bottomNavigationView=findViewById(R.id.bottomNavigation);

    }
}