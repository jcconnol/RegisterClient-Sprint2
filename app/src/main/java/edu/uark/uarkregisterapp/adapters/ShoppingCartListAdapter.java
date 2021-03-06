package edu.uark.uarkregisterapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            view = inflater.inflate(R.layout.shopping_cart_item_list, parent, false);
        }

        Product product = this.getItem(position);
        if (product != null) {
            TextView lookupCodeTextView = (TextView) view.findViewById(R.id.shopping_list_view_item_product_name);
            if (lookupCodeTextView != null) {
                lookupCodeTextView.setText(product.getLookupCode());
            }

            TextView countTextView = (TextView) view.findViewById(R.id.shopping_cart_item_qty_num);
            if (countTextView != null) {
                countTextView.setText(String.format(Locale.getDefault(), "%d", 1));
            }
        }

        return view;
    }

    public ShoppingCartListAdapter(Context context, List<Product> products) {
        super(context, R.layout.shopping_cart_item_list, products);
    }
}
