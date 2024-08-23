package webshop;

import webshop.cart.CartState;
import choral.lang.Unit;

class Webshop_Cart {
	private CartState cartState;

	public Webshop_Cart( Unit frontendState, CartState cartState ) {
		this( cartState );
	}
	
	public Webshop_Cart( CartState cartState ) {
		this.cartState = cartState;
	}

	public void run() {
		System.out.println( "Cart service started" );
	}

}
