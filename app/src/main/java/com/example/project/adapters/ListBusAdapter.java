package com.example.project.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.activity.AdminEditActivity;
import com.example.project.R;
import com.example.project.models.ListBusModel;

import java.util.ArrayList;
import java.util.List;

public class ListBusAdapter extends RecyclerView.Adapter<ListBusAdapter.ViewHolder> implements Filterable {


    List<ListBusModel> list;

    public ListBusAdapter(List<ListBusModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.listOld = list;
    }

    Context context;
    List<ListBusModel> listOld;




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bus_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ListBusModel user = list.get(position);
        if (user == null)
        {
            return;
        }
            holder.id.setText("Mã xe: " + list.get(position).getId());
            holder.departure.setText("Từ: " + list.get(position).getDeparture());
            holder.arrival.setText("Đến: " + list.get(position).getArrival());
            holder.date.setText("Ngày: " + list.get(position).getDate());
//        holder.total_seats.setText("Số ghế: " + String.valueOf(list.get(position).getTotal_seats()));

            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickGoToDetailItem(user);
                }
            });

    }

    private void onClickGoToDetailItem(ListBusModel user)
    {
        Intent intent = new Intent(context, AdminEditActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", user);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layoutItem;
        TextView id, departure, arrival, date, time, total_seats;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.list_bus_id);
            departure = itemView.findViewById(R.id.list_bus_from);
            arrival = itemView.findViewById(R.id.list_bus_to);
            date = itemView.findViewById(R.id.list_bus_date);
//            time = itemView.findViewById(R.id.list_bus_seat);
//            total_seats = itemView.findViewById(R.id.list_bus_seat);
            layoutItem = itemView.findViewById(R.id.item_bus_layout);
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
                } else {
                    List<ListBusModel> listbus = new ArrayList<>();
                    for (ListBusModel listBusModel: listOld) {
                        if (listBusModel.getArrival().toLowerCase().contains(strSearch.toLowerCase()) || listBusModel.getDeparture().toLowerCase().contains(strSearch.toLowerCase())) {
                            listbus.add(listBusModel);
                        }
                    }
                    list = listbus;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<ListBusModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
