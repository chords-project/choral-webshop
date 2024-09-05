package webshop.events;

import java.util.List;

public final class Cart@A {
    public final List@A<CartItem> items;

    public Cart(List@A<CartItem> items) {
        // Should be converted to Collections.unmodifiableList()
        this.items = items;
    }
}