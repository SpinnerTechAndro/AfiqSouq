package dev.mobile.afiqsouq.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.afiqsouq.Models.CartDbModel;
import dev.mobile.afiqsouq.Models.oldOrderModel;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;


public class OldCartListAdapter extends RecyclerView.Adapter<OldCartListAdapter.ViewHolder> {

    CartDbModel UpdateItem;
    private LayoutInflater mInflater;
    List<oldOrderModel.LineItem> cartList = new ArrayList<>();
    Context cartPage;
    double deliveryCharge, tax;


    public OldCartListAdapter(List<oldOrderModel.LineItem> cartList, Context context) {
        this.cartList = cartList;
        this.cartPage = context;
    }


    @Override
    public OldCartListAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.old_cart_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onBindViewHolder(OldCartListAdapter.ViewHolder holder, final int position) {

        if (position % 2 == 0) {

            holder.container.setBackgroundColor(Color.parseColor("#F8F9FF"));
        } else {
            holder.container.setBackgroundColor(Color.WHITE);
        }
        oldOrderModel.LineItem item = cartList.get(position);

        holder.textPrice.setText(Constants.BDT_SIGN + "." + item.getPrice() + "");
        holder.qtyTv.setText("Qty x" + item.getQuantity());

        holder.txtName.setText(item.getName());


    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView txtName;
        public TextView textPrice, qtyTv;
        public ConstraintLayout container;
        public ImageView productImage;


        public ViewHolder(View view) {
            super(view);

            // delteBtn = view.findViewById(R.id.cart_delete) ;
            txtName = view.findViewById(R.id.textView_shoppingcartfr_productName);
            textPrice = view.findViewById(R.id.textView_shoppingcartfr_price);
            qtyTv = view.findViewById(R.id.qty);
            //  cardView = view.findViewById(R.id.container);
            productImage = view.findViewById(R.id.cardview_shoppcartfr_productImage);
            container = view.findViewById(R.id.container);


        }
    }
}

