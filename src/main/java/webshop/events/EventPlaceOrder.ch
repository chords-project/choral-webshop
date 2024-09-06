package webshop.events;

import webshop.common.models.Cart;
import webshop.common.models.Order;

class EventPlaceOrder@A extends Event@A {
    public String@A userID;
    public Cart@A cart;
    public Order@A order;

    public EventPlaceOrder() {
        this.userID = null@A;
        this.cart = null@A;
        this.order = null@A;
    }

    public void addUserID(String@A userID) {
        this.userID = userID;
    }

    public void addUserCart(Cart@A cart) {
        this.cart = cart;
    }

    public void addOrder(Order@A order) {
        this.order = order;
    }

    public void addShipment(String@A address) {
        this.order.addShippingAddress(address);
    }

    public Command@A getCommand() {
        return Command@A.PLACE_ORDER;
    }
}