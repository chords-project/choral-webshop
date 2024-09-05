package webshop.events;

import choral.runtime.Media.MessageQueue;
import choral.utils.Pair;
import webshop.events.channel.LocalTypeChannel;
import java.util.List;
import java.lang.Runnable;

public class Main {
    public static void main(String[] args) {
        System.out.println("Events example");

        Pair<LocalTypeChannel<Event>, LocalTypeChannel<Event>> ch_clientCart = makeChannel();
        Pair<LocalTypeChannel<Event>, LocalTypeChannel<Event>> ch_clientShipping = makeChannel();
        Pair<LocalTypeChannel<Event>, LocalTypeChannel<Event>> ch_cartBilling = makeChannel();
        Pair<LocalTypeChannel<Event>, LocalTypeChannel<Event>> ch_billingShipping = makeChannel();

        EventHandler_Client eventHandler_client = new EventHandler_Client(
                ch_clientCart.left(),
                ch_clientShipping.left(),
                new ClientState("client1"));

        EventHandler_Cart eventHandler_cart = new EventHandler_Cart(
                ch_clientCart.right(),
                ch_cartBilling.left(),
                new CartState());

        EventHandler_Billing eventHandler_billing = new EventHandler_Billing(
                ch_cartBilling.right(),
                ch_billingShipping.left(),
                new BillingState());

        EventHandler_Shipping eventHandler_shipping = new EventHandler_Shipping(
                ch_clientShipping.right(),
                ch_billingShipping.right(),
                new ShippingState());

        List<Runnable> processes = List.of(
                () -> {
                    eventHandler_client.on(new EventAddItem(new CartItem("product1", 1)));
                    eventHandler_client.on(new EventPlaceOrder());
                },
                () -> {
                    while (true) {
                        eventHandler_cart.on();
                    }
                },
                () -> {
                    while (true) {
                        eventHandler_billing.on();
                    }
                },
                () -> {
                    while (true) {
                        eventHandler_shipping.on();
                    }
                });

        processes.parallelStream().forEach(Runnable::run);
    }

    public static Pair<LocalTypeChannel<Event>, LocalTypeChannel<Event>> makeChannel() {
        MessageQueue msqA = new MessageQueue();
        MessageQueue msqB = new MessageQueue();

        return new Pair<LocalTypeChannel<Event>, LocalTypeChannel<Event>>(
                new LocalTypeChannel<Event>(msqA, msqB), new LocalTypeChannel<Event>(msqB, msqA));
    }
}
