package webshop.checkout;

import java.util.List;

import choral.channels.SymChannel;

import webshop.cart.CartItem;
import webshop.cart.CartService;
import webshop.checkout.messages.CheckoutMessage;
import webshop.checkout.messages.PlaceOrderReq;
import webshop.checkout.messages.PlaceOrderRes;

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