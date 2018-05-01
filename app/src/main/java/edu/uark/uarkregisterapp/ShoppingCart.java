package edu.uark.uarkregisterapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import edu.uark.uarkregisterapp.adapters.ProductListAdapter;
import edu.uark.uarkregisterapp.adapters.SavedCartListAdapter;
import edu.uark.uarkregisterapp.adapters.ShoppingCartListAdapter;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Employee;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.enums.EmployeeClassification;
import edu.uark.uarkregisterapp.models.api.services.EmployeeService;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
import edu.uark.uarkregisterapp.models.transition.EmployeeTransition;
import edu.uark.uarkregisterapp.models.transition.ShoppingTransition;

public class ShoppingCart extends AppCompatActivity {

    Intent intent;
    ShoppingTransition shoppingTransition;
    List<Product> shoppingCartList;
    List<Product> saveForLaterList;
    ShoppingCartListAdapter cartArrayAdapter;
    SavedCartListAdapter savedArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        intent = getIntent();
        this.shoppingCartList = new ArrayList<>();
        this.saveForLaterList = new ArrayList<>();

        //if(intent.getExtras() != null) {
            //shoppingTransition = intent.getParcelableExtra("ShoppingTransition");
            //shoppingCartList = shoppingTransition.getShopProducts();
            //saveForLaterList = shoppingTransition.getSavedProducts();

            //ListView cartListView = findViewById(R.id.shopping_cart_list);
            //ListView savedListView = findViewById(R.id.saved_items_list);

            Product holder = new Product();
            holder.setLookupCode("whatever");

            shoppingCartList.add(holder);
            //shoppingCartList.add(holder);

            saveForLaterList.add(holder);
            //saveForLaterList.add(holder);

            cartArrayAdapter = new ShoppingCartListAdapter(this, this.shoppingCartList);
            savedArrayAdapter = new SavedCartListAdapter(this, this.saveForLaterList);

            if (!shoppingCartList.isEmpty()) {
                setCartEmptyHidden();
                populateShoppingCartList();
            }

            if (!saveForLaterList.isEmpty()) {
                setSaveForLaterEmptyHidden();
                populateSavedList();
            }
        //}
    }

    public void purchaseOnClick(View view){
        super.onResume();
        for(int i = 0; i < shoppingCartList.size(); i++) {
            (new ShoppingCart.PurchaseProductTask()).execute();
        }
    }

    public void deleteFromCart(View view){
        Product elem = (new Product()).setLookupCode(view.getTag().toString());
        shoppingCartList.remove(elem);
        cartArrayAdapter.notifyDataSetChanged();
    }

    public void saveForLater(View view){
        Product elem = (new Product()).setLookupCode(view.getTag().toString());
        int found = cartArrayAdapter.getPosition(elem);
        saveForLaterList.add(shoppingCartList.get(found));
        cartArrayAdapter.notifyDataSetChanged();
        savedArrayAdapter.notifyDataSetChanged();
    }

    public void moveToCart(View view){
        Product elem = (new Product()).setLookupCode(view.getTag().toString());
        int found = savedArrayAdapter.getPosition(elem);
        shoppingCartList.add(saveForLaterList.get(found));
        cartArrayAdapter.notifyDataSetChanged();
        savedArrayAdapter.notifyDataSetChanged();
    }

    private void populateShoppingCartList() {
        ListView cartListView = findViewById(R.id.shopping_cart_list);
        this.cartArrayAdapter = new ShoppingCartListAdapter(this, this.shoppingCartList);

        cartListView.setAdapter(this.cartArrayAdapter);
        cartArrayAdapter.notifyDataSetChanged();
    }

    private void populateSavedList() {
        ListView savedListView = findViewById(R.id.saved_items_list);
        this.savedArrayAdapter = new SavedCartListAdapter(this, this.saveForLaterList);

        savedListView.setAdapter(this.savedArrayAdapter);
        savedArrayAdapter.notifyDataSetChanged();
    }

    private class PurchaseProductTask extends AsyncTask<Product, Void, ApiResponse<Product>> {
        @Override
        protected void onPreExecute() {
            this.purchaseProductAlert = new AlertDialog.Builder(ShoppingCart.this)
                    .setMessage(R.string.alert_dialog_product_create)
                    .create();
            this.purchaseProductAlert.show();
        }

        @Override
        protected ApiResponse<Product> doInBackground(Product... product) {
            if (product.length > 0) {
                return (new ProductService()).createProduct(product[0]);
            } else {
                return (new ApiResponse<Product>())
                        .setValidResponse(false);
            }
        }

        @Override
        protected void onPostExecute(ApiResponse<Product> apiResponse) {
            if (apiResponse.isValidResponse()) {
                cartArrayAdapter.notifyDataSetChanged();
            }

            this.purchaseProductAlert.dismiss();

            if (!apiResponse.isValidResponse()) {
                new AlertDialog.Builder(ShoppingCart.this).
                        setMessage(R.string.alert_dialog_products_load_failure).
                        setPositiveButton(
                                R.string.button_dismiss,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                }
                        ).
                        create().
                        show();
            }
        }

        private AlertDialog purchaseProductAlert;
    }

    private EditText getLookUpCodeTextView() {
        return (EditText) this.findViewById(R.id.shopping_list_view_item_product_name);
    }

    private EditText getCountTextView() {
        return (EditText) this.findViewById(R.id.shopping_cart_item_qty_num);
    }

    private ListView getProductsListView() {
        return (ListView) this.findViewById(R.id.list_view_products);
    }

    private void setSaveForLaterEmptyHidden(){
        TextView emptyCartText  = (TextView)findViewById(R.id.noItemsSaved);
        emptyCartText.setVisibility(View.INVISIBLE);
    }

    private void setCartEmptyHidden(){
        TextView emptyCartText  = (TextView)findViewById(R.id.noItemsInCart);
        emptyCartText.setVisibility(View.INVISIBLE);
    }

    private void setSaveForLaterEmptyVisible(){
        TextView emptyCartText  = (TextView)findViewById(R.id.noItemsSaved);
        emptyCartText.setVisibility(View.VISIBLE);
    }

    private void setCartEmptyVisible(){
        TextView emptyCartText  = (TextView)findViewById(R.id.noItemsInCart);
        emptyCartText.setVisibility(View.VISIBLE);
    }
}

