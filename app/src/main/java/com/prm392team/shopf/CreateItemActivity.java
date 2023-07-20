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

public class CreateItemActivity extends AppCompatActivity {

    EditText productName;
    EditText productQuantity;
    EditText productDescription;
    EditText productPrice;
    EditText productDiscount;
    EditText productImage;
    Button btnCreate;
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
        setContentView(R.layout.activity_create_item);

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
                Toast.makeText(CreateItemActivity.this, "Item: " + selectedCateName, Toast.LENGTH_SHORT).show();
            }
        });


        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    startActivity(new Intent(CreateItemActivity.this, ItemManagerActivity.class));
                Product product = new Product();

                String name = productName.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(CreateItemActivity.this, "Error Product Name" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateItemActivity.this, CreateItemActivity.class));
                }

                int quantity = 0;
                try {
                    quantity = Integer.parseInt(productQuantity.getText().toString());
                }catch (Exception ex){
                    Toast.makeText(CreateItemActivity.this, "Error Product Quantity" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateItemActivity.this, CreateItemActivity.class));
                }

                String description = productDescription.getText().toString();
                if(description.isEmpty()){
                    Toast.makeText(CreateItemActivity.this, "Error Product Description" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateItemActivity.this, CreateItemActivity.class));
                }

                double price = 0;
                try {
                    price = Double.parseDouble(productPrice.getText().toString());
                }catch (Exception ex){
                    Toast.makeText(CreateItemActivity.this, "Error Product Price" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateItemActivity.this, CreateItemActivity.class));
                }

                double discount = 0;
                try {
                    discount = Double.parseDouble(productDiscount.getText().toString());
                    if(discount > 0){
                        Toast.makeText(CreateItemActivity.this, "Discount < 1" , Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateItemActivity.this, CreateItemActivity.class));
                    }
                }catch (Exception ex){
                    Toast.makeText(CreateItemActivity.this, "Error Discount" , Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(CreateItemActivity.this, CreateItemActivity.class));
                }

                product.setProductName(productName.getText().toString());
                product.setQuantity(quantity);
                product.setDescription(productDescription.getText().toString());
                product.setPrice(price);
                product.setDiscount(discount);
                product.setImage(productImage.getText().toString());
                product.setCategoryIds(selectedCateID);
                db.productDAO().insertProduct(product);
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
//        productCategory = findViewById(R.id.itemCategory);

        listCate = db.categoryDAO().getAllCategory();

        autoCompleteTextView = findViewById(R.id.autolistItemCategory);

    }
}