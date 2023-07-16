package com.prm392team.shopf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.prm392team.shopf.Entity.Category;
import com.prm392team.shopf.Entity.Product;
import com.prm392team.shopf.RoomDB.FFoodDB;

import java.util.ArrayList;
import java.util.List;

public class UpdateItemActivity extends AppCompatActivity {

    int productID;

    EditText productName;
    EditText productQuantity;
    EditText productDescription;
    EditText productPrice;
    EditText productDiscount;
    EditText productImage;
    EditText productCategory;

    Button btnUpdateItem;

    FFoodDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        bindingView();
        Intent intent = getIntent();
        productID = intent.getIntExtra("id", 0);
        Product product = new Product();
        product = db.productDAO().getProductByIds(productID);
        productName.setText(product.getProductName());
        productQuantity.setText(String.valueOf(product.getQuantity()));
        productDescription.setText(product.getDescription());
        productPrice.setText(String.valueOf(product.getPrice()));
        productDiscount.setText(String.valueOf(product.getDiscount()));
        productImage.setText(product.getImage());
        productCategory.setText(String.valueOf(product.getCategoryIds()));

        btnUpdateItem = findViewById(R.id.btnUpdateProduct);

        btnUpdateItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateItemActivity.this, ItemManagerActivity.class));
                Product product = new Product();

                String name = productName.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(UpdateItemActivity.this, "Error Product Name" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateItemActivity.this, CreateItemActivity.class));
                }

                int quantity = 0;
                try {
                    quantity = Integer.parseInt(productQuantity.getText().toString());
                }catch (Exception ex){
                    Toast.makeText(UpdateItemActivity.this, "Error Product Quantity" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateItemActivity.this, CreateItemActivity.class));
                }

                String description = productDescription.getText().toString();
                if(description.isEmpty()){
                    Toast.makeText(UpdateItemActivity.this, "Error Product Description" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateItemActivity.this, CreateItemActivity.class));
                }

                double price = 0;
                try {
                    price = Double.parseDouble(productPrice.getText().toString());
                }catch (Exception ex){
                    Toast.makeText(UpdateItemActivity.this, "Error Product Price" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateItemActivity.this, CreateItemActivity.class));
                }

                int categoryID = 0;
                try {
                    categoryID = Integer.parseInt(productCategory.getText().toString());
                }catch (Exception ex){
                    Toast.makeText(UpdateItemActivity.this, "Error Category" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateItemActivity.this, CreateItemActivity.class));
                }

                int count = 0;
                List<Category> listCate = new ArrayList<Category>();
                listCate = db.categoryDAO().getAllCategory();
                for (int i=0; i <= listCate.size(); i++){
                    if(listCate.get(i).getCategoryId() == categoryID){
                        count++;
                        break;
                    }
                }

                if(count == 0){
                    Toast.makeText(UpdateItemActivity.this, "Can not add Product with Category = " +  categoryID, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateItemActivity.this, CreateItemActivity.class));
                }
                product.setProductId(productID);
                product.setProductName(productName.getText().toString());
                product.setDescription(productDescription.getText().toString());
                product.setPrice(price);
                product.setQuantity(quantity);
                product.setCategoryIds(categoryID);
                product.setDescription(productDiscount.getText().toString());
                product.setImage(productImage.getText().toString());
                db.productDAO().update(product);
            }
        });

    }

    private void bindingView(){

        productName = findViewById(R.id.itemName);
        productQuantity = findViewById(R.id.itemQuantity);
        productDescription = findViewById(R.id.itemDescription);
        productPrice = findViewById(R.id.itemPrice);
        productDiscount = findViewById(R.id.itemDiscount);
        productImage = findViewById(R.id.itemImage);
        productCategory = findViewById(R.id.itemCategory);
        db = FFoodDB.getInstance(this);
    }

}