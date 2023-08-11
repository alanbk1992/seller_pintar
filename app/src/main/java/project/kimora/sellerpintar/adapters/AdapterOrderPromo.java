package project.kimora.sellerpintar.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import project.kimora.sellerpintar.R;
import project.kimora.sellerpintar.utils.ValueFormatter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by msinfo on 11/02/23.
 */

public class AdapterOrderPromo extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<JSONObject> orderList;
    public ProgressBar progressBar;

    public AdapterOrderPromo(Context context, ArrayList<JSONObject> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_promo, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder = ((ViewHolder) holder);
        final JSONObject order = orderList.get(position);
        try {

            viewHolder.medicine_name.setText(order.getString("Title"));
            viewHolder.price.setText("Rp. " + ValueFormatter.formatPrice(Long.parseLong(order.getString("Harga"))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView medicine_name , price;
        RelativeLayout row;

        public ViewHolder(View view) {
            super(view);
            medicine_name   = view.findViewById(R.id.medicine_name);
            row             = view.findViewById(R.id.row);
            price        = view.findViewById(R.id.et_price);
        }
    }

}
