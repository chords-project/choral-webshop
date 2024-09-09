package webshop.loopback.cart;

import webshop.loopback.events.EventHandler;
import webshop.common.channel.TypeSymChannel;
import webshop.common.Utils;

public class CartEventHandler@(Sender, Cart) implements EventHandler@(Sender, Cart)<CartEvent> {

    private TypeSymChannel@(Sender, Cart)<CartEvent> ch;

    public CartEventHandler(TypeSymChannel@(Sender, Cart)<CartEvent> ch) {
        this.ch = ch;
    }

    public void on(CartEvent@Sender event) {
        switch (event.getCommand()) {
            case ADD_ITEM -> {
                CartEventAddItem@Sender ev_sender = Utils@Sender.<CartEventAddItem>cast(event);
                CartEventAddItem@Cart ev_cart = ch.<CartEventAddItem>tselect(ev_sender);

                System@Cart.out.println("CART: Add item"@Cart);
            }
            case GET_CART -> {
                CartEventGetCart@Sender ev_sender = Utils@Sender.<CartEventGetCart>cast(event);
                CartEventGetCart@Cart ev_cart = ch.<CartEventGetCart>tselect(ev_sender);

                System@Cart.out.println("CART: Get cart"@Cart);
            }
            case EMPTY_CART -> {
                CartEventEmptyCart@Sender ev_sender = Utils@Sender.<CartEventEmptyCart>cast(event);
                CartEventEmptyCart@Cart ev_cart = ch.<CartEventEmptyCart>tselect(ev_sender);

                System@Cart.out.println("CART: Empty cart"@Cart);
            }
        }
    }
}