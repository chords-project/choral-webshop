package webshop.choreographic;

public class ClientState@A {
    public final String@A userID;

    public ClientState(String@A userID) {
        this.userID = userID;
    }

    public void showOrderSummary(ShippedOrder@A order) {
        System@A.out.println("CLIENT: Order completed: "@A + order.orderID);
    }
}