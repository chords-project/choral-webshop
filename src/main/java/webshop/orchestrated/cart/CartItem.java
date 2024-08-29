package webshop.orchestrated.cart;

public final class CartItem {
	private final String productID;
	private final int quantity;

	public CartItem( String productID, int quantity ) {
		this.productID = productID;
		this.quantity = quantity;
	}

	public String productID() {
		return this.productID;
	}
	
	public int quantity() {
		return this.quantity;
	}

}
