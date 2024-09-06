package webshop.common.models;

public class ClientState {
    public final String userID;

    public ClientState(String userID) {
        this.userID = userID;
    }

    public void showOrderSummary(Order order) {
        System.out.println("CLIENT: Order completed: " + order.orderID);
    }
}