package project.kimora.sellerpintar.adapters;


import android.annotation.SuppressLint;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import project.kimora.sellerpintar.models.ModelHome;
import project.kimora.sellerpintar.*;
import project.kimora.sellerpintar.models.ModelHome;
import project.kimora.sellerpintar.models.ModelStatisticHome;

import java.util.List;


public class AdapterStatisticHome extends RecyclerView.Adapter<AdapterStatisticHome.ViewHolder> {

    private List<ModelStatisticHome> statisticHomeList;
    private Context context;
    public boolean showShimmer = true;
    int SHIMMER_ITEM_NUMBER = 6;
    private AdapterStatisticHome.Listener listener;

    public AdapterStatisticHome(List<ModelStatisticHome> ModelStatisticHome, Context context , AdapterStatisticHome.Listener listener ) {
        this.statisticHomeList = ModelStatisticHome;
        this.context = context;
        this.listener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.row_statistic_home, null);
        return new AdapterStatisticHome.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewHolder vHolder = (ViewHolder)holder;
        if (showShimmer) {
            vHolder.shimmerFrameLayout.startShimmer();
        }else{


            final ModelStatisticHome modelStatisticHome = statisticHomeList.get(position);
            vHolder.shimmerFrameLayout.stopShimmer();
            vHolder.shimmerFrameLayout.setShimmer(null);
            holder.tvTitle.setBackgroundResource(R.drawable.small_radius_white);
            holder.tvCount.setBackgroundResource(R.drawable.small_radius_white);
            holder.tvTitle.setText(modelStatisticHome.getTitle());
          holder.tvCount.setText(modelStatisticHome.getCount());



            holder.shimmerFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {

                    listener.onStatisticDetailClick(position);

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return showShimmer ? SHIMMER_ITEM_NUMBER :statisticHomeList.size();
    }

    // inner class of adapter
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvTitle , tvCount;
        ShimmerFrameLayout shimmerFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            shimmerFrameLayout     = itemView.findViewById(R.id.shimmer_layout);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count);


        }
    }

    public interface Listener {
        void onStatisticDetailClick(int position);


    }
}