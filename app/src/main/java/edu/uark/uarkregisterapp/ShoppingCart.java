package edu.uark.uarkregisterapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.transition.ShoppingTransition;

public class ShoppingCart extends AppCompatActivity {

    Context fileContext;
    Intent intent;
    ShoppingTransition shoppingTransition;
    Vector<Product> shoppingCartList;
    Vector<Product> saveForLaterList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        intent = getIntent();
        shoppingTransition = intent.getParcelableExtra("ShoppingTransition");
        shoppingCartList = shoppingTransition.getShopProducts();
        saveForLaterList = shoppingTransition.getSavedProducts();

        

    }


}

