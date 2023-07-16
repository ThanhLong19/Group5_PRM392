package com.prm392team.shopf;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.prm392team.shopf.Adapter.CartAdapter;
import com.prm392team.shopf.Entity.Order;
import com.prm392team.shopf.Entity.Product;
import com.prm392team.shopf.Entity.User;
import com.prm392team.shopf.Helper.ManagementCart;
import com.prm392team.shopf.Interface.ChangeNumberOfItems;
import com.prm392team.shopf.RoomDB.FFoodDB;
import com.prm392team.shopf.RoomDB.OrderDAO;
import com.prm392team.shopf.RoomDB.UserDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerViewList;
    private ManagementCart managementCart;
    private Button checkOut;
    TextView totalFeeTxt, taxTxt, deliveryTxt, totalTxt, emptyTxt, numofPro, emptyTxt2;
    private double tax;
    private ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_list);

        managementCart = new ManagementCart(this);

        bindingView();
        initList();
        CalculateCart();
        bindingAction();
        checkOut();
    }

    private void bindingAction(){
        FloatingActionButton floatingActionButton = findViewById(R.id.fabCart);
        BottomAppBar babHome = findViewById(R.id.babHome);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFinishing() && !isCartListActivityOnTop()) {
                    startActivity(new Intent(CartListActivity.this,CartListActivity.class));
                }
            }
        });
        babHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void checkOut(){
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Product> products = managementCart.getListCart();
                SharedPreferences sharedPreferences = getSharedPreferences("mypref", MODE_PRIVATE);
                String user = sharedPreferences.getString("user", null);
                FFoodDB fFoodDB = FFoodDB.getInstance(CartListActivity.this);
                UserDAO userDAO = fFoodDB.userDAO();
                OrderDAO orderDAO = fFoodDB.orderDAO();
                List<Order> orders = orderDAO.getAllOrder();;
                if(user.length() > 0){
                    User user1 = userDAO.getAccount(user);
                    for (int i=0; i < products.size(); i++){
                        Product product = products.get(i);
                        Order order = new Order();
                        order.setProductId(product.getProductId());
                        order.setOrderAddress("Ha Noi");
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            LocalDateTime currentDateTime = LocalDateTime.now();
                            order.setOrderDate(currentDateTime+"");
                        }
                        order.setUserId(user1.getUserId());
                        order.setStatus(1);

                        Order category7=new Order(product.getProductId(), user1.getUserId(),
                                "10/03/2023", (product.getPrice()* product.getQuantity())
                                , "Dom B, Đại học FPT", 1);
                        orderDAO.insertOrder(category7);

                         orders = orderDAO.getAllOrder();

                    }
                }




                Toast.makeText(CartListActivity.this, "Đặt hàng thành công !", Toast.LENGTH_SHORT).show();

                managementCart.ListClear();
                recreate();
            }
        });
    }



    private void bindingView() {
        recyclerViewList=findViewById(R.id.recycleView);
        totalFeeTxt=findViewById(R.id.totalFeeTxt);
        taxTxt = findViewById(R.id.taxTxt);
        deliveryTxt=findViewById(R.id.deliveryTxt);
        totalTxt=findViewById(R.id.totalTxt);
        emptyTxt=findViewById(R.id.emptyTxt);
        scrollView=findViewById(R.id.scrollView3);
        recyclerViewList = findViewById(R.id.recycleView);
        checkOut = findViewById(R.id.checkOutbtn);
        emptyTxt2 = findViewById(R.id.emptyTxt2);

    }
    private void initList(){
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerViewList.setLayoutManager(linearLayoutManager);
        adapter = new CartAdapter(managementCart.getListCart(), this, new ChangeNumberOfItems() {
            @Override
            public void changed() {
                CalculateCart();
            }
        });

        recyclerViewList.setAdapter(adapter);
        if(managementCart.getListCart().isEmpty()){
            emptyTxt.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.GONE);
        }else{
            emptyTxt.setVisibility(View.GONE);
            scrollView.setVisibility(View.VISIBLE);
        }
    }
    private void CalculateCart(){
        double percentTax = 0.02;
        double delivery = 0;

        tax = Math.round((managementCart.getTotalFee() * percentTax)*100)/100;
        double total = Math.round((managementCart.getTotalFee() + 0 + delivery)*100)/100;
        double itemTotal = Math.round(managementCart.getTotalFee()*100)/100;

        totalFeeTxt.setText("₫" + itemTotal);
        taxTxt.setText("₫" + 0);
        deliveryTxt.setText("₫" + delivery);
        totalTxt.setText("₫" + total);
    }

    private boolean isCartListActivityOnTop() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        if (tasks.size() > 0) {
            String topActivityName = tasks.get(0).topActivity.getClassName();
            return topActivityName.equals(CartListActivity.class.getName());
        }

        return false;
    }
}