package com.prm392team.shopf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.prm392team.shopf.Adapter.ItemAdapter;
import com.prm392team.shopf.Entity.Product;
import com.prm392team.shopf.RoomDB.FFoodDB;
import com.prm392team.shopf.RoomDB.ProductDAO;

import java.util.ArrayList;
import java.util.List;

public class ItemManagerActivity extends AppCompatActivity {

    private RecyclerView rcv;
    private List<Product> data;
    FFoodDB db;

    private ProductDAO productDAO;

    private Button btnCreate;

    private void bindingView(){
        rcv = findViewById(R.id.rcv_list_item);
        data = new ArrayList<>();
        db = FFoodDB.getInstance(this);
        productDAO = db.productDAO();
        btnCreate = findViewById(R.id.btnAdd);
    }

    private void bindingAction(){
        btnCreate.setOnClickListener(this::onCreateItem);
    }

    private void onCreateItem(View view) {
        Intent intent = new Intent(ItemManagerActivity.this, CreateItemActivity.class);
                        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_manager);

        bindingView();
        bindingAction();
        data = productDAO.getAllProduct();
        ItemAdapter adapter = new ItemAdapter(data);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(adapter);
    }
}