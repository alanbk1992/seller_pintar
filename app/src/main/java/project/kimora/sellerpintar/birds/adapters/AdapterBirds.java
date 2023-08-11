package project.kimora.sellerpintar.birds.adapters;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.extensions.ResizeAnimation;
import project.kimora.sellerpintar.utils.Checker;
import project.kimora.sellerpintar.birds.models.ModelBirds;

import java.util.ArrayList;

public class AdapterBirds extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ModelBirds> birdList;
    private ArrayList<ModelBirds> productSearchList;
    private AdapterBirds.Listener listener;

    private boolean isVertical;
    public ProgressBar progressBar;
    private boolean limit = false;
    private int progressBarHeight = 0;
    private int TYPE_ITEM = 1;
    private int TYPE_FOOTER = 2;

    public AdapterBirds(Context context, AdapterBirds.Listener listener, ArrayList<ModelBirds> birdList) {
        this.context = context;
        this.listener = listener;
        this.birdList = birdList;
    // this.isVertical = isVertical;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_ITEM) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_birds, parent, false);
            return new AdapterBirds.ViewHolder(itemView);
        } else if(viewType == TYPE_FOOTER) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_load_more, parent, false);
            return new AdapterBirds.FooterViewHolder(itemView);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder && position < birdList.size()) {
            ViewHolder vHolder = ((ViewHolder)holder);
            ModelBirds birds = birdList.get(position);
            vHolder.tvCategory2Name.setText(birds.sub_category2_name);
            vHolder.tvGender.setText(birds.gender);
            vHolder.tvNoRing.setText("No Ring" + " " + birds.no_ring);
            vHolder.tvConditionName.setText(birds.status_name);
//            vHolder.tvWarehouse.setText(product.warehouse);
//            vHolder.tvConditionName.setText(product.condition_name);
//            vHolder.tvUnit.setText(product.unit);
           // vHolder.tvSKU.setText(product.SkuID);
           // if (product.PriceRetail == null) {
               // vHolder.tvPrice.setText("Rp " + 0);
           // }else{
               // vHolder.tvPrice.setText("Rp "+ ValueFormatter.formatPrice(Long.parseLong(product.PriceRetail)));
           // }
//
//            if (product.Stock.equals("0")) {
              //  vHolder.tvStock.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark));
          //  }else{
              //  vHolder.tvStock.setTextColor(ContextCompat.getColor(context, android.R.color.darker_gray));
          //  }
            //vHolder.tvStock.setText("Stok : " + product.Stock);

      //  if(product.ImageVariantProduct != null) {
        //loadImage(context, vHolder.ivImage, product.ImageVariantProduct);
        //}else{
           // loadImage(context, vHolder.ivImage, "http://twinzahra.com/images/products/default.jpg");
       // }




            vHolder.Row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDetailClick(birdList.get(position));
                    ;
                }
            });

//            vHolder.img_action.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onActionClick(birdList.get(position));
//                    ;
//                }
//            });

        } else if (holder instanceof FooterViewHolder) {
            if (birdList.size() > 0 && Checker.isNetWorkAvailable(context) && !limit) {
                setProgressVisibility(View.VISIBLE);
                listener.onLoadMore();
            } else
                setProgressVisibility(View.GONE);
        }
    }

    public static void loadImage(Context context, ImageView imageView, String url) {
        Log.d("loadImage", url);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.no_image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(context).load(url).apply(options).into(imageView);
    }


    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        } else return TYPE_ITEM;
    }





    public void setProgressVisibility(int v) {
        if (progressBar != null) {
            if(v == View.VISIBLE) {
                progressBar.setVisibility(v);
                progressBar.getLayoutParams().height = progressBarHeight;
            } else {
                ResizeAnimation.resizeHideAnimation(progressBar, progressBarHeight);
            }
        }
    }


    public void limitNow(boolean b) {
        limit = b;
        setProgressVisibility(View.GONE);
    }

    private boolean isPositionFooter(int position) {
        return position == birdList.size();
    }
    @Override
    public int getItemCount() {

        return birdList.size() + 1 ;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategory2Name, tvGender , tvNoRing , tvConditionName ;
        ImageView ivImage , img_action;
        RelativeLayout Row;


        public ViewHolder(View view) {
            super(view);
            tvCategory2Name     = view.findViewById(R.id.tv_category2_name);
            tvGender      = view.findViewById(R.id.tv_gender);
            tvNoRing      = view.findViewById(R.id.tv_no_ring);
            tvConditionName      = view.findViewById(R.id.tv_condition_name);
            Row         = view.findViewById(R.id.row);
            ivImage       = view.findViewById(R.id.iv_image);
            img_action    =  view.findViewById(R.id.img_action);
           //if(!isVertical) {
            //  ivImage.getLayoutParams().width = Constants.displayWidth * 25 / 100;
              // ivImage.getLayoutParams().height = Constants.displayWidth * 50 / 100;
            //}
        }
    }
    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
            progressBar = itemView.findViewById(R.id.progressbar);
            progressBarHeight = progressBar.getLayoutParams().height;
        }
    }


    public interface Listener {
        void onDetailClick(ModelBirds product);
        void onActionClick(ModelBirds product);
        void onLoadMore();
    }




}
