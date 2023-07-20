package com.prm392team.shopf.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prm392team.shopf.Adapter.RecyclerViewAdapter;
import com.prm392team.shopf.Entity.Order;
import com.prm392team.shopf.Entity.User;
import com.prm392team.shopf.R;
import com.prm392team.shopf.RoomDB.FFoodDB;
import com.prm392team.shopf.RoomDB.OrderDAO;
import com.prm392team.shopf.RoomDB.UserDAO;

import java.util.ArrayList;
import java.util.List;

public class OrderShippingFragment extends Fragment {

    private RecyclerView rcv;
    private List<Order> data;
    FFoodDB db;
    private OrderDAO orderDAO;
    UserDAO userDAO;
    private void bindingView(){
        rcv = (RecyclerView) getView().findViewById(R.id.rcv_ship);
        data = new ArrayList<>();
        db = FFoodDB.getInstance(getContext());
        orderDAO = db.orderDAO();
        userDAO = db.userDAO();
    }
    private void bindingAction(){
        //data = orderDAO.getAllOrder();
        User user = getUser();
        List<Order> gdata = new ArrayList<Order>();

        gdata = orderDAO.loadAllOrderByUIds(user.getUserId());

        for (int i = 0; i< gdata.size();i++){
            if(gdata.get(i).getStatus()==1){
                data.add(gdata.get(i));
                // Log.d("TAG",String.valueOf( data.size()));
            }
        }
    }
    private User getUser() {

        int getIds= getActivity().getIntent().getIntExtra("userId",0);
        User u = userDAO.getUserByIds(getIds);
        return u;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_shipping, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindingView();
        bindingAction();

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(data,getContext());
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        rcv.setLayoutManager(layoutManager);
        rcv.setAdapter(adapter);
    }
}