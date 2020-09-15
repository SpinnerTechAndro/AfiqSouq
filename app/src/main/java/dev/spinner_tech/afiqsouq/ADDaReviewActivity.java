package dev.spinner_tech.afiqsouq;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

public class ADDaReviewActivity extends AppCompatActivity {

    private ImageButton backIB,cartIB;
    private TextView addTocartCountTV;
    private RatingBar ratingBar;
    private EditText reviewET,nameET,emailET;
    private CheckBox saveNameEmailCB;
    private Button submitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_a_review);

        //imageButton
        backIB=findViewById(R.id.backIB);
        cartIB=findViewById(R.id.addTocartIB);

        //textview
        addTocartCountTV=findViewById(R.id.countCartItemTV);

        //ratingbar
        ratingBar=findViewById(R.id.rating);

        //edit text
        emailET=findViewById(R.id.email_et);
        nameET=findViewById(R.id.name_et);
        reviewET=findViewById(R.id.review_et);

        //chceck box
        saveNameEmailCB=findViewById(R.id.save_name_email_CB);

        //button
        submitBtn=findViewById(R.id.submit_Btn);





    }
}