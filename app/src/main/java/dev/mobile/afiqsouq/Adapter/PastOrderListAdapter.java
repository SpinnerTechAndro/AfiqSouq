package dev.mobile.afiqsouq.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import dev.mobile.afiqsouq.Models.CartModel;
import dev.mobile.afiqsouq.Models.oldOrderModel;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;

public class PastOrderListAdapter extends RecyclerView.Adapter<PastOrderListAdapter.ViewHolder> {

    private List<oldOrderModel> orders = new ArrayList<>();
    List<CartModel> students = new ArrayList<>();
    private Context context;
    private PastOrderListAdapter.ItemClickListener itemClickListener;
    CartModel student;

    public PastOrderListAdapter(List<oldOrderModel> orders, Context context , PastOrderListAdapter.ItemClickListener itemClickListener) {
        this.orders = orders;
        this.context = context;
        this.itemClickListener = itemClickListener ;
    }

    @NonNull
    @Override
    public PastOrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_past_order, parent, false);
        PastOrderListAdapter.ViewHolder viewHolder = new PastOrderListAdapter.ViewHolder(view);
        return viewHolder;
    }

    public interface ItemClickListener {
        void onItemClick(oldOrderModel model);
    }


    @Override
    public void onBindViewHolder(@NonNull PastOrderListAdapter.ViewHolder holder, final int position) {

        oldOrderModel item = orders.get(position);


        holder.orderID.setText("Order Id : app_" + item.getId());
        holder.status.setText(item.getStatus());
        holder.textPrice.setText(Constants.BDT_SIGN + " " + item.getTotal() + "");

        try {
            String date = item.getDateCreated();
            String[] arrOfStr = date.split("T");
            Log.d("TAG", "onBindViewHolder: " + date.indexOf("T"));

            holder.date.setText("Placed On : " + arrOfStr[0]);
        } catch (Exception e) {
            holder.date.setText("Placed On : ");
        }
        try {
            List<oldOrderModel.LineItem> itemList = item.getLineItems();
            holder.oriderCount.setText("" + itemList.size());


        } catch (Exception e) {
            holder.oriderCount.setText("N/A");
            Log.d("TAG", "onBindViewHolder: " + e.getMessage() + "\n");
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldOrderModel model = orders.get(position);
                itemClickListener.onItemClick(model);
            }
        });


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView orderID;
        public TextView textPrice;
        public TextView status;
        public TextView oriderCount;
        public TextView date;


        public ViewHolder(View view) {
            super(view);

            orderID = view.findViewById(R.id.order_id);
            status = view.findViewById(R.id.status);
            textPrice = view.findViewById(R.id.price);
            oriderCount = view.findViewById(R.id.count);
            date = view.findViewById(R.id.date);


        }
    }
}
