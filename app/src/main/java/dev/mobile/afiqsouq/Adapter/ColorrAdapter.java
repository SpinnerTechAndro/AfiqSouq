package dev.mobile.afiqsouq.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import dev.mobile.afiqsouq.R;

public  class ColorrAdapter extends RecyclerView.Adapter<ColorrAdapter.ViewHolder> {
    List<String> list;
    LayoutInflater mInflater;
    Context context;
    int pos  = 0 ;
    int selected_position = 0;

    public ColorrAdapter(List<String> list, Context context ) {
        this.list = list;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;


    }

    @NonNull
    @Override
    public ColorrAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_size, parent, false);
        return new ColorrAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColorrAdapter.ViewHolder holder, final int position) {


        holder.product_size.setBackground(selected_position == position ? context.getResources().getDrawable(R.drawable.select_size_background) :null);
        holder.product_size.setText(list.get(position).toUpperCase());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pos = position ;
                notifyItemChanged(selected_position);
                selected_position = pos;
                notifyItemChanged(selected_position);



            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public String getColorName(){

        if(list.size()==0){

            return  "null" ;
        }
        else {

            return  list.get(pos) ;
        }



    }
    public class ViewHolder extends RecyclerView.ViewHolder  {

        public TextView product_size ;

        public ViewHolder(View itemView ) {
            super(itemView);
            product_size = itemView.findViewById(R.id.size_tv) ;

        }


    }
}
