package dev.spinner_tech.afiqsouq.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Models.ProductModel;
import dev.spinner_tech.afiqsouq.R;


/**
 * Created by USER on 18-Jul-20.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> implements Filterable {

    private List<ProductModel> mData = new ArrayList<>();
    private List<ProductModel> mDataFiltered = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;
    Context context;

    public ProductListAdapter(Context context, List<ProductModel> productList, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = productList;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.mDataFiltered = productList;
    }

    public void addItems(List<ProductModel> newItems) {
        Log.d("TAG", "addItems: " + newItems.size());
        mData.addAll(newItems);
        mDataFiltered = mData;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(ProductModel model);
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_product
                , parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(ProductListAdapter.ViewHolder holder, final int position) {

        Log.d("TAG", "onBindViewHolder: " + mDataFiltered.size());
        Glide.with(context)
                .load(mDataFiltered.get(position).getImages().get(0).getSrc())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.placeholder)
                .into(holder.imageView);
        holder.title.setText(mDataFiltered.get(position).getName());
        holder.price.setText(mDataFiltered.get(position).getSalePrice() + "৳");


        holder.itemView.setOnClickListener(v -> {

            ProductModel model = mDataFiltered.get(position);
            itemClickListener.onItemClick(model);

        });


    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData;

                } else {
                    List<ProductModel> lstFiltered = new ArrayList<>();
                    for (ProductModel row : mData) {
                        //Log.d("TAG", "Filtering : " + row.getProductTitle());
                        if (row.getName().toLowerCase().contains(Key.toLowerCase())) {

                            //  Log.d("TAG", "Fillered: " + row.getProductTitle());
                            lstFiltered.add(row);
                        }

                    }
                    // Log.d("TAG", "Size: " + lstFiltered.size());
                    mDataFiltered = lstFiltered;
                    // Log.d("TAG", "dataset Size: " + mDataFiltered.size());

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values = mDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                mDataFiltered = (List<ProductModel>) results.values;
                notifyDataSetChanged();

            }
        };


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public CardView container;
        public TextView title, price;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview_search_favourite);
            title = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.textview_search_price);


        }

    }


}


