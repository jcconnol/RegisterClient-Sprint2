package edu.uark.uarkregisterapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShoppingCart extends AppCompatActivity {

    Context fileContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        try{
            File file = new File(getString(R.string.internal_file_name));
            FileInputStream fis = new FileInputStream(file);

            //Construct BufferedReader from InputStreamReader
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String line = null;
            while ((line = br.readLine()) != null) {
                //things read from file go into list on screen

            }

            br.close();
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

