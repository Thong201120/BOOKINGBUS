package com.example.project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.R;
import com.example.project.models.ListOrderModel;
import com.example.project.models.ListTravelModel;

import java.util.ArrayList;
import java.util.List;

public class ListTravelAdapter extends RecyclerView.Adapter<ListTravelAdapter.ViewHolder> implements Filterable {
    
    List<ListTravelModel> list;
    Context context;
    List<ListTravelModel> listOld;
    
    public ListTravelAdapter(List<ListTravelModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.listOld = list;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_travel_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText("Mã giao dịch: " + list.get(position).getId());
        holder.departure.setText("Điểm đi: " + list.get(position).getDeparture());
        holder.arrival.setText("Điểm đến: " + list.get(position).getArrival());
        holder.date.setText("Ngày đi: " + list.get(position).getDate());
        holder.time.setText("Giờ đi: " + list.get(position).getTime());
        holder.seated.setText("Số ghế đã đặt: " + list.get(position).getNumber_seat());
        holder.price.setText("Giá vé: " + list.get(position).getPrice() + "vnđ");
        holder.revenue.setText("Tổng chi phí: " + list.get(position).getRevenue() + "vnđ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layoutItem;
        TextView id, departure, arrival, date, time, seated, price,revenue;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.list_travel_id);
            departure = itemView.findViewById(R.id.list_travel_from);
            arrival = itemView.findViewById(R.id.list_travel_to);
            date = itemView.findViewById(R.id.list_travel_date);
            time = itemView.findViewById(R.id.list_travel_time);
            seated = itemView.findViewById(R.id.list_travel_seated);
            revenue = itemView.findViewById(R.id.list_travel_revenue);
            price = itemView.findViewById(R.id.list_travel_price);
            layoutItem = itemView.findViewById(R.id.item_travel_layout);
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
                    List<ListTravelModel> listorder = new ArrayList<>();
                    for (ListTravelModel listTravelModel: listOld) {
                        if (listTravelModel.getArrival().toLowerCase().contains(strSearch.toLowerCase()) || listTravelModel.getDeparture().toLowerCase().contains(strSearch.toLowerCase()))
                        {
                            listorder.add(listTravelModel);
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
                list = (List<ListTravelModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
