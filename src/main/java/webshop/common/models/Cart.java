package webshop.common.models;

import java.util.List;

public final class Cart {
    public final List<CartItem> items;

    public Cart(List<CartItem> items) {
        // Should be converted to Collections.unmodifiableList()
        this.items = items;
    }
}