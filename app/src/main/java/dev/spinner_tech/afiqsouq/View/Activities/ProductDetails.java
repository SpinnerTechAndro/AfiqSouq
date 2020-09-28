package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Models.ProductModel;
import dev.spinner_tech.afiqsouq.R;
import es.dmoral.toasty.Toasty;

public class ProductDetails extends AppCompatActivity {

    //toolbar & content
    ImageView back, enlarge, share, favourite, image1, image2, image3 , main_image;
    TextView cart_number, status, price, S, M, XL, XXL, title;
    Button additional_info, review, about_brand, add_to_cart;
    ElegantNumberButton quantity;
    RelativeLayout dashboard;
    ProductModel model;
    ProductModel.Image image;
    CardView cardImage1, cardImage2, cardImage3;
    List<ProductModel.Image> imageList = new ArrayList<>( ) ;
    HtmlTextView  desc ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        // get the product model
        model = (ProductModel) getIntent().getSerializableExtra("MODEL");
        if (model != null) {
            setupView(model);
        }


    }

    private void setupView(ProductModel model) {
        //toolbar
        back = findViewById(R.id.backBtn);
//        cart_number = findViewById(R.id.textview_productDetail_cartNumber);
        title = findViewById(R.id.header_title);

        //dashboard
        dashboard = findViewById(R.id.Relativelayout_productdetail_dashboard);
//        enlarge = findViewById(R.id.Imageview_productdetail_enlarge);
//        share = findViewById(R.id.Imageview_productdetail_share);
//        favourite = findViewById(R.id.Imageview_productdetail_favourite);
        image1 = findViewById(R.id.Imageview_productdetail_image1);
        image2 = findViewById(R.id.Imageview_productdetail_image2);
        image3 = findViewById(R.id.Imageview_productdetail_image3);
        cardImage1 = findViewById(R.id.image1_container);
        cardImage2 = findViewById(R.id.image2_container);
        cardImage3 = findViewById(R.id.image3_container);
        main_image = findViewById(R.id.main_image) ;
        desc = findViewById(R.id.Textview_productDetail_status);
        additional_info = findViewById(R.id.Button_productDetail_additionalInfo);
        review = findViewById(R.id.Button_productDetail_review);
//        about_brand = findViewById(R.id.Button_productDetail_aboutBrand);

        price = findViewById(R.id.Textview_productDetail_price);

        add_to_cart = findViewById(R.id.button_productDetail_addToCart);


        title.setText(model.getName());
        price.setText("৳"+model.getSalePrice());


        desc.setHtml(model.getShortDescription());
        imageList = model.getImages();
        if(imageList.size()>0){
            try{
                Glide.with(getApplicationContext())
                        .load(imageList.get(0).getSrc())
                        .into(main_image);
            }
            catch (Exception e ){

            }
        }
        setUpImage(imageList);
        allClickListenter() ;

    }

    private void allClickListenter() {

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    Glide.with(getApplicationContext())
                            .load(imageList.get(0).getSrc())
                            .into(main_image);
                }
                catch (Exception e ){

                }
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Glide.with(getApplicationContext())
                            .load(imageList.get(1).getSrc())
                            .into(main_image);
                }
                catch (Exception e ){

                }
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Glide.with(getApplicationContext())
                            .load(imageList.get(2).getSrc())
                            .into(main_image);
                }
                catch (Exception e ){

                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setUpImage(List<ProductModel.Image> imageList) {
        int size = imageList.size();
        if (size > 0) {
            // populate the image list
            if (size == 1) {
                Glide.with(ProductDetails.this)
                        .load(imageList.get(0).getSrc())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image1);

                cardImage2.setVisibility(View.GONE);
                cardImage3.setVisibility(View.GONE);

            } else if (size == 2) {
                Glide.with(ProductDetails.this)
                        .load(imageList.get(0).getSrc())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image1);
                Glide.with(ProductDetails.this)
                        .load(imageList.get(1).getSrc())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image2);

                cardImage3.setVisibility(View.GONE);
            } else if (size == 3) {
                Glide.with(ProductDetails.this)
                        .load(imageList.get(0).getSrc())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image1);
                Glide.with(ProductDetails.this)
                        .load(imageList.get(1).getSrc())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image2);
                Glide.with(ProductDetails.this)
                        .load(imageList.get(2).getSrc())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image3);

            }
            else {
                Glide.with(ProductDetails.this)
                        .load(imageList.get(0).getSrc())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image1);
                Glide.with(ProductDetails.this)
                        .load(imageList.get(1).getSrc())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image2);
                Glide.with(ProductDetails.this)
                        .load(imageList.get(2).getSrc())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image3);

            }
        }
    }
}