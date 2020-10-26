package dev.spinner_tech.afiqsouq.Adapter;

import android.content.Context;
import android.content.Intent;
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
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import dev.spinner_tech.afiqsouq.Models.CartDbModel;
import dev.spinner_tech.afiqsouq.Models.ProductModel;
import dev.spinner_tech.afiqsouq.R;
import dev.spinner_tech.afiqsouq.View.Activities.CartListPage;
import dev.spinner_tech.afiqsouq.database.CartDatabase;
import es.dmoral.toasty.Toasty;


/**
 * Created by USER on 18-Jul-20.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> implements Filterable {

    private List<ProductModel> mData = new ArrayList<>();
    private List<ProductModel> mDataFiltered = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;
    Context context;
    CartDatabase database;

    public ProductListAdapter(Context context, List<ProductModel> productList, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = productList;
        this.context = context;
        this.itemClickListener = itemClickListener;
        this.mDataFiltered = productList;
        database = Room.databaseBuilder(context,
                CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    public void addItems(List<ProductModel> newItems) {
        Log.d("TAG", "addItems: " + newItems.size());
        mData.addAll(newItems);
        mDataFiltered = mData;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClick(ProductModel model , int id );
    }

    @Override
    public ProductListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_product
                , parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(ProductListAdapter.ViewHolder holder, final int position) {

      //  Log.d("TAG", "onBindViewHolder: " + mDataFiltered.size());
      try{
          Glide.with(context)
                  .load(mDataFiltered.get(position).getImages().get(0).getSrc())
                  .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.placeholder)
                  .into(holder.imageView);

      }catch (Exception e){

      }


        holder.title.setText(mDataFiltered.get(position).getName());
        String sale_price = mDataFiltered.get(position).getSalePrice();
        if (sale_price.equals("") || sale_price.isEmpty()) {
            holder.price.setText(mDataFiltered.get(position).getPrice() + "৳");
        } else {
            holder.price.setText(mDataFiltered.get(position).getSalePrice() + "৳");
        }

        holder.cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductModel model = mDataFiltered.get(position);
                if (checkIfItemAllreadyExist(model.getId())) {
                    Toasty.error(context, "You Already Added The Product" , Toasty.LENGTH_SHORT).show();

                } else {



                    insertTheProduct(model  , position , view.getId());

                }

            }
        });

        holder.container.setOnClickListener(v -> {

            ProductModel model = mDataFiltered.get(position);
            itemClickListener.onItemClick(model , v.getId());

        });




    }

    private void insertTheProduct(ProductModel singelProduct , int pos , int view_id ) {

        CartDbModel cartDbModel = new CartDbModel();

         List<ProductModel.Attribute> attrList = singelProduct.getAttributes() ;

         try{
             if(attrList.size()==0){
                 cartDbModel.title = singelProduct.getName();
                 String sale_price = singelProduct.getSalePrice();
                 double price = 0.0;

                 if (sale_price.equals("") || sale_price.isEmpty()) {
                     sale_price = singelProduct.getPrice();
                 }

                 price = Double.parseDouble(sale_price);
                 cartDbModel.unit_price = Double.parseDouble(price + "");
                 cartDbModel.qty = 1;
                 cartDbModel.product_image = mDataFiltered.get(pos).getImages().get(0).getSrc();
                 cartDbModel.product_id = singelProduct.getId();
                 cartDbModel.color = "NULL";
                 cartDbModel.size = "NULL";
                 cartDbModel.variation_id = 0;
                 cartDbModel.sub_total = (double) (price * 1);

                 CartDatabase.databaseWriteExecutor.execute(new Runnable() {
                     @Override
                     public void run() {
                         CartDatabase.getDatabase(context).dao().insertCartItem(cartDbModel);
                         itemClickListener.onItemClick(singelProduct , view_id);
                     }
                 });


                 Toasty.success(context, "Added To Cart", Toasty.LENGTH_SHORT).show();

             }
             else {
                 Toasty.warning(context , "This Product Has Different Type Of Variation\n Plz Choose it " , Toasty.LENGTH_LONG).show();

             }

         }catch (Exception r ){
             Toasty.warning(context , "This Product Has Different Type Of Variation\n Plz Choose it " , Toasty.LENGTH_LONG).show();
         }



    }

    public boolean checkIfItemAllreadyExist(int pid) {
        CartDbModel  singleCartItem ;
        if(database != null){


             singleCartItem = database.dao().fetchCartByID(pid) ;

        }
        else {
            singleCartItem = null ;
        }

        if(singleCartItem!= null){
            Log.d("TAG", "checkIfProductExist: " + singleCartItem.title);
            return  true  ;
        }
        else {
            Log.d("TAG", "checkIfProductExist: NO DATA" );
            return  false ;
        }
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
        public ImageView imageView, cartImage ;
        public CardView container;
        public TextView title, price;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview_search_favourite);
            title = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.textview_search_price);
            cartImage = itemView.findViewById(R.id.imageview_search_cart_fr);
            container = itemView.findViewById(R.id.container) ;


        }

    }


}


