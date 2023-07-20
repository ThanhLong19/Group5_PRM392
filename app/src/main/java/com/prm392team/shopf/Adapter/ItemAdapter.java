package com.prm392team.shopf.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prm392team.shopf.Entity.Order;
import com.prm392team.shopf.Entity.Product;
import com.prm392team.shopf.ProductHolder;
import com.prm392team.shopf.R;
import com.prm392team.shopf.RecyclerViewHolder;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ProductHolder> {

    private List<Product> data;

    public ItemAdapter(List<Product> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_product_holder, parent, false);
        ProductHolder recyclerViewHolder = new ProductHolder(v, parent.getContext());

        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product show = data.get(position);
        holder.setData(show);
        holder.setProductID(show.getProductId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
