package webshop.reactive;

import webshop.common.models.BillingState;
import webshop.common.models.CartItem;
import webshop.common.models.CartState;
import webshop.common.models.ClientState;
import webshop.common.models.Order;
import webshop.reactive.driver.IntegrityKey;
import webshop.reactive.driver.ReactiveChannel_A;
import webshop.reactive.driver.ReactiveChannel_B;
import webshop.reactive.driver.ReactiveQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static enum FlowAction {
        PLACE_ORDER, ADD_ITEM
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Reactive spawn example\n");

        ExecutorService executor = Executors.newCachedThreadPool();
        ReactiveQueue<FlowAction> queue_client = new ReactiveQueue<>();
        ReactiveQueue<FlowAction> queue_cart = new ReactiveQueue<>();
        ReactiveQueue<FlowAction> queue_billing = new ReactiveQueue<>();

        runCart(executor, queue_client, queue_cart, queue_billing);
        runBilling(executor, queue_client, queue_cart, queue_billing);
        runClient(executor, queue_client, queue_cart, queue_billing);

        executor.awaitTermination(1, TimeUnit.HOURS);
    }

    static void runClient(
            ExecutorService executor,
            ReactiveQueue<FlowAction> queue_client,
            ReactiveQueue<FlowAction> queue_cart,
            ReactiveQueue<FlowAction> queue_billing) {

        queue_client.onNewFlowReceived((key, cleanup) -> {
            System.out.println("DRIVER: New flow received on Client queue: " + key);

            cleanup.run();
        });

        ClientState clientState = new ClientState("user1000");

        executor.execute(() -> {
            // Client kicks off the events
            IntegrityKey<FlowAction> addItemKey = IntegrityKey.makeKey(FlowAction.ADD_ITEM);
            FlowAddItem_Client clientAddItem = new FlowAddItem_Client(
                    clientState,
                    new ReactiveChannel_A<>(addItemKey, queue_cart),
                    new ReactiveChannel_B<>(addItemKey, queue_client));

            clientAddItem.addItem(new CartItem("sunglasses", 1));

            System.out.println("\nClient added item, now place order\n");

            // Place order after adding item
            IntegrityKey<FlowAction> placeOrderKey = IntegrityKey.makeKey(FlowAction.PLACE_ORDER);
            FlowPlaceOrder_Client clientPlaceOrder = new FlowPlaceOrder_Client(
                    clientState,
                    new ReactiveChannel_A<>(placeOrderKey, queue_cart),
                    new ReactiveChannel_B<>(placeOrderKey, queue_client));

            Order result = clientPlaceOrder.placeOrder();

            System.out.println("CLIENT RESULT: " + result.orderID);
            executor.shutdown();
        });
    }

    static void runCart(
            ExecutorService executor,
            ReactiveQueue<FlowAction> queue_client,
            ReactiveQueue<FlowAction> queue_cart,
            ReactiveQueue<FlowAction> queue_billing) {

        CartState cartState = new CartState();

        queue_cart.onNewFlowReceived((key, cleanup) -> {
            System.out.println("DRIVER: New flow received on Cart queue: " + key);

            switch (key.action) {
                case PLACE_ORDER:
                    FlowPlaceOrder_Cart cartPlaceOrder = new FlowPlaceOrder_Cart(
                            cartState,
                            new ReactiveChannel_B<>(key, queue_cart),
                            new ReactiveChannel_A<>(key, queue_billing));

                    executor.submit(() -> {
                        cartPlaceOrder.placeOrder();
                        cleanup.run();
                    });

                    break;
                case ADD_ITEM:
                    FlowAddItem_Cart cartAddItem = new FlowAddItem_Cart(
                            cartState,
                            new ReactiveChannel_B<>(key, queue_cart),
                            new ReactiveChannel_A<>(key, queue_client));

                    executor.execute(() -> {
                        cartAddItem.addItem();
                        cleanup.run();
                    });

                    break;
                default:
                    throw new RuntimeException("Unknown flow action");
            }
        });
    }

    static void runBilling(
            ExecutorService executor,
            ReactiveQueue<FlowAction> queue_client,
            ReactiveQueue<FlowAction> queue_cart,
            ReactiveQueue<FlowAction> queue_billing) {

        BillingState billingState = new BillingState();

        queue_billing.onNewFlowReceived((key, cleanup) -> {
            System.out.println("DRIVER: New flow received on Billing queue: " + key);

            switch (key.action) {
                case PLACE_ORDER:
                    FlowPlaceOrder_Billing billingPlaceOrder = new FlowPlaceOrder_Billing(
                            billingState,
                            new ReactiveChannel_B<>(key, queue_billing),
                            new ReactiveChannel_A<>(key, queue_client));

                    executor.execute(() -> {
                        billingPlaceOrder.placeOrder();
                        cleanup.run();
                    });

                    break;
                default:
                    throw new RuntimeException("Unknown flow action");
            }
        });
    }
}
