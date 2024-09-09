package webshop.loopback.cart;

import webshop.loopback.events.EventHandler_Receiver;
import choral.lang.Unit;
import webshop.common.channel.TypeSymChannel_B;

public class CartEventHandler_Cart implements EventHandler_Receiver < CartEvent > {
	private TypeSymChannel_B < CartEvent > ch;

	public CartEventHandler_Cart( TypeSymChannel_B < CartEvent > ch ) {
		this.ch = ch;
	}

	public void on( Unit event ) {
		on();
	}
	
	public void on() {
		{
			switch( ch.< CartEvent >tselect( Unit.id ) ){
				case CartEventAddItem ev_cart -> {
					
				}
				case CartEventGetCart ev_cart -> {
					
				}
				case CartEventEmptyCart ev_cart -> {
					
				}
				default -> {
					throw new RuntimeException( "Received unexpected label from select operation" );
				}
			}
		}
	}

}
