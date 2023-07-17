package com.prm392team.shopf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

    Button btnUpdateItem;

    FFoodDB db;

    List<Category> listCate;
    String[] categorys;
    int[] cateIDs;

    String selectedCateName;
    int selectedCateID;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_item);
        bindingView();

        categorys = new String[listCate.size()];
        cateIDs = new int[listCate.size()];

        for (int i=0; i<listCate.size(); i++){
            categorys[i] = listCate.get(i).getCategoryName();
            cateIDs[i] = listCate.get(i).getCategoryId();
        }
        arrayAdapter = new ArrayAdapter<String>(this, R.layout.list_item_category, categorys);
        autoCompleteTextView.setAdapter(arrayAdapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCateName = parent.getItemAtPosition(position).toString();
                selectedCateID = cateIDs[position];
                Toast.makeText(UpdateItemActivity.this, "Item: " + selectedCateName, Toast.LENGTH_SHORT).show();
            }
        });

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

                double discount = 0;
                try {
                    discount = Double.parseDouble(productDiscount.getText().toString());
                    if(discount > 0){
                        Toast.makeText(UpdateItemActivity.this, "Discount < 1" , Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateItemActivity.this, CreateItemActivity.class));
                    }
                }catch (Exception ex){
                    Toast.makeText(UpdateItemActivity.this, "Error Discount" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateItemActivity.this, CreateItemActivity.class));
                }

                product.setProductId(productID);
                product.setProductName(productName.getText().toString());
                product.setDescription(productDescription.getText().toString());
                product.setPrice(price);
                product.setQuantity(quantity);
                product.setCategoryIds(selectedCateID);
                product.setDiscount(discount);
                product.setImage(productImage.getText().toString());
                db.productDAO().update(product);
            }
        });

    }

    private void bindingView(){

        db = FFoodDB.getInstance(this);

        productName = findViewById(R.id.itemName);
        productQuantity = findViewById(R.id.itemQuantity);
        productDescription = findViewById(R.id.itemDescription);
        productPrice = findViewById(R.id.itemPrice);
        productDiscount = findViewById(R.id.itemDiscount);
        productImage = findViewById(R.id.itemImage);

        listCate = db.categoryDAO().getAllCategory();
        autoCompleteTextView = findViewById(R.id.autoItemCategory);
    }

}