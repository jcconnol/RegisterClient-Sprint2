package edu.uark.uarkregisterapp.models.transition;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Vector;
import edu.uark.uarkregisterapp.models.api.Product;

public class ShoppingTransition implements Parcelable {

    private Vector<Product> cartProducts;
    public void setCartProducts(Vector<Product> productsVec){
        for(int i = 0; i < productsVec.size(); i++){
            cartProducts.addElement(productsVec[i]);
        }
    }
    public Vector<Product> getProducts(){
        return cartProducts;
    }

    private Vector<Product> savedProducts;
    public void setSavedProducts(Vector<Product> productsVec){
        for(int i = 0; i < productsVec.size(); i++){
            savedProducts.addElement(productsVec[i]);
        }
    }
    public Vector<Product> getProducts(){
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
        destination.writeArray(this.cartProducts);
        destination.writeArray(this.savedProducts);
    }

    public ShoppingTransition(Parcel shoppingTransitionParcel) {
        this.cartProducts = shoppingTransitionParcel.readArray();
        this.savedProducts = shoppingTransitionParcel.readArray();
    }

    public ShoppingTransition(Product product) {
        cartProducts.addElement(product);
    }

    public ShoppingTransition() {
        savedProducts.clear();
        cartProducts.clear();
    }

}
