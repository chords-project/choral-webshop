package webshop.orchestrated.checkout;

import choral.lang.Unit;
import webshop.orchestrated.cart.CartService_Cart;

public class CheckoutService_Cart {
	private CartService_Cart cartService;

	public CheckoutService_Cart( Unit ch_CheckoutClient, CartService_Cart cartService ) {
		this( cartService );
	}
	
	public CheckoutService_Cart( CartService_Cart cartService ) {
		this.cartService = cartService;
	}

	public Unit placeOrder( Unit req ) {
		return placeOrder();
	}
	
	public Unit placeOrder() {
		cartService.getCart( Unit.id );
		return Unit.id;
	}

}
