package dev.mobile.afiqsouq.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smarteist.autoimageslider.SliderViewAdapter;


import java.util.ArrayList;
import java.util.List;

import dev.mobile.afiqsouq.Models.sliderItem;
import dev.mobile.afiqsouq.R;


public class SliderAdapterExample extends SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {

    private Context context;
    private List<sliderItem> mSliderItems = new ArrayList<>();
     int view_type  =  0 ;  // 1 means product detais
    public SliderAdapterExample(Context context , List<sliderItem> sliderItems   ) {
        this.context = context;
        this.mSliderItems = sliderItems ;

    }

    public void renewItems(List<sliderItem> sliderItems) {
        this.mSliderItems = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.mSliderItems.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(sliderItem sliderItem) {
        this.mSliderItems.add(sliderItem);
        notifyDataSetChanged();
    }

    View inflate  ;

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {

        inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item, null);


        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        sliderItem sliderItem = mSliderItems.get(position);

       // viewHolder.textViewDescription.setText(sliderItem.getDescription());
          Glide.with(viewHolder.itemView)
                  .load(sliderItem.getImage())
                  .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                  .into(viewHolder.imageViewBackground);



        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();


            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }

    public  class SliderAdapterVH  extends SliderViewAdapter.ViewHolder  {

        View itemView;
        ImageView imageViewBackground;
        ImageView imageGifContainer;
        TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.image);
            this.itemView = itemView;
        }
    }

}