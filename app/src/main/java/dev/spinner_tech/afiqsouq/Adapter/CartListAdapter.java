package dev.spinner_tech.afiqsouq.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Models.CartDbModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;
import dev.spinner_tech.afiqsouq.Utils.Utilities;
import dev.spinner_tech.afiqsouq.View.Activities.CartListPage;
import dev.spinner_tech.afiqsouq.database.CartDatabase;


public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {

    CartDbModel UpdateItem;
    private LayoutInflater mInflater;
    List<CartDbModel> cartList = new ArrayList<>();
    CartListPage cartPage;
    double deliveryCharge, tax;


    public CartListAdapter(List<CartDbModel> cartList, CartListPage context, double deliveryCharge, double tax) {
        this.cartList = cartList;
        this.cartPage = context;
        this.deliveryCharge = deliveryCharge;
        this.tax = tax;
    }


    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(CartListAdapter.ViewHolder holder, final int position) {

        if (position % 2 == 0) {

            holder.container.setBackgroundColor(Color.parseColor("#F8F9FF"));
        } else {
            holder.container.setBackgroundColor(Color.WHITE);
        }

        final CartDatabase database = Room.databaseBuilder(cartPage,
                CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        final CartDbModel cartItem = cartList.get(position);
        holder.txtName.setText(cartItem.title);
        holder.textPrice.setText("৳" + Math.round(cartItem.unit_price));
        holder.numberButton.setNumber(cartItem.qty + "");

        Glide.with(cartPage)
                .load(cartList.get(position).product_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.productImage);

        holder.numberButton.setOnValueChangeListener((view, oldValue, newValue) -> {

            // update the value .....

            UpdateItem = cartList.get(position);

            UpdateItem.qty = newValue;

            UpdateItem.sub_total = (double) (Math.round(cartItem.unit_price) * newValue);

            // UpdateItem.title = cartList.get(position).title ;
            //   holder.textPrice.setText(cartList.get(position).price * newValue + ""); // setting the item price in the row
            //UpdateItem.product_id = cartList.get(position).product_id ;
            //UpdateItem.cart_id = cartList.get(position).cart_id ;


//                new AsyncTask<CartDbModel, Void, Integer>() {
//                    @Override
//                    protected Integer doInBackground(CartDbModel... params) {
//                        return database.dao().updateCart(params[0]);
//                    }
//
//                    @Override
//                    protected void onPostExecute(Integer number) {
//                        super.onPostExecute(number);
//
//
//                    }
//                }.execute(UpdateItem);


            CartDatabase.databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    CartDatabase.getDatabase(cartPage).dao().updateCart(UpdateItem);
                }

            });


            Utilities utilities = new Utilities();
            double total = utilities.calculateTotal(cartList);
//                        cartPage.totalview.setText(utilities.calculateTotal(cartList)+ " .Tk");
//                        cartPage.totalPrice.setText(Math.round(utilities.calculateTotal(cartList) +deliveryCharge )  + " ");

            //calculate total tax


            cartPage.sub_total.setText(Constants.BDT_SIGN + total);
            DecimalFormat dec = new DecimalFormat("#0.0");
            cartPage.tax_fee.setText(Constants.BDT_SIGN + dec.format(total * (tax / 100)));
            total = total + ((total * (tax / 100)));
            Log.d("TAG", " adapter run: " + ((total * (tax / 100))));
            cartPage.total.setText(Constants.BDT_SIGN + (total + deliveryCharge));
            cartPage.paid.setText(Constants.BDT_SIGN + (total + deliveryCharge));


        });


        holder.delteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                CartDatabase.databaseWriteExecutor.execute(() ->
                        CartDatabase.getDatabase(cartPage).dao().deleteCartItem(cartItem)


                );

                cartList.remove(position);
                notifyItemChanged(position);
                notifyDataSetChanged();
                Utilities utilities = new Utilities();
                double total = utilities.calculateTotal(cartList);
//                        cartPage.totalview.setText(utilities.calculateTotal(cartList)+ " .Tk");
//                        cartPage.totalPrice.setText(Math.round(utilities.calculateTotal(cartList) +deliveryCharge )  + " ");

                cartPage.sub_total.setText(Constants.BDT_SIGN + total);
                DecimalFormat dec = new DecimalFormat("#0.0");
                cartPage.tax_fee.setText(Constants.BDT_SIGN + dec.format(total * (tax / 100)));
                total = total + ((total * (tax / 100)));
                cartPage.total.setText(Constants.BDT_SIGN + (total + deliveryCharge));
                cartPage.paid.setText(Constants.BDT_SIGN + (total + deliveryCharge));
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView textPrice;
        public ConstraintLayout container;
        public ElegantNumberButton numberButton;
        public ImageView delteBtn;
        public ImageView productImage;


        public ViewHolder(View view) {
            super(view);

            // delteBtn = view.findViewById(R.id.cart_delete) ;
            txtName = view.findViewById(R.id.textView_shoppingcartfr_productName);
            textPrice = view.findViewById(R.id.textView_shoppingcartfr_price);
            numberButton = view.findViewById(R.id.elegantNumberButton_shoppingcartfr_quantity);
            //  cardView = view.findViewById(R.id.container);
            productImage = view.findViewById(R.id.cardview_shoppcartfr_productImage);
            delteBtn = view.findViewById(R.id.Imageview_shoppingcartfr_cancel);
            container = view.findViewById(R.id.container);


        }
    }
}

