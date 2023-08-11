package project.kimora.sellerpintar.adapters;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.models.ModelPromo;
import project.kimora.sellerpintar.utils.Constants;
import project.kimora.sellerpintar.utils.DateTime;
import project.kimora.sellerpintar.utils.ImageProcessor;
import project.kimora.sellerpintar.utils.ValueFormatter;

import java.util.ArrayList;

/**
 * Created by msinfo on 11/02/23.
 */

public class AdapterHistoryPromo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ModelPromo> orderList;
    private Listener listener;

    public ProgressBar progressBar;

    public AdapterHistoryPromo(Context context, Listener listener, ArrayList<ModelPromo> orderList) {
        this.context = context;
        this.listener = listener;
        this.orderList = orderList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history_promo, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder viewHolder = ((ViewHolder)holder);
        final ModelPromo order = orderList.get(position);
        viewHolder.tvName.setText(order.Title);
        // viewHolder.tvLocation.setText(order.Address);
        viewHolder.tvStatus.setText(order.StatusName);
        if(order.TotalPayment.equals("0"))
            viewHolder.tvPrice.setVisibility(View.GONE);
        viewHolder.tvPrice.setText("Rp. "+ ValueFormatter.formatPrice(Long.parseLong(order.TotalPayment)));
        viewHolder.tv_date.setText(DateTime.formatDate(order.PromoOrderDate, "dd MMM yyyy"));
        if(order.PromoOrderNo.equals("0")) {
            viewHolder.tvOrderNum.setText("-");
        }else {
            viewHolder.tvOrderNum.setText("#" + order.PromoOrderNo);
        }
        ImageProcessor.loadImage(context, viewHolder.ivProfile, Constants.URL_IMAGE_PROMO + order.PromoID +".jpg");
        viewHolder.row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDetailClick(order);
            }
        });

        int currentStatus = Integer.parseInt(order.PromoOrderStatusID);



        if(currentStatus == 5 || currentStatus == 3){
            viewHolder.tvStatus.setText(order.StatusName);
            viewHolder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorGreen));
        } else if(currentStatus == 6 || currentStatus == 8){
            viewHolder.tvStatus.setText(order.StatusName);
            viewHolder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorRed));
        } else{
            viewHolder.tvStatus.setText(order.StatusName);
            viewHolder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvLocation, tvStatus, tv_date, tvPrice, tvOrderNum;
        ImageView ivProfile;
        RelativeLayout row;

        public ViewHolder(View view) {
            super(view);
            tvName      = view.findViewById(R.id.tv_name);
           // tvLocation  = view.findViewById(R.id.tv_location);
            tvStatus    = view.findViewById(R.id.tv_status);
            tv_date     = view.findViewById(R.id.tv_date);
            tvPrice     = view.findViewById(R.id.tv_price);
            tvOrderNum  = view.findViewById(R.id.tv_order_num);
            ivProfile   = view.findViewById(R.id.iv_profile);
            row         = view.findViewById(R.id.row);
        }
    }

    public interface Listener {
        void onDetailClick(ModelPromo OrderPromo);
    }
}
