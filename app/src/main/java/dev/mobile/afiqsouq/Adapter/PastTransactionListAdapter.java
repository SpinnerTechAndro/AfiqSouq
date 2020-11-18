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

public class PastTransactionListAdapter extends RecyclerView.Adapter<PastTransactionListAdapter.ViewHolder> {

    private List<oldOrderModel.LineItem> orders = new ArrayList<>();
    List<CartModel> students = new ArrayList<>();
    List<String> dateList = new ArrayList<>() ;
    private Context context;
    private PastTransactionListAdapter.ItemClickListener itemClickListener;
    CartModel student;

    public PastTransactionListAdapter(List<oldOrderModel.LineItem> orders, List<String> dateList, Context context, ItemClickListener itemClickListener) {
        this.orders = orders;
        this.dateList = dateList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PastTransactionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_spending_row_layout, parent, false);
        PastTransactionListAdapter.ViewHolder viewHolder = new PastTransactionListAdapter.ViewHolder(view);
        return viewHolder;
    }

    public interface ItemClickListener {
        void onItemClick(oldOrderModel model);
    }


    @Override
    public void onBindViewHolder(@NonNull PastTransactionListAdapter.ViewHolder holder, final int position) {

        oldOrderModel.LineItem item = orders.get(position);
        String date =dateList.get(position) ;


        holder.textPrice.setText("-"+Constants.BDT_SIGN+" "  + item.getTotal() + "");
        holder.title.setText(item.getName() + "\n  X"  +item.getQuantity() );


        try {

            String[] arrOfStr = date.split("T");
            Log.d("TAG", "onBindViewHolder: " + date.indexOf("T"));

            holder.date.setText("Placed On : " + arrOfStr[0]);
        } catch (Exception e) {
            holder.date.setText("Placed On : ");
        }


//        try {
//            List<oldOrderModel.LineItem> itemList = item.getLineItems();
//           // holder.oriderCount.setText("" + itemList.size());
//
//
//        } catch (Exception e) {
//          //  holder.oriderCount.setText("N/A");
//            Log.d("TAG", "onBindViewHolder: " + e.getMessage() + "\n");
//        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                oldOrderModel model = orders.get(position);
//                itemClickListener.onItemClick(model);
            }
        });


    }

    @Override
    public int getItemCount() {
        return orders.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView textPrice;
        public TextView date;


        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.tv_title);
            textPrice = view.findViewById(R.id.price_TV);
            date = view.findViewById(R.id.tv_date_and_time);


        }
    }
}
