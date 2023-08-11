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

import java.util.List;


public class AdapterHome extends RecyclerView.Adapter<AdapterHome.ViewHolder> {

    private List<ModelHome> homeList;
    private Context context;
    public boolean showShimmer = true;
    int SHIMMER_ITEM_NUMBER = 8;
    private AdapterHome.Listener listener;

    public AdapterHome(List<ModelHome> ModelHome, Context context , AdapterHome.Listener listener ) {
        this.homeList = ModelHome;
        this.context = context;
        this.listener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.row_home, null);
        return new AdapterHome.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        ViewHolder vHolder = (ViewHolder)holder;
        if (showShimmer) {
            vHolder.shimmerFrameLayout.startShimmer();
        }else{


            final ModelHome modelhome = homeList.get(position);
            vHolder.shimmerFrameLayout.stopShimmer();
            vHolder.shimmerFrameLayout.setShimmer(null);
            holder.tvHomeJudul.setBackgroundResource(R.drawable.small_radius_white);
            holder.ivHomeImg.setBackgroundResource(R.drawable.small_radius_white);
            holder.tvHomeJudul.setText(modelhome.getHomeTitle());

            Glide.with(context)
                    .load(modelhome.getHomeImage())
                    .into(holder.ivHomeImg);
            //on click linear layout to check the position


            holder.shimmerFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {

                    listener.onMenuDetailClick(position);

                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return showShimmer ? SHIMMER_ITEM_NUMBER :homeList.size();
    }

    // inner class of adapter
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivHomeImg;
        public TextView tvHomeJudul;
        ShimmerFrameLayout shimmerFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            shimmerFrameLayout     = itemView.findViewById(R.id.shimmer_layout);
            ivHomeImg = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvHomeJudul = (TextView) itemView.findViewById(R.id.tvName);


        }
    }

    public interface Listener {
        void onMenuDetailClick(int position);


    }
}