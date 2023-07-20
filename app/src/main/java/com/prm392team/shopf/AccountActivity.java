package com.prm392team.shopf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.prm392team.shopf.Entity.User;
import com.prm392team.shopf.R;
import com.prm392team.shopf.RoomDB.FFoodDB;
import com.prm392team.shopf.RoomDB.OrderDAO;
import com.prm392team.shopf.RoomDB.ProductDAO;
import com.prm392team.shopf.RoomDB.UserDAO;

public class AccountActivity extends AppCompatActivity {

    private TextView username;
    private EditText email;
    private EditText phone;
    private EditText dob;
    private Button update;
    private Button cancelUpdate;
    private ImageView imgAccAva;
    FFoodDB db;
    UserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        username = findViewById(R.id.txtUsernameAM);
        email = findViewById(R.id.emailAM);
        phone = findViewById(R.id.phoneAM);
        dob = findViewById(R.id.dobAM);
        update = findViewById(R.id.updateInfor);
        cancelUpdate = findViewById(R.id.btnCancelUpdate);
        imgAccAva = findViewById(R.id.imgAccAva);
        imgAccAva.setImageResource(R.drawable.profile);

        db = FFoodDB.getInstance(this);
        userDAO = db.userDAO();
        int getIds= getIntent().getIntExtra("userId",0);
        User u = userDAO.getUserByIds(getIds);

        username.setText(u.getUsername());
        email.setText(u.getEmail());
        phone.setText(u.getPhone());
        dob.setText(String.valueOf(u.getDob()));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailStr = email.getText().toString().trim();
                String phoneStr = phone.getText().toString().trim();
                String dobStr = dob.getText().toString().trim();
                if(TextUtils.isEmpty(emailStr) || TextUtils.isEmpty(phoneStr) || TextUtils.isEmpty(dobStr)){
                    Toast.makeText(AccountActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                } else{
                    userDAO.updateUser(getIds, emailStr, phoneStr, dobStr);
                    Log.e("user", u.toString());
                    Toast.makeText(getApplicationContext(), "Update profile successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}