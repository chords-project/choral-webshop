package webshop.loopback.cart;

import webshop.common.Utils;
import webshop.loopback.events.EventHandler_Sender;
import webshop.common.channel.TypeSymChannel_A;

public class CartEventHandler_Sender implements EventHandler_Sender < CartEvent > {
	private TypeSymChannel_A < CartEvent > ch;

	public CartEventHandler_Sender( TypeSymChannel_A < CartEvent > ch ) {
		this.ch = ch;
	}

	public void on( CartEvent event ) {
		switch( event.getCommand() ){
			case ADD_ITEM -> {
				CartEventAddItem ev_sender = Utils.< CartEventAddItem >cast( event );
				ch.< CartEventAddItem >tselect( ev_sender );
			}
			case GET_CART -> {
				CartEventGetCart ev_sender = Utils.< CartEventGetCart >cast( event );
				ch.< CartEventGetCart >tselect( ev_sender );
			}
			case EMPTY_CART -> {
				CartEventEmptyCart ev_sender = Utils.< CartEventEmptyCart >cast( event );
				ch.< CartEventEmptyCart >tselect( ev_sender );
			}
		}
	}

}
