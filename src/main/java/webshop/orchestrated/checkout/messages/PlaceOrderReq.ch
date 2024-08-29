package webshop.orchestrated.checkout.messages;

public class PlaceOrderReq@A implements CheckoutMessage@A {
    public final String@A userID;
    public final String@A currency;
    public final String@A address;
    public final String@A email;
    public final String@A creditCard;

    public PlaceOrderReq(String@A userID, String@A currency, String@A address, String@A email, String@A creditCard) {
        this.userID = userID;
        this.currency = currency;
        this.address = address;
        this.email = email;
        this.creditCard = creditCard;
    }

    public CheckoutCommand@A getCommand() {
        return CheckoutCommand@A.PLACE_ORDER_REQ;
    }
}