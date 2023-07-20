package com.prm392team.shopf.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prm392team.shopf.RecyclerViewHolder;
import com.prm392team.shopf.R;
import com.prm392team.shopf.Entity.Order;
import com.prm392team.shopf.RoomDB.FFoodDB;
import com.prm392team.shopf.RoomDB.OrderDAO;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<Order> data;
    private Context context;




    public RecyclerViewAdapter(List<Order> data, Context context) {
        this.data = data;
        this.context = context;
    }
    public void deleteOrder(int position){
        if (position >= 0 && position < data.size()){
            data.remove(position);
            notifyItemRemoved(position);
        }
    }
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_item, parent, false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(v, parent.getContext(), this);

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        Order show = data.get(position);
        holder.setData(show);
    }
    public void reLoad(){
        FFoodDB fFoodDB = FFoodDB.getInstance(context);
        OrderDAO orderDAO = fFoodDB.orderDAO();
        data = orderDAO.getAllOrder();
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return data.size();
    }
}
