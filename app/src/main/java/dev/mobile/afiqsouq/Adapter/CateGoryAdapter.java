package dev.mobile.afiqsouq.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dev.mobile.afiqsouq.Models.CategoryResp;
import dev.mobile.afiqsouq.Models.ProductModel;
import dev.mobile.afiqsouq.R;




public class CateGoryAdapter extends RecyclerView.Adapter<CateGoryAdapter.ViewHolder> {

    List<CategoryResp> list;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;
    Context context;

    public CateGoryAdapter(Context context, List<CategoryResp> list, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.categories_row_layout, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // get the image
        ProductModel.Image image = list.get(position).getImage();

        if (image != null) {
            Glide.with(context)
                    .load(image.getSrc())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                    .into(holder.imageView);
        }
        if (list.get(position).getName().contains("amp;")) {

            String name = list.get(position).getName().replace("amp;", "");
            holder.name.setText(name);
        } else {
            holder.name.setText(list.get(position).getName());

        }


    }

    public interface ItemClickListener {
        void onItemClick(View view, int pos);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;
        public TextView name;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_category);
            name = itemView.findViewById(R.id.tv_category_name);
            this.itemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
