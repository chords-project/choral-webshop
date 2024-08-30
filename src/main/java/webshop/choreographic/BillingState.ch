package webshop.choreographic;

import java.util.List;
import java.util.ArrayList;

public class BillingState@A {
    public List@A<Order> orders;

    public BillingState() {
        this.orders = new ArrayList@A<Order>();
    }

    public Order@A makeOrder(String@A userID, Cart@A products) {
        Order@A order = new Order@A("order"@A + (orders.size() + 1@A), userID, products);
        orders.add(order);

        System@A.out.println("BILLING: Make order: "@A + order.orderID);

        return order;
    }
}