package com.example.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.project.R;
import com.example.project.models.ListOrderModel;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> implements Filterable {

    public ListOrderAdapter(List<ListOrderModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.listOld = list;
    }

    List<ListOrderModel> list;
    Context context;
    List<ListOrderModel> listOld;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText("Mã xe: " + list.get(position).getId());
        holder.departure.setText("Điểm đi: " + list.get(position).getDeparture());
        holder.arrival.setText("Điểm đến: " + list.get(position).getArrival());
        holder.date.setText("Ngày đi: " + list.get(position).getDate());
        holder.time.setText("Giờ đi: " + list.get(position).getTime());
        holder.total_seats.setText("Số ghế: " + list.get(position).getTotal_seats());
        holder.seated.setText("Số ghế còn trống: " + list.get(position).getSeated());
        holder.price.setText("Giá vé: " + list.get(position).getPrice() + "vnđ");
        holder.revenue.setText("Tổng thu: " + list.get(position).getRevenue() + "vnđ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layoutItem;
        TextView id, departure, arrival, date, time, total_seats, seated, price,revenue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.list_order_id);
            departure = itemView.findViewById(R.id.list_order_from);
            arrival = itemView.findViewById(R.id.list_order_to);
            date = itemView.findViewById(R.id.list_order_date);
            time = itemView.findViewById(R.id.list_order_time);
            total_seats = itemView.findViewById(R.id.list_order_seats);
            seated = itemView.findViewById(R.id.list_order_seated);
            revenue = itemView.findViewById(R.id.list_order_revenue);
            price = itemView.findViewById(R.id.list_order_price);
            layoutItem = itemView.findViewById(R.id.item_order_layout);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty())
                {
                    list = listOld;
                }
                else {
                    List<ListOrderModel> listorder = new ArrayList<>();
                    for (ListOrderModel listOrderModel: listOld) {
                        if (listOrderModel.getArrival().toLowerCase().contains(strSearch.toLowerCase()) || listOrderModel.getDeparture().toLowerCase().contains(strSearch.toLowerCase()))
                        {
                            listorder.add(listOrderModel);
                        }
                    }
                    list = listorder;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<ListOrderModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
