package webshop.orchestrated;

import choral.lang.Unit;
import webshop.orchestrated.checkout.CheckoutService_Checkout;

class Webshop_Checkout {
	private CheckoutService_Checkout checkoutService;

	public Webshop_Checkout( CheckoutService_Checkout checkoutService ) {
		this.checkoutService = checkoutService;
	}

	public void run() {
		System.out.println( "CHECKOUT: started" );
		checkoutService.placeOrder( Unit.id );
		System.out.println( "CHECKOUT: done" );
	}

}
