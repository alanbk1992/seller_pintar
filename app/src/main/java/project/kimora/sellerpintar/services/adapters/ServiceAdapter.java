package project.kimora.sellerpintar.services.adapters;


import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import project.kimora.sellerpintar.*;
import project.kimora.sellerpintar.services.models.ServiceModel;

import java.util.List;


public class ServiceAdapter extends RecyclerView.Adapter<project.kimora.sellerpintar.services.adapters.ServiceAdapter.ViewHolder> {

    private List<ServiceModel> serviceList;
    private Context context;
    public boolean showShimmer = true;
    int SHIMMER_ITEM_NUMBER = 9;
    private project.kimora.sellerpintar.services.adapters.ServiceAdapter.Listener listener;

    public ServiceAdapter(List<ServiceModel> serviceList, Context context , project.kimora.sellerpintar.services.adapters.ServiceAdapter.Listener listener ) {
        this.serviceList = serviceList;
        this.context = context;
        this.listener = listener;

    }

    @Override
    public project.kimora.sellerpintar.services.adapters.ServiceAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.row_services, null);
        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(project.kimora.sellerpintar.services.adapters.ServiceAdapter.ViewHolder holder, final int position) {
        project.kimora.sellerpintar.services.adapters.ServiceAdapter.ViewHolder vHolder = (project.kimora.sellerpintar.services.adapters.ServiceAdapter.ViewHolder)holder;
        if (showShimmer) {
            vHolder.shimmerFrameLayout.startShimmer();
        }else{


            final ServiceModel serviceModel = serviceList.get(position);
            vHolder.shimmerFrameLayout.stopShimmer();
            vHolder.shimmerFrameLayout.setShimmer(null);
            holder.tvHomeName.setBackgroundResource(R.drawable.small_radius_white);
            holder.ivHomeImg.setBackgroundResource(R.drawable.small_radius_white);
            holder.tvHomeName.setText(serviceModel.getServiceName());

            Glide.with(context)
                    .load(serviceModel.getServiceImage())
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
        return showShimmer ? SHIMMER_ITEM_NUMBER :serviceList.size();
    }

    // inner class of adapter
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivHomeImg;
        public TextView tvHomeName;
        ShimmerFrameLayout shimmerFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            shimmerFrameLayout     = itemView.findViewById(R.id.shimmer_layout);
            ivHomeImg = (ImageView) itemView.findViewById(R.id.ivIcon);
            tvHomeName = (TextView) itemView.findViewById(R.id.tvName);


        }
    }

    public interface Listener {
        void onMenuDetailClick(int position);


    }
}