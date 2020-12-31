package dev.mobile.afiqsouq.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import dev.mobile.afiqsouq.Models.CartDbModel;
import dev.mobile.afiqsouq.Models.ProductModel;
import dev.mobile.afiqsouq.R;
import dev.mobile.afiqsouq.database.CartDatabase;
import es.dmoral.toasty.Toasty;

public class ProductListDifferAdapter extends RecyclerView.Adapter<ProductListDifferAdapter.Viewholder> {
    Context context;
    CartDatabase database;
    private AsyncListDiffer<ProductModel> mDiffer;
    private List<ProductModel> mData = new ArrayList<>();
    // private List<ProductModel> mDataFiltered = new ArrayList<>();
    private LayoutInflater mInflater;
    private ProductListDifferAdapter.ItemClickListener itemClickListener;
    //callBack


    private DiffUtil.ItemCallback<ProductModel> diffCallback = new DiffUtil.ItemCallback<ProductModel>() {
        @Override
        public boolean areItemsTheSame(ProductModel oldItem, ProductModel newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(ProductModel oldItem, ProductModel newItem) {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getShortDescription().equals(newItem.getShortDescription());
        }

    };

    public ProductListDifferAdapter(Context context, ProductListDifferAdapter.ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        // this.mData = productList;
        this.context = context;
        this.itemClickListener = itemClickListener;
        database = Room.databaseBuilder(context,
                CartDatabase.class, CartDatabase.DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
        mDiffer = new AsyncListDiffer<>(this, diffCallback);

    }



    //method to submit list
    public void submitlist(List<ProductModel> data) {
       // mDiffer.submitList(null);

        mDiffer.submitList(new ArrayList<>(data));

        Log.d("TAG", "submitList: "+ data.size()) ;
    }


    //method getItem by position
    public ProductModel getItem(int position) {
        return mDiffer.getCurrentList().get(position);
    }


    @Override
    public int getItemCount() {
        return mDiffer.getCurrentList().size();
    }

    //override the method of Adapter

    @NonNull
    @Override
    public ProductListDifferAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_product
                , parent, false);
        return new ProductListDifferAdapter.Viewholder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListDifferAdapter.Viewholder holder, int position) {
        //holder.setData(getItem(position));
        try {

            Glide.with(context)
                    .load(getItem(position).getImages().get(0).getSrc())
//                .placeholder(R.drawable.placeholder)
//                .error(R.drawable.placeholder)
                    .apply(new RequestOptions().override(180, 180))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.placeholder)
                    .centerCrop()
                    .into(holder.imageView);

//          Picasso.get()
//                  .load(mDataFiltered.get(position).getImages().get(0).getSrc())
//                  .resize(150, 150)
//                  .centerCrop()
//                  .into(holder.imageView) ;

        } catch (Exception e) {
        }

        holder.title.setText(getItem(position).getName());
        String sale_price = getItem(position).getPrice();
//        if (sale_price.equals("") || sale_price.isEmpty()) {
//            holder.price.setText(getItem(position).getPrice() + "৳");
//        } else {
//            holder.price.setText(getItem(position).getSalePrice() + "৳");
//        }

        if (sale_price.equals("") || sale_price.isEmpty()) {
            // there is no sale

            holder.price.setVisibility(View.VISIBLE);
            holder.discountedPrice.setVisibility(View.INVISIBLE);
            holder.price.setText(getItem(position).getPrice() + "৳");
        }
        else {
            if(sale_price.equals(getItem(position).getRegularPrice())){
                holder.price.setVisibility(View.VISIBLE);
                holder.discountedPrice.setVisibility(View.INVISIBLE);
                holder.price.setText(getItem(position).getPrice() + "৳");
            }
            else {
                holder.price.setVisibility(View.VISIBLE);
                holder.discountedPrice.setVisibility(View.VISIBLE);
            /*
            there is discount cut thrught the price
             */

                holder.discountedPrice.setText(getItem(position).getPrice() + "৳");
                holder.price.setText(getItem(position).getRegularPrice() + "৳");

                holder.price.setPaintFlags(holder.price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }

        }

        holder.cartImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProductModel model = getItem(position);
                if (checkIfItemAllreadyExist(model.getId())) {
                    Toasty.error(context, "You Already Added The Product", Toasty.LENGTH_SHORT).show();

                } else {

                    if(!model.getStockStatus().equals("instock")){
                        Toasty.error(context, "Product Out Of Stock", Toasty.LENGTH_SHORT).show();
                    }else {
                        int cat_id  ;
                        try {
                            List<ProductModel.Category> categoryList = model.getCategories();
                            if (categoryList.size() == 0) {
                                cat_id = 0;
                            } else {
                                Log.d("TAG", "insertIntoCart: " + categoryList.get(0).getId() + " Last Item " +
                                        categoryList.get(categoryList.size() - 1).getId());
                                cat_id = categoryList.get(0).getId();

                            }

                        } catch (Exception e) {
                            Log.d("TAG", "onClick: "+e.getMessage());
                            cat_id = 0;
                        }
                        if(!checkIfItemCtegoryLogic(cat_id)){
                            insertTheProduct(model, position, view.getId());
                        }
                        else {
                            Toasty.error(context, "You Can Only Order  Max 2 Items At a time", Toasty.LENGTH_LONG).show();

                        }

                    }


                }

            }
        });

        holder.container.setOnClickListener(v -> {

            ProductModel model = getItem(position);
            itemClickListener.onItemClick(model, v.getId());

        });

    }
    public boolean checkIfItemCtegoryLogic(int cid) {
        int count = 0;
        List<CartDbModel> singleCartItem;
        if (database != null) {

            singleCartItem = database.dao().fetchCartCountByCatgoryID();
            count = singleCartItem.size();

        } else {
            Log.d("TAG", "Count Getting Zero : ");
            count = 0;
        }

        Log.d("TAG", "checkIfItemCtegoryLogic:=" + count + " : Cat ID =  " + cid);
        if (count >= 2) {
            return true;
        } else {

            return false;
        }
    }
    private void insertTheProduct(ProductModel singelProduct, int pos, int view_id) {

        CartDbModel cartDbModel = new CartDbModel();

        List<ProductModel.Attribute> attrList = singelProduct.getAttributes();

        try {
            if (attrList.size() == 0) {
                int cat_id = 0;
                List<ProductModel.Category> categoryList = singelProduct.getCategories();
                try {
                    if (categoryList.size() == 0) {
                        cat_id = 0;
                    } else {
                        Log.d("TAG", "insertIntoCart: " + categoryList.get(0).getId() + " Last Item " +
                                categoryList.get(categoryList.size() - 1).getId());
                        cat_id = categoryList.get(0).getId();

                    }

                } catch (Exception e) {
                    Log.d("TAG", "onClick: " + e.getMessage());
                    cat_id = 0;
                }
                cartDbModel.title = singelProduct.getName();
                String sale_price = singelProduct.getSalePrice();
                double price = 0.0;

                if (sale_price.equals("") || sale_price.isEmpty()) {
                    sale_price = singelProduct.getPrice();
                }

                price = Double.parseDouble(sale_price);
                cartDbModel.unit_price = Double.parseDouble(price + "");
                cartDbModel.qty = 1;
                cartDbModel.cat_id = cat_id;
                cartDbModel.product_image = getItem(pos).getImages().get(0).getSrc();
                cartDbModel.product_id = singelProduct.getId();
                cartDbModel.color = "NULL";
                cartDbModel.size = "NULL";
                try{
                    cartDbModel.stock_qty  = singelProduct.getStockQuantity() ;
                }catch (Exception e  ){
                    cartDbModel.stock_qty  =200 ;
                }
                cartDbModel.variation_id = 0;
                cartDbModel.sub_total = (double) (price * 1);

                CartDatabase.databaseWriteExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        CartDatabase.getDatabase(context).dao().insertCartItem(cartDbModel);
                        itemClickListener.onItemClick(singelProduct, view_id);
                    }
                });


                Toasty.success(context, "Added To Cart", Toasty.LENGTH_SHORT).show();

            }
            else {

                Toasty.warning(context, "This Product Has Different Type Of Variation\n Plz Choose it ", Toasty.LENGTH_LONG).show();

            }

        } catch (Exception r) {

            Toasty.warning(context, "Please Click On The Product !!", Toasty.LENGTH_LONG).show();
        }


    }

    public boolean checkIfItemAllreadyExist(int pid) {
        CartDbModel singleCartItem;
        if (database != null) {


            singleCartItem = database.dao().fetchCartByID(pid);

        } else {
            singleCartItem = null;
        }

        if (singleCartItem != null) {
            Log.d("TAG", "checkIfProductExist: " + singleCartItem.title);
            return true;
        } else {
            Log.d("TAG", "checkIfProductExist: NO DATA");
            return false;
        }
    }

    public interface ItemClickListener {
        void onItemClick(ProductModel model, int id);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public ImageView imageView, cartImage;
        public CardView container;
        public TextView title, price ,discountedPrice;
        ProductListDifferAdapter.ItemClickListener itemClickListener;

        public Viewholder(View itemView, ProductListDifferAdapter.ItemClickListener itemClickListener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageview_search_favourite);
            title = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.textview_search_price);
            cartImage = itemView.findViewById(R.id.imageview_search_cart_fr);
            container = itemView.findViewById(R.id.container);
            discountedPrice = itemView.findViewById(R.id.textview_discount_price);


        }

    }

}

