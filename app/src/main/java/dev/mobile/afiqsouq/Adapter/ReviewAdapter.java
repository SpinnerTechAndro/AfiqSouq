package dev.mobile.afiqsouq.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.shape.CornerFamily;

import org.jetbrains.annotations.NotNull;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.List;

import dev.mobile.afiqsouq.Models.ReviewResp;
import dev.mobile.afiqsouq.R;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    List<ReviewResp> list;
    private LayoutInflater mInflater;
    private ItemClickListener itemClickListener;
    Context context;

    public ReviewAdapter(Context context, List<ReviewResp> list, ItemClickListener itemClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.reviews_row_layout, parent, false);
        return new ViewHolder(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        // get the image

        ReviewResp reviewModel = list.get(position) ;
        ReviewResp.ReviewerAvatarUrls imageModel =  reviewModel.getReviewerAvatarUrls() ;

        holder.name.setText(reviewModel.getReviewer()+"");
        holder.dateTv.setText("");
        holder.reviewTv.setHtml(reviewModel.getReview()+"");
        holder.rateTv.setText("("+reviewModel.getRating()+")");
        holder.ratingBar.setRating(Float.valueOf(reviewModel.getRating()));

//        float radius = context.getResources().getDimension(R.dimen.default_corner_radius);
        holder.imageView.setShapeAppearanceModel(holder.imageView.getShapeAppearanceModel()
                .toBuilder()
                .setAllCorners(CornerFamily.ROUNDED,20)
                .build());

        Glide.with(context)
                .load(imageModel.get96())
                .into(holder.imageView) ;


        Log.d("TAG", "onBindViewHolder: " + imageModel.get96());

    }

    public interface ItemClickListener {
        void onItemClick(View view, int pos);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ShapeableImageView imageView;
        public TextView name , dateTv , rateTv  ;
        HtmlTextView  reviewTv ;
        RatingBar ratingBar ;

        ItemClickListener itemClickListener;

        public ViewHolder(View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            imageView = (ShapeableImageView) itemView.findViewById(R.id.prodct_image_id);
            name = itemView.findViewById(R.id.rater_person_name_tv);
            dateTv = itemView.findViewById(R.id.date_TV) ;
            rateTv = itemView.findViewById(R.id.avg_rating_count) ;
            ratingBar = itemView.findViewById(R.id.rating) ;
            reviewTv = itemView.findViewById(R.id.review_text_TV) ;
            this.itemClickListener = itemClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
