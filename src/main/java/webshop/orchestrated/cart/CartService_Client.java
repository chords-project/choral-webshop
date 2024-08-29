package webshop.orchestrated.cart;

import webshop.orchestrated.cart.messages.GetCartReq;
import webshop.orchestrated.cart.messages.AddItemReq;
import java.util.List;
import choral.lang.Unit;
import choral.channels.SymChannel_B;
import webshop.orchestrated.cart.messages.GetCartRes;
import webshop.orchestrated.cart.messages.EmptyCartReq;

public class CartService_Client {
	private SymChannel_B < Object > ch;

	public CartService_Client( SymChannel_B < Object > ch ) {
		this.ch = ch;
	}

	public void addItem( String userID, CartItem item ) {
		ch.< AddItemReq >com( new AddItemReq( userID, item ) );
	}
	
	public void emptyCart( String userID ) {
		ch.< EmptyCartReq >com( new EmptyCartReq( userID ) );
	}
	
	public List < CartItem > getCart( String userID ) {
		ch.< GetCartReq >com( new GetCartReq( userID ) );
		GetCartRes res = ch.< GetCartRes >com( Unit.id );
		return res.items();
	}

}
