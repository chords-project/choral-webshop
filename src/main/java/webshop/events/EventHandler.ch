package webshop.events;

import webshop.common.channel.TypeSymChannel;
import webshop.common.models.ClientState;
import webshop.common.models.CartState;
import webshop.common.models.BillingState;
import webshop.common.models.ShippingState;
import webshop.common.models.CartItem;
import webshop.common.models.Order;

public class EventHandler@(Client, Cart, Billing, Shipping) {

    ClientState@Client clientState;
    CartState@Cart cartState;
    BillingState@Billing billingState;
    ShippingState@Shipping shippingState;

    TypeSymChannel@(Client, Cart)<Event> ch_clientCart;
    TypeSymChannel@(Client, Shipping)<Event> ch_clientShipping;
    TypeSymChannel@(Cart, Billing)<Event> ch_cartBilling;
    TypeSymChannel@(Billing, Shipping)<Event> ch_billingShipping;

    public EventHandler(
        TypeSymChannel@(Client, Cart)<Event> ch_clientCart,
        TypeSymChannel@(Client, Shipping)<Event> ch_clientShipping,
        TypeSymChannel@(Cart, Billing)<Event> ch_cartBilling,
        TypeSymChannel@(Billing, Shipping)<Event> ch_billingShipping,
        ClientState@Client clientState,
        CartState@Cart cartState,
        BillingState@Billing billingState,
        ShippingState@Shipping shippingState
    ) {
        this.ch_clientCart = ch_clientCart;
        this.ch_clientShipping = ch_clientShipping;
        this.ch_cartBilling = ch_cartBilling;
        this.ch_billingShipping = ch_billingShipping;

        this.clientState = clientState;
        this.cartState = cartState;
        this.billingState = billingState;
        this.shippingState = shippingState;
    }

    EventResult@(Client, Cart, Billing, Shipping) on(Event@Client event) {
        switch (event.getCommand()) {
            case PLACE_ORDER -> {
                System@Client.out.println("Client perform event: PLACE_ORDER"@Client);

                // Client -> Cart -> Billing -> Shipping -> Client
                EventPlaceOrder@Client ev_client = Utils@Client.<EventPlaceOrder>cast(event);
                ev_client.addUserID(clientState.userID);

                EventPlaceOrder@Cart ev_cart = ch_clientCart.<EventPlaceOrder>tselect(ev_client);
                ev_cart.addUserCart(cartState.getUserCart(ev_cart.userID));

                EventPlaceOrder@Billing ev_billing = ch_cartBilling.<EventPlaceOrder>tselect(ev_cart);
                Order@Billing order = billingState.makeOrder(ev_billing.userID, ev_billing.cart);
                ev_billing.addOrder(order);

                EventPlaceOrder@Shipping ev_shipping = ch_billingShipping.<EventPlaceOrder>tselect(ev_billing);
                String@Shipping address = shippingState.shipOrder(ev_shipping.order);
                ev_shipping.addShipment(address);

                EventPlaceOrder@Client result = ch_clientShipping.<EventPlaceOrder>tselect(ev_shipping);
                clientState.showOrderSummary(result.order);
            }
            case ADD_ITEM -> {
                System@Client.out.println("Client perform event: ADD_ITEM"@Client);

                // client -> cart
                EventAddItem@Client ev_client = Utils@Client.<EventAddItem>cast(event);
                ev_client.addUserID(clientState.userID);

                EventAddItem@Cart ev_cart = ch_clientCart.<EventAddItem>tselect(ev_client);
                this.cartState.addItem(ev_cart.userID, ev_cart.item);

                // Would be nice to get rid of these lines somehow.
                // Since the `on` method loops, these messages could be ignored and the choreographies would still align.
                EventAddItem@Billing ev_billing = ch_cartBilling.<EventAddItem>tselect(ev_cart);
                EventAddItem@Shipping ev_shipping = ch_billingShipping.<EventAddItem>tselect(ev_billing);
            }
            case TERMINATE -> {
                System@Client.out.println("Client perform event: TERMINATE"@Client);

                // Knowledge of choice
                EventTerminate@Client ev_client = Utils@Client.<EventTerminate>cast(event);
                EventTerminate@Cart ev_cart = ch_clientCart.<EventTerminate>tselect(ev_client);
                EventTerminate@Billing ev_billing = ch_cartBilling.<EventTerminate>tselect(ev_cart);
                EventTerminate@Shipping ev_shipping = ch_billingShipping.<EventTerminate>tselect(ev_billing);

                return new EventResult@(Client, Cart, Billing, Shipping)(true@Client, true@Cart, true@Billing, true@Shipping);
            }
        }

        return new EventResult@(Client, Cart, Billing, Shipping)(false@Client, false@Cart, false@Billing, false@Shipping);
    }
}