package webshop.events;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class CartState@A {
    private HashMap@A<String, List<CartItem>> userItems;

    public CartState() {
        this.userItems = new HashMap@A<String, List<CartItem>>();
    }

    public void addItem(String@A userID, CartItem@A item) {
        System@A.out.println("CART: add item to user: "@A + userID);
        List@A<CartItem> userItems = this.userItems.get(userID);
        if (userItems == null@A) {
            userItems = new ArrayList@A<CartItem>();
        }
        userItems.add(item);
        this.userItems.put(userID, userItems);
    }

    public Cart@A getUserCart(String@A userID) {
        List@A<CartItem> items = this.userItems.get(userID);
        if (items == null@A) {
            items = new ArrayList@A<CartItem>();
            this.userItems.put(userID, items);
        }

        System@A.out.println("CART: get user cart: "@A + items.size() + " items"@A);

        return new Cart@A(items);
    }

    public void emptyCart(String@A userID) {
        userItems.remove(userID);
    }
}