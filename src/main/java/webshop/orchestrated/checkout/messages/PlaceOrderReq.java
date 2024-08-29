package webshop.orchestrated.checkout.messages;

public class PlaceOrderReq implements CheckoutMessage {
	public final String userID;
	public final String currency;
	public final String address;
	public final String email;
	public final String creditCard;

	public PlaceOrderReq( String userID, String currency, String address, String email, String creditCard ) {
		this.userID = userID;
		this.currency = currency;
		this.address = address;
		this.email = email;
		this.creditCard = creditCard;
	}

	public CheckoutCommand getCommand() {
		return CheckoutCommand.PLACE_ORDER_REQ;
	}

}
