package webshop.common.models;

import java.util.List;
import java.util.ArrayList;

public class BillingState {
    public List<Order> orders;

    public BillingState() {
        this.orders = new ArrayList<Order>();
    }

    public Order makeOrder(String userID, Cart products) {
        Order order = new Order("order" + (orders.size() + 1), userID, products);
        orders.add(order);

        System.out.println("BILLING: Make order: " + order.orderID);

        return order;
    }
}