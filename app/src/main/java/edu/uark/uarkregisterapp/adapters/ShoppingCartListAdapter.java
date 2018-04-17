package edu.uark.uarkregisterapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import edu.uark.uarkregisterapp.R;
import edu.uark.uarkregisterapp.models.api.Product;

public class ShoppingCartListAdapter extends ArrayAdapter<Product> {
    //TODO IMPLEMENT SHOPPING CART PRODUCT ADAPTER
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        return convertView;
    }

    public ShoppingCartListAdapter(Context context, List<Product> products) {
        super(context, R.layout.list_view_item_product, products);
    }

}
