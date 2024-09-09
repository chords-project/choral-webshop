package webshop.reactive;

import webshop.common.models.BillingState;
import webshop.common.models.CartItem;
import webshop.common.models.CartState;
import webshop.common.models.ClientState;
import webshop.common.models.Order;
import webshop.reactive.driver.Flow;
import webshop.reactive.driver.FlowChannel_A;
import webshop.reactive.driver.FlowChannel_B;
import webshop.reactive.driver.FlowQueue;
import webshop.reactive.driver.Flow.Action;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Reactive spawn example");

        ExecutorService executor = Executors.newCachedThreadPool();
        FlowQueue queue_client = new FlowQueue();
        FlowQueue queue_cart = new FlowQueue();
        FlowQueue queue_billing = new FlowQueue();

        runCart(executor, queue_client, queue_cart, queue_billing);
        runBilling(executor, queue_client, queue_cart, queue_billing);
        runClient(executor, queue_client, queue_cart, queue_billing);

        executor.awaitTermination(1, TimeUnit.DAYS);
    }

    static void runClient(
            ExecutorService executor,
            FlowQueue queue_client,
            FlowQueue queue_cart,
            FlowQueue queue_billing) {

        queue_client.onNewFlowReceived(flow -> {
            System.out.println("DRIVER: New flow received on Client queue: " + flow.action + "(" + flow.id + ")");
        });

        ClientState clientState = new ClientState("user1000");

        executor.execute(() -> {
            // Client kicks off the events
            Flow addItemFlow = Flow.generateFlow(Action.ADD_ITEM);
            FlowAddItem_Client clientAddItem = new FlowAddItem_Client(clientState,
                    new FlowChannel_A(addItemFlow, queue_cart));
            clientAddItem.addItem(new CartItem("sunglasses", 1));

            System.out.println("\nClient added item, now place order\n");

            // Place order after adding item
            Flow placeOrderFlow = Flow.generateFlow(Action.PLACE_ORDER);
            FlowPlaceOrder_Client clientPlaceOrder = new FlowPlaceOrder_Client(
                    clientState,
                    new FlowChannel_A(placeOrderFlow, queue_cart),
                    new FlowChannel_B(placeOrderFlow, queue_client));
            Order result = clientPlaceOrder.placeOrder();

            System.out.println("CLIENT RESULT: " + result.orderID);
            executor.shutdown();
        });
    }

    static void runCart(
            ExecutorService executor,
            FlowQueue queue_client,
            FlowQueue queue_cart,
            FlowQueue queue_billing) {

        CartState cartState = new CartState();

        queue_cart.onNewFlowReceived(flow -> {
            System.out.println("DRIVER: New flow received on Cart queue: " + flow.action + "(" + flow.id + ")");

            switch (flow.action) {
                case PLACE_ORDER:
                    FlowPlaceOrder_Cart cartPlaceOrder = new FlowPlaceOrder_Cart(cartState,
                            new FlowChannel_B(flow, queue_cart), new FlowChannel_A(flow, queue_billing));

                    executor.execute(cartPlaceOrder::placeOrder);
                    break;
                case ADD_ITEM:
                    FlowAddItem_Cart cartAddItem = new FlowAddItem_Cart(cartState, new FlowChannel_B(flow, queue_cart));
                    executor.execute(cartAddItem::addItem);
                    break;
                default:
                    throw new RuntimeException("Unknown flow action");
            }
        });
    }

    static void runBilling(
            ExecutorService executor,
            FlowQueue queue_client,
            FlowQueue queue_cart,
            FlowQueue queue_billing) {

        BillingState billingState = new BillingState();

        queue_billing.onNewFlowReceived(flow -> {
            System.out.println("DRIVER: New flow received on Billing queue: " + flow.action + "(" + flow.id + ")");

            switch (flow.action) {
                case PLACE_ORDER:
                    FlowPlaceOrder_Billing billingPlaceOrder = new FlowPlaceOrder_Billing(billingState,
                            new FlowChannel_B(flow, queue_billing), new FlowChannel_A(flow, queue_client));
                    executor.execute(billingPlaceOrder::placeOrder);
                    break;
                default:
                    throw new RuntimeException("Unknown flow action");
            }
        });
    }
}
