package com.prm392team.shopf;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.prm392team.shopf.Adapter.RecyclerViewAdapter;
import com.prm392team.shopf.Entity.Product;
import com.prm392team.shopf.R;
import com.prm392team.shopf.Entity.Order;
import com.prm392team.shopf.RoomDB.FFoodDB;
import com.prm392team.shopf.RoomDB.OrderDAO;
import com.prm392team.shopf.RoomDB.ProductDAO;

import java.util.ArrayList;

public class RecyclerViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {
    private Context context;
    private TextView txtFname;
    private TextView txtFprice;
    private TextView txtTotalProduct;
    private TextView txtTotalPrice;
    private ImageView imgData;
    private int orderId;
    private  float total = 0;
    private Button viewOrderbtn;
    private Button btncancer;
    FFoodDB db;
    private RecyclerViewAdapter adapter;


    ProductDAO productDAO;
    private void bindingView(View itemView) {
        imgData = itemView.findViewById(R.id.first_item_image);
        txtFname = itemView.findViewById((R.id.firstProductname));
        txtFprice = itemView.findViewById((R.id.totalPrice));
        txtTotalProduct = itemView.findViewById((R.id.total_item));
        txtTotalPrice = itemView.findViewById((R.id.total_price_order));
        viewOrderbtn = itemView.findViewById((R.id.btnViewAll));
        btncancer = itemView.findViewById(R.id.btnCancer);
        btncancer.setOnClickListener(this::onDeleteOrder);

        db = FFoodDB.getInstance(itemView.getContext());
        productDAO = db.productDAO();
    }

    private void onDeleteOrder(View view) {
        int position = getAdapterPosition();

        if (position != RecyclerView.NO_POSITION) {
//            int orderId = db.get(position).getOrderId();
            OrderDAO orderDAO = db.orderDAO();
            int orderId = orderDAO.getAllOrder().get(position).getOrderId();
            orderDAO.deleteOrder(orderId);
            adapter.reLoad();

            Toast.makeText(context, "Đã xóa đơn hàng", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteOrderFromDatabase(int orderId) {

    }

    private void bindingAction(View itemView) {
        itemView.setOnClickListener(this);
        viewOrderbtn.setOnClickListener(this::swapScreen);
        
    }


    public void setData(Order item) {
        orderId = item.getOrderId();

        int[] i = {item.getProductId()};
        ArrayList<Product> products = (ArrayList<Product>) productDAO.loadAllProductByIds(i);
        float totalPrice=0;

        for (Product p: products) {
            totalPrice += p.getPrice();
        }
        total = totalPrice;

        int drawableResourceId = itemView.getContext().getResources().getIdentifier(products.get(0).getImage(), "drawable", itemView.getContext().getPackageName());
        Glide.with(itemView.getContext()).load(drawableResourceId).into(imgData);
        txtFname.setText(products.get(0).getProductName());
        txtFprice.setText("Price: " + String.valueOf(products.get(0).getPrice()));
        txtTotalProduct.setText("Total item: "+String.valueOf(products.size()));
        txtTotalPrice.setText("Total price:"+String.valueOf(totalPrice));

    }

    public RecyclerViewHolder(@NonNull View itemView, Context context, RecyclerViewAdapter adapter) {
        super(itemView);
        this.context = context;
        this.adapter = adapter;
        bindingView(itemView);
        bindingAction(itemView);
    }

    @Override
    public void onClick(View view) {
        swapScreen(view);
    }

    private void swapScreen(View view){
        Intent i = new Intent(context, OrderDetailActivity.class);
        i.putExtra("id", orderId);
        i.putExtra("total", total);
        context.startActivity(i);

    }
}
