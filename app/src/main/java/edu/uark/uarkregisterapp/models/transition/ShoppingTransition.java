package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.Vector;
import edu.uark.uarkregisterapp.models.api.Product;

public class ShoppingTransition implements Parcelable {

    private List<Product> cartProducts;
    public void setCartProducts(List<Product> productsList){
        for(int i = 0; i < productsList.size(); i++){
            cartProducts.add(productsList.get(i));
        }
    }
    public List<Product> getShopProducts(){
        return cartProducts;
    }

    private List<Product> savedProducts;
    public void setSavedProducts(List<Product> productsList){
        for(int i = 0; i < productsList.size(); i++){
            savedProducts.add(productsList.get(i));
        }
    }
    public List<Product> getSavedProducts(){
        return savedProducts;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ShoppingTransition> CREATOR = new Parcelable.Creator<ShoppingTransition>() {
        public ShoppingTransition createFromParcel(Parcel ShoppingTransitionParcel) {
            return new ShoppingTransition(ShoppingTransitionParcel);
        }

        public ShoppingTransition[] newArray(int size) {
            return new ShoppingTransition[size];
        }
    };

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeList(this.cartProducts);
        destination.writeList(this.savedProducts);
    }

    public ShoppingTransition(Parcel shoppingTransitionParcel) {
        shoppingTransitionParcel.readList(cartProducts, Object.class.getClassLoader());
        shoppingTransitionParcel.readList(savedProducts, Object.class.getClassLoader());
    }

    public ShoppingTransition(Product product) {
        cartProducts.add(product);
    }

    public ShoppingTransition() {
        savedProducts.clear();
        cartProducts.clear();
    }
}
