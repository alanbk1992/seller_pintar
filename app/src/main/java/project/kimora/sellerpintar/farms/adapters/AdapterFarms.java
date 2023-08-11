package project.kimora.sellerpintar.farms.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.farms.models.ModelFarms;
import project.kimora.sellerpintar.models.ModelPromo;
import project.kimora.sellerpintar.services.models.ServiceModel;


public class AdapterFarms extends RecyclerView.Adapter<AdapterFarms.ViewHolder> {

    private ArrayList<ModelFarms> farmList;
  //  private List<ModelFarms> farmList;
    private Context context;
    public boolean showShimmer = true;
    int SHIMMER_ITEM_NUMBER = 2;
    private AdapterFarms.Listener listener;

    public AdapterFarms(ArrayList<ModelFarms> farmsList, Context context , AdapterFarms.Listener listener ) {
        this.farmList = farmsList;
        this.context = context;
        this.listener = listener;

    }

    @Override
    public AdapterFarms.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.row_farms, null);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(AdapterFarms.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        AdapterFarms.ViewHolder vHolder = (AdapterFarms.ViewHolder)holder;
        if (showShimmer == true) {
            vHolder.shimmerFrameLayout.startShimmer();

        }else{


            final ModelFarms modelFarms = farmList.get(position);
            vHolder.shimmerFrameLayout.stopShimmer();
            vHolder.shimmerFrameLayout.setShimmer(null);
            holder.ivFarm.setBackgroundResource(R.drawable.small_radius_white);
            holder.tvName.setBackgroundResource(R.drawable.small_radius_white);
            holder.tvMemberStatus.setBackgroundResource(R.drawable.small_radius_white);
            holder.tvAddress.setBackgroundResource(R.drawable.small_radius_white);
            holder.tvPhone.setBackgroundResource(R.drawable.small_radius_white);

            holder.tvName.setText(modelFarms.farm_name);
            holder.tvMemberStatus.setText(modelFarms.membership_id);
            holder.tvAddress.setText(modelFarms.address);
            holder.tvPhone.setText(modelFarms.phone);
          //  Glide.with(context)
                //    .load(modelFarms.getIvFarm())
                 //   .into(holder.ivFarm);
            //on click linear layout to check the position


            holder.shimmerFrameLayout.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {

                    listener.onDetailFarmClick(position);

                }
            });
        }
    }
    @Override
    public int getItemCount() {

        return showShimmer ? SHIMMER_ITEM_NUMBER :farmList.size();
    }

    // inner class of adapter
    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView ivFarm;
        public TextView tvName  , tvAddress , tvPhone , tvMemberStatus;
        public ShimmerFrameLayout shimmerFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            shimmerFrameLayout     = itemView.findViewById(R.id.shimmer_layout);
            ivFarm = (ImageView) itemView.findViewById(R.id.iv_farm);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvMemberStatus = (TextView) itemView.findViewById(R.id.tv_member_status);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address);
            tvPhone = (TextView) itemView.findViewById(R.id.tv_phone);

        }
    }

    public interface Listener {
        void onDetailFarmClick(int position);


    }
}