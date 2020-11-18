package dev.mobile.afiqsouq.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dev.mobile.afiqsouq.Models.CouponModel;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.Utils.Constants;


public class couponAdapter extends RecyclerView.Adapter<couponAdapter.ViewHolder> {

    List<CouponModel> list;
    private LayoutInflater mInflater;

    Context context;

    public couponAdapter(Context context, List<CouponModel> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;

    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_coupon, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NotNull ViewHolder holder, final int position) {

        // coupon model
        CouponModel model = list.get(position)  ;
        holder.title.setText("Coupon Code : "+model.getCode());
        holder.value.setText(Constants.BDT_SIGN+" "+model.getAmount());
        holder.code.setText(model.getDescription()+"");

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title , code , value  ;


        public ViewHolder(View itemView) {
            super(itemView);

        title = itemView.findViewById(R.id.title) ;
        code = itemView.findViewById(R.id.code) ;
        value = itemView.findViewById(R.id.value) ;

        }


    }
}
