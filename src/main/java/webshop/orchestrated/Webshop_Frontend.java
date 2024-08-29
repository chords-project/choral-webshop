package webshop.orchestrated;

import webshop.orchestrated.checkout.messages.PlaceOrderReq;
import webshop.orchestrated.checkout.messages.PlaceOrderRes;
import webshop.orchestrated.checkout.CheckoutService_Client;

class Webshop_Frontend {
	private CheckoutService_Client checkoutService;

	public Webshop_Frontend( CheckoutService_Client checkoutService ) {
		this.checkoutService = checkoutService;
	}

	public void run() {
		System.out.println( "FRONTEND: started" );
		PlaceOrderReq req = new PlaceOrderReq( "1000", "USD", "Campusvej 55", "test@example.com", "555-444-333-222" );
		PlaceOrderRes res = checkoutService.placeOrder( req );
		System.out.println( "FRONTEND: Order placed for items: " + res.items );
		System.out.println( "FRONTEND: done" );
	}

}
