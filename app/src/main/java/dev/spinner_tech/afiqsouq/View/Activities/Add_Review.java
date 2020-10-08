package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import dev.spinner_tech.afiqsouq.Models.CreateReviewRep;
import dev.spinner_tech.afiqsouq.Models.PrefUserModel;
import dev.spinner_tech.afiqsouq.Models.ReviewResp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.Utils.SharedPrefManager;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Add_Review extends AppCompatActivity {

    RatingBar ratingBar;
    EditText review;
    TextView name, email;
    String reviewStr ;
    float ratting = 0 ;
    int pid = 0  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__review);
        ratingBar = findViewById(R.id.rating);
        review = findViewById(R.id.review_et);
        name = findViewById(R.id.name_et);
        email = findViewById(R.id.email_et);

        // getting product id
        pid = getIntent().getIntExtra("PID" , 0 ) ;

        PrefUserModel model = SharedPrefManager.getInstance(getApplicationContext())
                .getUser() ;

        email.setText(model.getMail());
        name.setText(model.getName());

        findViewById(R.id.submit_Btn)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        reviewStr = review.getText().toString() ;
                        if (TextUtils.isEmpty(reviewStr)){
                            Toasty.error(getApplicationContext() , "Please Give A REview " , 1 ).show();
                        }
                        else {
                            uploadTheReview() ;
                        }
                    }
                });

        findViewById(R.id.backIB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar arg0, float rateValue, boolean arg2) {
                // TODO Auto-generated method stub
                ratting = rateValue ;
            }
        });

    }

    private void uploadTheReview() {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);

        ratting = ratingBar.getRating() ;

        PrefUserModel model = SharedPrefManager.getInstance(getApplicationContext())
                .getUser() ;

        CreateReviewRep ReviewModel  = new CreateReviewRep(pid  , reviewStr ,  model.getName(), model.getMail(),Math.round(ratting)) ;

        Call<ReviewResp> call = RetrofitClient.getInstance()
                .getApi()
                .CreateReviews(authHeader  , ReviewModel) ;

        call.enqueue(new Callback<ReviewResp>() {
            @Override
            public void onResponse(Call<ReviewResp> call, Response<ReviewResp> response) {

                if(response.code() == 201 ){
                    finish();
                }
                else {
                    Toasty.error(getApplicationContext(), "Try Again " + response.code() ,1 ).show();
                }
            }

            @Override
            public void onFailure(Call<ReviewResp> call, Throwable t) {
            Toasty.error(getApplicationContext(), "Try Again " + t.getMessage() ,1 ).show();
            }
        });

    }
}