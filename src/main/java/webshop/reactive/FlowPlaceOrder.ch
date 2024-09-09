package webshop.reactive;

import choral.channels.DiChannel;

import webshop.common.models.*;

public class FlowPlaceOrder@(Client, Cart, Billing) {
    ClientState@Client clientState;
    CartState@Cart cartState;
    BillingState@Billing billingState;

    DiChannel@(Client, Cart)<Object> ch_clientCart;
    DiChannel@(Cart, Billing)<Object> ch_cartBilling;
    DiChannel@(Billing, Client)<Object> ch_billingClient;

    public FlowPlaceOrder(
        ClientState@Client clientState,
        CartState@Cart cartState,
        BillingState@Billing billingState,
        DiChannel@(Client, Cart)<Object> ch_clientCart,
        DiChannel@(Cart, Billing)<Object> ch_cartBilling,
        DiChannel@(Billing, Client)<Object> ch_billingClient
    ) {
        this.clientState = clientState;
        this.cartState = cartState;
        this.billingState = billingState;
        
        this.ch_clientCart = ch_clientCart;
        this.ch_cartBilling = ch_cartBilling;
        this.ch_billingClient = ch_billingClient;
    }

    public Order@Client placeOrder() {
        System@Client.out.println("CLIENT: Place order started"@Client);
        System@Cart.out.println("CART: Place order started"@Cart);
        System@Billing.out.println("BILLING: Place order started"@Billing);

        String@Cart userID_cart = ch_clientCart.<String>com(clientState.userID);

        Cart@Cart userCart_cart = cartState.getUserCart(userID_cart);
        
        UserCart@Billing userCart_billing = ch_cartBilling.<UserCart>com(new UserCart@Cart(userID_cart, userCart_cart));
        Order@Billing order_billing = billingState.makeOrder(userCart_billing.userID, userCart_billing.cart);

        Order@Client order_client = ch_billingClient.<Order>com(order_billing);

        System@Client.out.println("CLIENT: Place order done"@Client);
        System@Cart.out.println("CART: Place order done"@Cart);
        System@Billing.out.println("BILLING: Place order done"@Billing);

        return order_client;
    }
}