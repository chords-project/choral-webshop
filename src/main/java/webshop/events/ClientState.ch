package webshop.events;

public class ClientState@A {
    public final String@A userID;

    public ClientState(String@A userID) {
        this.userID = userID;
    }

    public void showOrderSummary(EventPlaceOrder@A event) {
        System@A.out.println("CLIENT: Order completed: "@A + event.order.orderID);
    }
}