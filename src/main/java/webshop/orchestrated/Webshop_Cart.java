package webshop.orchestrated;

import choral.lang.Unit;
import webshop.orchestrated.checkout.CheckoutService_Cart;

class Webshop_Cart {
	private CheckoutService_Cart checkoutService;

	public Webshop_Cart( CheckoutService_Cart checkoutService ) {
		this.checkoutService = checkoutService;
	}

	public void run() {
		System.out.println( "CART: started" );
		checkoutService.placeOrder( Unit.id );
		System.out.println( "CART: done" );
	}

}
