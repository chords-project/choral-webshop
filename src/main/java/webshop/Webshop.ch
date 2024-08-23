package webshop;

import java.lang.Runnable;
import webshop.frontend.FrontendState;
import webshop.cart.CartState;

// Does not seem possible to "implement Runnable@(Frontend, Cart)" ??

class Webshop@(Frontend, Cart) {
    private FrontendState@Frontend frontendState;
    private CartState@Cart cartState;

    public Webshop(FrontendState@Frontend frontendState, CartState@Cart cartState) {
        this.frontendState = frontendState;
        this.cartState = cartState;
    }

    public void run() {
        System@Frontend.out.println("Frontend service started"@Frontend);
        System@Cart.out.println("Cart service started"@Cart);
    }
}