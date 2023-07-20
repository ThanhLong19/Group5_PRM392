package com.prm392team.shopf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prm392team.shopf.Entity.User;
import com.prm392team.shopf.RoomDB.FFoodDB;
import com.prm392team.shopf.RoomDB.UserDAO;

public class ChangePwActivity extends AppCompatActivity {
    private EditText newPw, reNewPw;
    private Button saveBt;
    private User user;
    private FFoodDB db;
    private UserDAO userDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pw);
        newPw = findViewById(R.id.password);
        reNewPw = findViewById(R.id.rePassword);
        saveBt = findViewById(R.id.saveBt);

        db = FFoodDB.getInstance(this);
        userDAO = db.userDAO();
        int getIds= getIntent().getIntExtra("userId",0);
        User user = userDAO.getUserByIds(getIds);

        saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPwStr = newPw.getText().toString().trim();
                String rePwStr = reNewPw.getText().toString().trim();
                if(newPwStr.isEmpty() || rePwStr.isEmpty()){
                    Toast.makeText(ChangePwActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                    newPw.setText("");
                    reNewPw.setText("");
                } else{
                    if(!newPwStr.equals(rePwStr)){
                        Toast.makeText(ChangePwActivity.this, "Password are not matching!", Toast.LENGTH_SHORT).show();
                        newPw.setText("");
                        reNewPw.setText("");
                    } else{
                        userDAO.changePw(newPwStr, getIds);
                        Toast.makeText(ChangePwActivity.this, "Change password successfully", Toast.LENGTH_SHORT).show();
                        newPw.setText("");
                        reNewPw.setText("");

                    }
                }
            }
        });
    }
}