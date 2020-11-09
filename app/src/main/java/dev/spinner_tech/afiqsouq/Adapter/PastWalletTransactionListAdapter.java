package dev.spinner_tech.afiqsouq.Adapter;

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

import dev.spinner_tech.afiqsouq.Models.CartModel;
import dev.spinner_tech.afiqsouq.Models.PreviousWalletTransModel;
import dev.spinner_tech.afiqsouq.Models.oldOrderModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.Utils.Constants;

public class PastWalletTransactionListAdapter extends RecyclerView.Adapter<PastWalletTransactionListAdapter.ViewHolder> {

    private List<PreviousWalletTransModel> orders = new ArrayList<>();
    List<CartModel> students = new ArrayList<>();
    List<String> dateList = new ArrayList<>();
    private Context context;
    private PastWalletTransactionListAdapter.ItemClickListener itemClickListener;
    CartModel student;

    public PastWalletTransactionListAdapter(List<PreviousWalletTransModel> orders, Context context, ItemClickListener itemClickListener) {
        this.orders = orders;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public PastWalletTransactionListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_item, parent, false);
        PastWalletTransactionListAdapter.ViewHolder viewHolder = new PastWalletTransactionListAdapter.ViewHolder(view);
        return viewHolder;
    }

    public interface ItemClickListener {
        void onItemClick(oldOrderModel model);
    }


    @Override
    public void onBindViewHolder(@NonNull PastWalletTransactionListAdapter.ViewHolder holder, final int position) {

        PreviousWalletTransModel item = orders.get(position);
        if(item.getType().toLowerCase().equals("credit")){
            holder.transTitle.setText("Balance Added ");
        }
        else {
            holder.transTitle.setText("Product Purchased ");
        }
        holder.textPrice.setText(item.getAmount());
        String date = item.getDate();
        holder.bottomTitle.setText("on "+ date.substring(0 , 9 ) );
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

        public TextView title , transTitle;
        public TextView textPrice;
        public TextView bottomTitle;


        public ViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.tv_title);
            textPrice = view.findViewById(R.id.spent_ammount_tv);
            transTitle = view.findViewById(R.id.transactionTitle) ;
            bottomTitle = view.findViewById(R.id.tv_purchases_item) ;

        }
    }
}
