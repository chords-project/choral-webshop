package webshop.orchestrated;

import webshop.orchestrated.checkout.CheckoutService;
import webshop.orchestrated.checkout.messages.PlaceOrderReq;
import webshop.orchestrated.checkout.messages.PlaceOrderRes;

// Does not seem possible to "implement Runnable@(Frontend, Cart)" ??

class Webshop@(Frontend, Cart, Checkout) {
    private CheckoutService@(Frontend, Cart, Checkout) checkoutService;

    public Webshop(CheckoutService@(Frontend, Cart, Checkout) checkoutService) {
        this.checkoutService = checkoutService;
    }

    public void run() {
        System@Frontend.out.println("FRONTEND: started"@Frontend);
        System@Cart.out.println("CART: started"@Cart);
        System@Checkout.out.println("CHECKOUT: started"@Checkout);

        PlaceOrderReq@Frontend req = new PlaceOrderReq@Frontend("1000"@Frontend, "USD"@Frontend, "Campusvej 55"@Frontend, "test@example.com"@Frontend, "555-444-333-222"@Frontend);
        PlaceOrderRes@Frontend res = checkoutService.placeOrder(req);

        System@Frontend.out.println("FRONTEND: Order placed for items: "@Frontend + res.items);

        System@Frontend.out.println("FRONTEND: done"@Frontend);
        System@Cart.out.println("CART: done"@Cart);
        System@Checkout.out.println("CHECKOUT: done"@Checkout);
    }
}