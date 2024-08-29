package webshop.orchestrated.checkout;

import java.util.List;

import choral.channels.SymChannel;

import webshop.orchestrated.cart.CartItem;
import webshop.orchestrated.cart.CartService;
import webshop.orchestrated.checkout.messages.CheckoutMessage;
import webshop.orchestrated.checkout.messages.PlaceOrderReq;
import webshop.orchestrated.checkout.messages.PlaceOrderRes;

public class CheckoutService@(Client, Cart, Checkout) {
    private SymChannel@(Client, Checkout)<Object> ch_CheckoutClient;
    private CartService@(Cart, Checkout) cartService;

    public CheckoutService(
        SymChannel@(Client, Checkout)<Object> ch_CheckoutClient,
        CartService@(Cart, Checkout) cartService) {
        this.ch_CheckoutClient = ch_CheckoutClient;
        this.cartService = cartService;
    }

    public PlaceOrderRes@Client placeOrder(PlaceOrderReq@Client req) {
        PlaceOrderReq@Checkout sReq = ch_CheckoutClient.<PlaceOrderReq>com(req);

        List@Checkout<CartItem> orderItems = cartService.getCart(sReq.userID);

        PlaceOrderRes@Checkout res = new PlaceOrderRes@Checkout("1234"@Checkout, sReq.address, orderItems);
        return ch_CheckoutClient.<PlaceOrderRes>com(res);
    }
}