package com.prm392team.shopf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prm392team.shopf.Entity.Order;
import com.prm392team.shopf.Entity.Product;
import com.prm392team.shopf.RoomDB.FFoodDB;
import com.prm392team.shopf.RoomDB.ProductDAO;

import java.util.ArrayList;

public class ProductHolder extends RecyclerView.ViewHolder {

    private final Context c;

    private TextView txtProductName;
    private TextView txtDescription;
    private TextView txtPrice;
    private TextView txtCateID;
    private ImageView txtImage;
    FFoodDB db;
    ProductDAO productDAO;
    private int productID;
    private Button btnUpdateItem;


    public ProductHolder(@NonNull View itemView, Context c) {
        super(itemView);
        this.c = c;
        bindingView(itemView);
        bindingAction();
    }

    private void bindingView(View itemView){
        txtProductName = itemView.findViewById((R.id.tvNameListP));
        txtDescription = itemView.findViewById((R.id.tvDesListP));
        txtPrice = itemView.findViewById((R.id.tvPriceListP));
        txtImage = itemView.findViewById((R.id.imgListP));
        txtCateID = itemView.findViewById((R.id.tvCateID));

        db = FFoodDB.getInstance(itemView.getContext());
        productDAO = db.productDAO();
        btnUpdateItem = itemView.findViewById(R.id.btnUpdateItem);
    }

    private void bindingAction(){
        btnUpdateItem.setOnClickListener(this::onUpdateItem);
    }

    private void onUpdateItem(View view) {
        Intent intent = new Intent(c, UpdateItemActivity.class);
        intent.putExtra("id",productID);
        c.startActivity(intent);
    }

    public void setData(Product item) {
        productID = item.getProductId();

        int[] i = {item.getProductId()};
        ArrayList<Product> products = (ArrayList<Product>) productDAO.loadAllProductByIds(i);
        int drawableResourceId = itemView.getContext().getResources().getIdentifier(products.get(0).getImage(), "drawable", itemView.getContext().getPackageName());
        Glide.with(itemView.getContext()).load(drawableResourceId).into(txtImage);
        txtProductName.setText(item.getProductName());
        String price = Double.toString(item.getPrice());
        txtPrice.setText(price);
        txtDescription.setText((item.getDescription()));
        String cateID = Integer.toString(item.getCategoryIds());
        txtCateID.setText(cateID);
    }

    public void setProductID(int id){
        productID = id;
    }
}