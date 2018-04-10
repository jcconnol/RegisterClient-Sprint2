package edu.uark.uarkregisterapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        if(!shoppingCartList.isEmpty()){
            TextView num_in_cart = (TextView)findViewById(R.id.number_in_cart);
            num_in_cart.setText(String.valueOf(shoppingCartList.size()));

            setCartEmptyHidden();



        }

        if(!saveForLaterList.isEmpty()){
            setSaveForLaterEmptyHidden();



        }

    }

    private void setSaveForLaterEmptyHidden(){
        TextView emptyCartText  = (TextView)findViewById(R.id.noItemsInCart);
        emptyCartText.setVisibility(View.INVISIBLE);
    }

    private void setCartEmptyHidden(){
        TextView emptyCartText  = (TextView)findViewById(R.id.noItemsInCart);
        emptyCartText.setVisibility(View.INVISIBLE);
    }

    private void setSaveForLaterEmptyVisible(){
        TextView emptyCartText  = (TextView)findViewById(R.id.noItemsInCart);
        emptyCartText.setVisibility(View.VISIBLE);
    }

    private void setCartEmptyVisible(){
        TextView emptyCartText  = (TextView)findViewById(R.id.noItemsInCart);
        emptyCartText.setVisibility(View.VISIBLE);
    }

}

