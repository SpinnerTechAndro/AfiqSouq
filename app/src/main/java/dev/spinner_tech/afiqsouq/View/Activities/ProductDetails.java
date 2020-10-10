package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.button.MaterialButton;

import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.ColorrAdapter;
import dev.spinner_tech.afiqsouq.Adapter.SizeAdapter;
import dev.spinner_tech.afiqsouq.Models.CartDbModel;
import dev.spinner_tech.afiqsouq.Models.ProductModel;
import dev.spinner_tech.afiqsouq.Models.VariationResp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.Utils.SharedPrefManager;
import dev.spinner_tech.afiqsouq.database.CartDatabase;
import dev.spinner_tech.afiqsouq.services.RetrofitClient;
import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetails extends AppCompatActivity {

    //toolbar & content
    ImageView back, enlarge, share, favourite, image1, image2, image3, main_image;
    TextView cart_number, status, price, S, M, XL, XXL, title;
    Button additional_info, about_brand, add_to_cart;
    ElegantNumberButton quantity;
    RelativeLayout dashboard;
    ProductModel model;
    ProductModel.Image image;
    CardView cardImage1, cardImage2, cardImage3;
    List<ProductModel.Image> imageList = new ArrayList<>();
    List<String> sizeList = new ArrayList<>();
    List<String> ColorList = new ArrayList<>();
    List<ProductModel.Attribute> attributeList = new ArrayList<>();
    List<VariationResp> variationRespList = new ArrayList<>();
    HtmlTextView desc;
    LinearLayout colorCon, sizeCon;
    RecyclerView ColorRList, SizeRList;
    SizeAdapter sizeAdapter;
    ColorrAdapter colorrAdapter;
    MaterialButton addToCart, review;
    int variationId = 0;
    Boolean isColor = false, isSize = false;
    CartDatabase database;
    String imageLink = "nulll";
    ImageView cartImage;
    AlertDialog alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
//recent_product_list
        database = Room.databaseBuilder(getApplicationContext(),
                CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
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
        main_image = findViewById(R.id.main_image);
        cartImage = findViewById(R.id.imageView_discover_cart);

        desc = findViewById(R.id.Textview_productDetail_status);
        additional_info = findViewById(R.id.Button_productDetail_additionalInfo);
        review = findViewById(R.id.Button_productDetail_review);
        add_to_cart = findViewById(R.id.button_productDetail_addToCart);


        // find Id
        colorCon = findViewById(R.id.colorContainer);
        sizeCon = findViewById(R.id.sizeContainer);
        ColorRList = findViewById(R.id.colorList);
        SizeRList = findViewById(R.id.size_list);
//        about_brand = findViewById(R.id.Button_productDetail_aboutBrand);

        price = findViewById(R.id.Textview_productDetail_price);

        add_to_cart = findViewById(R.id.button_productDetail_addToCart);


        title.setText(model.getName());

        String sale_price = model.getSalePrice();
        if (sale_price.equals("") || sale_price.isEmpty()) {
            price.setText("৳" + model.getPrice());
        } else {
            price.setText("৳" + model.getSalePrice());
        }


        desc.setHtml(model.getShortDescription());
        imageList = model.getImages();
        if (imageList.size() > 0) {
            try {
                Glide.with(getApplicationContext())
                        .load(imageList.get(0).getSrc())
                        .into(main_image);
            } catch (Exception e) {

            }
        }
        // getting attributes list

        try {
            attributeList = model.getAttributes();
            if (attributeList.size() > 0) {

                for (ProductModel.Attribute item : attributeList) {
                    if (item.getName().equals("Size")) {
                        sizeList = item.getOptions();
                    } else if (item.getName().equals("Color")) {
                        ColorList = item.getOptions();

                    }

                }
            } else {
                sizeCon.setVisibility(View.GONE);
                colorCon.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            Toasty.error(getApplicationContext(), e.getMessage() + " ", 1).show();
            sizeCon.setVisibility(View.GONE);
            colorCon.setVisibility(View.GONE);
        }

        setUpImage(imageList);
        setUpSize_ColorConstiner(sizeList, ColorList);
        allClickListenter();

        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // got oto review page
                Intent p = new Intent(getApplicationContext(), reviewActivity.class);
                p.putExtra("PID", model.getId());
                startActivity(p);
            }
        });

    }

    private void setUpSize_ColorConstiner(List<String> sizeList, List<String> colorList) {

        // check they arr empt
        if (sizeList.size() == 0) {
            sizeCon.setVisibility(View.GONE);

        } else {
            // set adapter
            SizeRList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            sizeAdapter = new SizeAdapter(sizeList, getApplicationContext());

            SizeRList.setAdapter(sizeAdapter);

        }
        if (colorList.size() == 0) {
            colorCon.setVisibility(View.GONE);
        } else {
            ColorRList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            colorrAdapter = new ColorrAdapter(colorList, getApplicationContext());
            ColorRList.setAdapter(colorrAdapter);
        }

    }

    private void allClickListenter() {

        cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent p = new Intent(getApplicationContext(), CartListPage.class);
                startActivity(p);
            }
        });

        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!SharedPrefManager.getInstance(getApplicationContext()).isUserLoggedIn()) {
                    TriggerDilogue();
                } else {
                    try {
                        // size
                        int variationSize = variationRespList.size();
                        if (variationSize == 0) {
                            // dont have any variation
                            insertIntoCart(model);
                        //    Toasty.success(getApplicationContext(), "Variation ID : " + variationId, Toasty.LENGTH_LONG).show();

                        } else {

                            Boolean isSingleSize  = false , isSingleColor = false ;
                            String size, color;

                            try {
                                size = sizeAdapter.getSizeName().toLowerCase();
                            } catch (Exception e) {
                                size = "-no_Size";
                            }
                            try {
                                color = colorrAdapter.getColorName().toLowerCase();
                            } catch (Exception e) {
                                color = "-no_Color";
                            }
                            if(color.equals("-no_Color"))
                            {
                                isSingleColor = true ;
                            }
                            if(size.equals("-no_Size"))
                            {
                                isSingleSize = true ;

                            }
                            // loop through the varitaion list to get all the
                            //  List<VariationResp.Attribute> attributeList = variationRespList.;
                            for (VariationResp item : variationRespList) {

                                // now check which attribute has all of the color and size
                                List<VariationResp.Attribute> attributes = item.getAttributes();
                                // now cross check this
                                for (VariationResp.Attribute i : attributes) {

                                    //Log.d("TAG", "onClick: " +i.getName().toLowerCase() + " "+ i.getOption().toLowerCase());
                                        if (i.getName().toLowerCase().equals("color")) {

                                        //  Log.d("TAG", "onClick: " +color + " "+ size);

                                        if (i.getOption().toLowerCase().equals(color)) {
                                            Log.d("TAGE", "onClick: " + i.getName().toLowerCase() + " " + i.getOption().toLowerCase());

                                            isColor = true;
                                        } else isColor = false;

                                        // Log.d("TAG", "onClick:  color match " );
                                    }
                                        else if (i.getName().toLowerCase().equals("size")) {
                                        // Log.d("TAG", "onClick: " +i.getName().toLowerCase() + " "+ i.getOption().toLowerCase());
                                        // Log.d("TAG", "onClick: " +color + " "+ size);
                                        if (i.getOption().toLowerCase().equals(size)) {
                                            isSize = true;
                                            Log.d("TAGE", "onClick: " + i.getName().toLowerCase() + " " + i.getOption().toLowerCase());

                                        } else isSize = false;

                                        //  Log.d("TAG", "onClick:  size match " );
                                    }


                                }

                                if (isSize && isColor) {
                                    variationId = item.getId();
                                    Log.d("TAG", "onClick:  size & color match "  + variationId);
                                   break;
                                }
                                else {

                                    if(isSingleSize){ // size does not exist
                                        if(isColor){
                                            variationId = item.getId();
                                            Log.d("TAG", "onClick:  color match "  + variationId);
                                            break;
                                        }
                                    }
                                    else if(isSingleColor){
                                        // color does not exist
                                        if(isSize){
                                            variationId = item.getId();
                                            Log.d("TAG", "onClick:  size match "  + variationId);
                                            break;
                                        }
                                    }


                                }

                            }

                            //  Toasty.success(getApplicationContext(), "Variation ID : " + variationId, Toasty.LENGTH_LONG).show();
                            insertIntoCart(model);
                        }
                    } catch (Exception e) {
                        //insertIntoCart(model);
                        Toasty.error(getApplicationContext(), "Something Went Wrong " + e.getMessage(), 1).show();
                    }
                }


            }
        });

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Glide.with(getApplicationContext())
                            .load(imageList.get(0).getSrc())
                            .into(main_image);
                } catch (Exception e) {

                }
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Glide.with(getApplicationContext())
                            .load(imageList.get(1).getSrc())
                            .into(main_image);
                } catch (Exception e) {

                }
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Glide.with(getApplicationContext())
                            .load(imageList.get(2).getSrc())
                            .into(main_image);
                } catch (Exception e) {

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

    @Override
    protected void onStart() {
        super.onStart();
        dowloadVariationList(model.getId());
    }

    private void dowloadVariationList(Integer id) {
        String authHeader = "Basic " + Base64.encodeToString(Constants.BASE.getBytes(), Base64.NO_WRAP);
        Call<List<VariationResp>> cal = RetrofitClient.getInstance()
                .getApi()
                .ProductVariationList(authHeader, id + "");


        cal.enqueue(new Callback<List<VariationResp>>() {
            @Override
            public void onResponse(Call<List<VariationResp>> call, Response<List<VariationResp>> response) {

                if (response.code() == 200) {
                    variationRespList = response.body();
                } else {
                    Toasty.error(getApplicationContext(), response.code() + " error ", Toasty.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<VariationResp>> call, Throwable t) {
                Toasty.error(getApplicationContext(), t.getMessage(), Toasty.LENGTH_LONG).show();
            }
        });
    }

    private void setUpImage(List<ProductModel.Image> imageList) {
        int size = imageList.size();
        if (size > 0) {
            // populate the image list
            if (size == 1) {
                imageLink = imageList.get(0).getSrc();
                Glide.with(ProductDetails.this)
                        .load(imageList.get(0).getSrc())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(image1);

                cardImage2.setVisibility(View.GONE);
                cardImage3.setVisibility(View.GONE);

            } else if (size == 2) {
                imageLink = imageList.get(0).getSrc();
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
                imageLink = imageList.get(0).getSrc();
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

            } else {
                imageLink = imageList.get(0).getSrc();
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

    @SuppressLint("StaticFieldLeak")
    private void insertIntoCart(ProductModel singelProduct) {

        CartDbModel cartDbModel = new CartDbModel();

        cartDbModel.title = singelProduct.getName();
        String sale_price = singelProduct.getSalePrice();
        double price = 0.0;

        if (sale_price.equals("") || sale_price.isEmpty()) {
            sale_price = model.getPrice();
        }
        price = Double.parseDouble(sale_price);
        cartDbModel.unit_price = Double.parseDouble(price + "");
        cartDbModel.qty = 1;
        cartDbModel.product_image = imageLink;
        cartDbModel.product_id = singelProduct.getId();
        cartDbModel.color = "NULL";
        cartDbModel.size = "NULL";
        cartDbModel.variation_id = variationId;
        cartDbModel.sub_total = (double) (price * 1);
        //  cartDbModel.customer_id = SharedPrefManager.getInstance(getApplicationContext()).getUser().getCustomerId();


//        Now Insert it

//        new AsyncTask<CartDbModel, Void, Long>() {
//
//            @Override
//            protected Long doInBackground(CartDbModel... params) {
//                return database.dao().insertCartItem(params[0]);
//
//            }
//
//            @Override
//            protected void onPostExecute(Long id) {
//                super.onPostExecute(id);
//            Toasty.success(getApplicationContext() , "Added To Cart" , Toasty.LENGTH_LONG).show();
//            countCartItem();
//            }
//        }.execute(cartDbModel);

        CartDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                CartDatabase.getDatabase(getApplicationContext()).dao().insertCartItem(cartDbModel);

                Intent p = new Intent(getApplicationContext(), CartListPage.class);
                startActivity(p);
            }
        });


        Toasty.success(getApplicationContext(), "Added To Cart", Toasty.LENGTH_LONG).show();
        countCartItem();


    }

    private void countCartItem() {

        TextView mybag_item_count_tv;
        mybag_item_count_tv = findViewById(R.id.textview_discover_cartNumber);

//        new AsyncTask<String, Void, List<CartDbModel>>() {
//            @Override
//            protected List<CartDbModel> doInBackground(String... params) {
//                return database.dao().fetchAllTodos();
//            }
//
//            @SuppressLint("SetTextI18n")
//            @Override
//            protected void onPostExecute(List<CartDbModel> todoList) {
//
//
//
//
//            }
//        }.execute();

        CartDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {

                List<CartDbModel> list = CartDatabase.getDatabase(getApplicationContext()).dao().fetchAllTodos();
                if (list != null && !list.isEmpty()) // i know its werid but thats r8 cheaking list is popluted
                {

                    mybag_item_count_tv.setText(list.size() + "");
                } else {
                    // show  empty layout

                    mybag_item_count_tv.setText("0");
                }

            }
        });

    }

    private void TriggerDilogue() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetails.this);
        builder.setMessage("You Are Not Logged In !!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                        try {
                            alert.dismiss();
                            finish();
                        } catch (Exception r) {
                            finish();
                        }
                    }
                });
        alert = builder.create();
        alert.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        countCartItem();
    }
}