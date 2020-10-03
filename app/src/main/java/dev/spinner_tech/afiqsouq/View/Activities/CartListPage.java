package dev.spinner_tech.afiqsouq.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Adapter.CartListAdapter;
import dev.spinner_tech.afiqsouq.Models.CartDbModel;
import dev.spinner_tech.afiqsouq.Models.TaxREsp;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.Utils.SharedPrefManager;
import dev.spinner_tech.afiqsouq.database.CartDatabase;
import es.dmoral.toasty.Toasty;

public class CartListPage extends AppCompatActivity {


    public TextView cartNumber , tax_fee, coupon_no, sub_total, shipping_fee, total, discount, paid, product_name, product_price;
    Button checkoutout, apply_couppon;
    RecyclerView rv_shoppingCart;
    List<CartDbModel> cartList = new ArrayList<>();
    List<CartDbModel> orderList = new ArrayList<>();
    CartListAdapter adapter;
    double toatalAmount;
    double rate = 0.0  ;
    DecimalFormat dec = new DecimalFormat("#0.0");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);
        //content
        //coupon apply
        coupon_no = findViewById(R.id.textview_shoppingcart_couponNo);
        apply_couppon = findViewById(R.id.button_shoppingcart_applyCoupon);

        rv_shoppingCart = findViewById(R.id.recyclerview_shoppingcart_cartItem); //shows cart item

        sub_total = findViewById(R.id.textview_shoppingcart_subtotal_amount);
        tax_fee = findViewById(R.id.textview_shoppingcart_shippingfee_amount);
        total = findViewById(R.id.textview_shoppingcart_total_amount);
        discount = findViewById(R.id.textview_shoppingcart_discount_amount);
        paid = findViewById(R.id.textview_shoppingcart_tobe_paid_amount);
        checkoutout = findViewById(R.id.button_shoppingcart_checkoutd);
        rate = loadTaxFormCache();
        rv_shoppingCart.setLayoutManager(new LinearLayoutManager(this));


        loadAllCartItem();


    }


    private void loadAllCartItem() {

        countCartItem();
        CartDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                cartList = CartDatabase.getDatabase(getApplicationContext()).dao().fetchAllTodos();


                if (cartList != null && !cartList.isEmpty()) // i know its werid but thats r8 cheaking list is popluted
                {

                    orderList.clear();
                    orderList.addAll(cartList);
                    adapter = new CartListAdapter(cartList, CartListPage.this, 0, rate );
                    rv_shoppingCart.setAdapter(adapter);

                    // total
                    double totalMoney = calculateTotal(cartList ) ;
                    //setting sub amount
                    sub_total.setText(Constants.BDT_SIGN + Math.round(totalMoney) );

                    Log.d("TAG", "run: "+ "totall" + totalMoney + ((totalMoney*(rate/100)))) ;
                    // calculating tax

                    tax_fee.setText(Constants.BDT_SIGN + dec.format(totalMoney*(rate/100))) ;
                    totalMoney = totalMoney + ((totalMoney*(rate/100)))   ;
                    total.setText(Constants.BDT_SIGN + Math.round( totalMoney+ 0) );
                    paid.setText(Constants.BDT_SIGN + Math.round(totalMoney + 0) );

                } else {
                    // show  empty layout


                }
            }
        });


    }

    private double calculateTotal(List<CartDbModel> todoList) {
        toatalAmount = 0;
        for (CartDbModel item : todoList) {
            toatalAmount = toatalAmount + (item.unit_price * item.qty);
        }
        return toatalAmount;

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

//                    mybag_item_count_tv.setText(list.size() + "");
                } else {
                    // show  empty layout

//                    mybag_item_count_tv.setText("0");
                }

                Log.d("TAG", list.size() + "");

            }
        });


    }



    public  Double loadTaxFormCache(){
        String country  =   SharedPrefManager.getInstance(getApplicationContext())
                .getUser().getCountry().toLowerCase();
        String rate = "0.00" ;
        SharedPreferences shref;
        SharedPreferences.Editor editor;
        shref = getSharedPreferences("TAX", MODE_PRIVATE);
        String key = "TAX_KEY";
        Gson gson = new Gson();
        String response=shref.getString(key , "");
        List<TaxREsp> lstArrayList = gson.fromJson(response,
                new TypeToken<List<TaxREsp>>(){}.getType());

        assert lstArrayList != null;
        for(TaxREsp item : lstArrayList){

            if(item.getCountry().toLowerCase().equals(country) ){

                rate = item.getRate();
                break;
            }

       }

        return Double.parseDouble(rate);

    }
}