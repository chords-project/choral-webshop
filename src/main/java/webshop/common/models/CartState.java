package webshop.common.models;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class CartState {
    private HashMap<String, List<CartItem>> userItems;

    public CartState() {
        this.userItems = new HashMap<String, List<CartItem>>();
    }

    public void addItem(String userID, CartItem item) {
        System.out.println("CART: add item to user: " + userID);
        List<CartItem> userItems = this.userItems.get(userID);
        if (userItems == null) {
            userItems = new ArrayList<CartItem>();
        }
        userItems.add(item);
        this.userItems.put(userID, userItems);
    }

    public Cart getUserCart(String userID) {
        List<CartItem> items = this.userItems.get(userID);
        if (items == null) {
            items = new ArrayList<CartItem>();
            this.userItems.put(userID, items);
        }

        System.out.println("CART: get user cart: " + items.size() + " items");

        return new Cart(items);
    }

    public void emptyCart(String userID) {
        userItems.remove(userID);
    }
}