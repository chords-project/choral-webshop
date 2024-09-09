package webshop.loopback;

import webshop.loopback.cart.CartEventHandler;
import webshop.loopback.cart.CartEvent;
import webshop.common.channel.TypeSymChannel;
import webshop.loopback.events.Events;
import java.util.concurrent.ExecutorService;

public class ClientCartEvents@(Client, Cart) {
    Events@(Client, Cart)<CartEvent> events;
    CartEventHandler@(Client, Cart) evHandler;

    public ClientCartEvents(TypeSymChannel@(Client, Cart)<CartEvent> ch) {
        this.events = new Events@(Client, Cart)<CartEvent>();
        this.evHandler = new CartEventHandler@(Client, Cart)(ch);
    }

    public void enqueue(CartEvent@Client event) {
        events.queue().enqueue(event);
    }

    public void run(
        ExecutorService@Client executorSender,
        ExecutorService@Cart executorReceiver
    ) {
        events.run(
            executorSender,
            executorReceiver,
            evHandler
        );
    }
}