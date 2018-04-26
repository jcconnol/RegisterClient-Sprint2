package edu.uark.uarkregisterapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProductSalesReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_sales_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // (Temporary) Testing Stuff
        String[] products = {"Dish Soap", "Mops", "Toilet Paper", "Popcorn", "Ice Cream", "Bananas"};
        String[] numbers = {"1234", "2311","1100", "983", "627", "412"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, products);

        ListView listView = (ListView)findViewById(R.id.prodListView);
        listView.setAdapter(adapter);
    }

}
