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

import com.example.project.R;
import com.example.project.activity.ActivityBookingView;
import com.example.project.activity.AdminEditActivity;
import com.example.project.models.ListBusModel;
import com.example.project.models.ListBusUserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListBusUserAdapter extends RecyclerView.Adapter<ListBusUserAdapter.ViewHolder> implements Filterable {
    public ListBusUserAdapter(List<ListBusUserModel> list, Context context) {
        this.list = list;
        this.context = context;
        this.listOld = list;
    }

    List<ListBusUserModel> list;
    Context context;
    List<ListBusUserModel> listOld;


    @NonNull
    @Override
    public ListBusUserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bus_user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListBusUserAdapter.ViewHolder holder, int position) {
        final ListBusUserModel userModel = list.get(position);
        if (userModel == null)
        {
            return;
        }
        if (list.get(position).getSeated() == 0)
        {
            holder.id.setText("Mã xe: " + list.get(position).getId());
            holder.departure.setText("Từ: " + list.get(position).getDeparture());
            holder.arrival.setText("Đến: " + list.get(position).getArrival());
            holder.date.setText("Ngày: " + list.get(position).getDate());
            holder.time.setText("Giờ khởi hành: " + list.get(position).getTime());
            holder.total_seats.setText("Số ghế: " + list.get(position).getTotal_seats());
            holder.seated.setText("Đã đầy");
        }
        else
        {
            holder.id.setText("Mã xe: " + list.get(position).getId());
            holder.departure.setText("Từ: " + list.get(position).getDeparture());
            holder.arrival.setText("Đến: " + list.get(position).getArrival());
            holder.date.setText("Ngày: " + list.get(position).getDate());
            holder.time.setText("Giờ khởi hành: " + list.get(position).getTime());
            holder.total_seats.setText("Số ghế: " + list.get(position).getTotal_seats());
            holder.seated.setText("Còn trống: " + list.get(position).getSeated());
//(String.valueOf(cartModelList.get(position).getTotalPrice()))
            holder.layoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickGoToDetailItem(userModel);
                }
            });
        }
    }


    private void onClickGoToDetailItem(ListBusUserModel userModel)
    {
        Intent intent = new Intent(context, ActivityBookingView.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", userModel);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView layoutItem;
        TextView id, departure, arrival, date, time, total_seats, seated;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.list_bus_user_id);
            departure = itemView.findViewById(R.id.list_bus_user_from);
            arrival = itemView.findViewById(R.id.list_bus_user_to);
            date = itemView.findViewById(R.id.list_bus_user_date);
            time = itemView.findViewById(R.id.list_bus_user_time);
            seated = itemView.findViewById(R.id.list_bus_user_seated);
            total_seats = itemView.findViewById(R.id.list_bus_user_seats);
            layoutItem = itemView.findViewById(R.id.item_bus_user_layout);
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
                    List<ListBusUserModel> listbususer = new ArrayList<>();
                    for (ListBusUserModel listBusUserModel: listOld) {
                        if (listBusUserModel.getArrival().toLowerCase().contains(strSearch.toLowerCase()) || listBusUserModel.getDeparture().toLowerCase().contains(strSearch.toLowerCase()))
                        {
                            listbususer.add(listBusUserModel);
                        }
                    }
                    list = listbususer;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                list = (List<ListBusUserModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


}
