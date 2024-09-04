package webshop.choreographic;

import choral.channels.SymChannel;

public class Choreography@(Client, Cart, Billing, Shipping) {
    private ClientState@Client clientState;
    private CartState@Cart cartState;
    private BillingState@Billing billingState;
    private ShippingState@Shipping shippingState;

    private SymChannel@(Client, Cart)<Object> ch_ClientCart;
    private SymChannel@(Client, Shipping)<Object> ch_ClientShipping;
    private SymChannel@(Cart, Billing)<Object> ch_CartBilling;
    private SymChannel@(Billing, Shipping)<Object> ch_BillingShipping;

    public Choreography(
        SymChannel@(Client, Cart)<Object> ch_ClientCart,
        SymChannel@(Client, Shipping)<Object> ch_ClientShipping,
        SymChannel@(Cart, Billing)<Object> ch_CartBilling,
        SymChannel@(Billing, Shipping)<Object> ch_BillingShipping,
        ClientState@Client clientState,
        CartState@Cart cartState,
        BillingState@Billing billingState,
        ShippingState@Shipping shippingState
    ) {
        this.clientState = clientState;
        this.cartState = cartState;
        this.billingState = billingState;
        this.shippingState = shippingState;
        
        this.ch_ClientCart = ch_ClientCart;
        this.ch_ClientShipping = ch_ClientShipping;
        this.ch_CartBilling = ch_CartBilling;
        this.ch_BillingShipping = ch_BillingShipping;
    }

    public void placeOrder() {
        // Client -> Cart -> Billing -> Shipping -> Client

        ClientPlaceOrderReq@Client clientReq = new ClientPlaceOrderReq@Client(clientState.userID);

        ClientPlaceOrderReq@Cart cartPlaceOrder = ch_ClientCart.<ClientPlaceOrderReq>com(clientReq);
        CartPlaceOrderReq@Cart cartReq = new CartPlaceOrderReq@Cart(cartPlaceOrder.userID, cartState.getUserCart(cartPlaceOrder.userID));

        CartPlaceOrderReq@Billing billingPlaceOrder = ch_CartBilling.<CartPlaceOrderReq>com(cartReq);
        Order@Billing orderBilling = billingState.makeOrder(billingPlaceOrder.userID, billingPlaceOrder.cart);

        Order@Shipping orderShipping = ch_BillingShipping.<Order>com(orderBilling);
        ShippedOrder@Shipping shippedOrder = shippingState.shipOrder(orderShipping);

        ShippedOrder@Client shippedOrderClient = ch_ClientShipping.<ShippedOrder>com(shippedOrder);
        clientState.showOrderSummary(shippedOrderClient);
    }
}