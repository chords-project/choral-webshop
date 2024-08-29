package webshop.orchestrated.checkout;

import webshop.orchestrated.checkout.messages.PlaceOrderReq;
import choral.lang.Unit;
import webshop.orchestrated.checkout.messages.PlaceOrderRes;
import choral.channels.SymChannel_A;

public class CheckoutService_Client {
	private SymChannel_A < Object > ch_CheckoutClient;

	public CheckoutService_Client( SymChannel_A < Object > ch_CheckoutClient, Unit cartService ) {
		this( ch_CheckoutClient );
	}
	
	public CheckoutService_Client( SymChannel_A < Object > ch_CheckoutClient ) {
		this.ch_CheckoutClient = ch_CheckoutClient;
	}

	public PlaceOrderRes placeOrder( PlaceOrderReq req ) {
		ch_CheckoutClient.< PlaceOrderReq >com( req );
		return ch_CheckoutClient.< PlaceOrderRes >com( Unit.id );
	}

}
