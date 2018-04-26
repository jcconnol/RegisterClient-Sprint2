package edu.uark.uarkregisterapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

import edu.uark.uarkregisterapp.adapters.SavedCartListAdapter;
import edu.uark.uarkregisterapp.adapters.ShoppingCartListAdapter;
import edu.uark.uarkregisterapp.models.api.ApiResponse;
import edu.uark.uarkregisterapp.models.api.Product;
import edu.uark.uarkregisterapp.models.api.services.ProductService;
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
        if(intent.getExtras() != null) {
            shoppingTransition = intent.getParcelableExtra("ShoppingTransition");
            shoppingCartList = shoppingTransition.getShopProducts();
            saveForLaterList = shoppingTransition.getSavedProducts();

            ListView cartListView = findViewById(R.id.shopping_cart_list);
            ListView savedListView = findViewById(R.id.saved_items_list);

            cartArrayAdapter = new ShoppingCartListAdapter(this, this.shoppingCartList);
            savedArrayAdapter = new SavedCartListAdapter(this, this.saveForLaterList);

            if(!shoppingCartList.isEmpty()){
                TextView num_in_cart = findViewById(R.id.number_in_cart);

                num_in_cart.setText(String.valueOf(shoppingCartList.size()));

                setCartEmptyHidden();
                cartListView.setAdapter(cartArrayAdapter);

                //TODO SET SAVED STUFF TEXTVIEW BELOW LIST, POPULATE LIST
                populateSavedList();
                populateShoppingCartList();

            }

            if(!saveForLaterList.isEmpty()){
                setSaveForLaterEmptyHidden();
                savedListView.setAdapter(savedArrayAdapter);
            }
        }
    }

    public void purchaseOnClick(){
        super.onResume();
        (new ShoppingCart.PurchaseProductTask()).execute();
    }

    public void deleteFromCart(Product elem){
        shoppingCartList.remove(elem);
        shoppingCartList.notify();
    }

    public void saveForLater(Product elem){
        int found = cartArrayAdapter.getPosition(elem);
        saveForLaterList.add(shoppingCartList.get(found));
        saveForLaterList.notify();
        shoppingCartList.notify();
    }

    public void moveToCart(Product elem){
        int found = savedArrayAdapter.getPosition(elem);
        shoppingCartList.add(saveForLaterList.get(found));
        shoppingCartList.notify();
        saveForLaterList.notify();
    }

    private void populateShoppingCartList(ListView listView, ArrayAdapter arrayAdapter) {
       // ArrayAdapter<String> newArrayAdapter = new ArrayAdapter<String>(
        //        this,
        //        R.id.shopping_cart_list);


    }

    private void populateSavedList(ListView listView, List<String> arrayAdapter, Layout layout) {
        /*ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                layout,
                arrayAdapter);*/

        //listView.setAdapter(arrayAdapter);
    }

    private class PurchaseProductTask extends AsyncTask<Void, Void, ApiResponse<List<Product>>> {
        @Override
        protected void onPreExecute() {
            this.purchaseProductAlert = new AlertDialog.Builder(ShoppingCart.this)
                    .setMessage(R.string.alert_dialog_employee_create)
                    .create();
            this.purchaseProductAlert.show();
        }

        @Override
        protected ApiResponse<List<Product>> doInBackground(Void... params) {
            ApiResponse<List<Product>> apiResponse = (new ProductService()).getProducts();

            if (apiResponse.isValidResponse()) {
                shoppingCartList.clear();
                shoppingCartList.addAll(apiResponse.getData());
            }

            return apiResponse;
        }

        @Override
        protected void onPostExecute(ApiResponse<List<Product>> apiResponse) {
            if (apiResponse.isValidResponse()) {
                //cartArrayAdapter.notifyDataSetChanged();
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

    private ListView getProductsListView() {
        return (ListView) this.findViewById(R.id.list_view_products);
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

