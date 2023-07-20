package com.prm392team.shopf;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.prm392team.shopf.Adapter.RecyclerViewAdapter;
import com.prm392team.shopf.Entity.Order;
import com.prm392team.shopf.Entity.User;
import com.prm392team.shopf.RoomDB.FFoodDB;
import com.prm392team.shopf.RoomDB.OrderDAO;
import com.prm392team.shopf.RoomDB.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private List<Order> data;
    FFoodDB db;
    private OrderDAO orderDAO;
    UserDAO userDAO;
    Button btncancer;
    private void bindingView(){
        rcv = findViewById(R.id.rcv_order_detail);
        data = new ArrayList<>();
        db = FFoodDB.getInstance(this);
        orderDAO = db.orderDAO();
        userDAO = db.userDAO();
    }
    private void bindingAction(){
        //data = orderDAO.getAllOrder();
        User user = getUser();
        data = orderDAO.loadAllOrderByUIds(user.getUserId());


    }

    private User getUser() {

        int getIds= getIntent().getIntExtra("userId",1);
        User u = userDAO.getUserByIds(1);
        return u;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        bindingView();
        bindingAction();
         data = FFoodDB.getInstance(this).orderDAO().getAllOrder();
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data,this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(adapter);

        btncancer = findViewById(R.id.btnCancer);
//        btncancer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }
}