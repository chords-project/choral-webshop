package webshop.cart;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

public class CartState@A {
    private HashMap@A<String, List<CartItem>> userItems;

    public CartState() {
        this.userItems = new HashMap@A<String, List<CartItem>>();
    }

    public void addItem(String@A userID, CartItem@A item) {
        List@A<CartItem> userItems = this.userItems.get(userID);
        if (userItems == null@A) {
            userItems = new ArrayList@A<CartItem>();
        }
        userItems.add(item);
        this.userItems.put(userID, userItems);
    }
}