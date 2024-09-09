package webshop.loopback;

import choral.runtime.Media.MessageQueue;
import choral.utils.Pair;
import webshop.common.channel.LocalTypeChannel;
import webshop.loopback.cart.CartEvent;
import webshop.loopback.cart.CartEventAddItem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Loopback example");

        ExecutorService execService = Executors.newCachedThreadPool();

        Pair<LocalTypeChannel<CartEvent>, LocalTypeChannel<CartEvent>> ch_clientCart = Main.<CartEvent>makeChannel();
        ClientCartEvents_Client ev_client = new ClientCartEvents_Client(ch_clientCart.left());
        ClientCartEvents_Cart ev_cart = new ClientCartEvents_Cart(ch_clientCart.right());

        ev_client.enqueue(new CartEventAddItem());

        ev_client.run(execService);
        ev_cart.run(execService);

        try {
            execService.awaitTermination(1, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static <T> Pair<LocalTypeChannel<T>, LocalTypeChannel<T>> makeChannel() {
        MessageQueue msqA = new MessageQueue();
        MessageQueue msqB = new MessageQueue();

        return new Pair<LocalTypeChannel<T>, LocalTypeChannel<T>>(
                new LocalTypeChannel<T>(msqA, msqB),
                new LocalTypeChannel<T>(msqB, msqA));
    }
}
