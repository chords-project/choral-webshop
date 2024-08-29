package webshop.orchestrated.cart;

import webshop.orchestrated.cart.messages.GetCartReq;
import choral.lang.Unit;
import webshop.orchestrated.cart.messages.AddItemReq;
import java.util.List;
import choral.channels.SymChannel_A;
import webshop.orchestrated.cart.messages.GetCartRes;
import webshop.orchestrated.cart.messages.EmptyCartReq;

public class CartService_Cart {
	private SymChannel_A < Object > ch;
	private CartState state;

	public CartService_Cart( SymChannel_A < Object > ch ) {
		this.ch = ch;
		this.state = new CartState();
	}

	public void addItem( Unit userID, Unit item ) {
		addItem();
	}
	
	public void emptyCart( Unit userID ) {
		emptyCart();
	}
	
	public Unit getCart( Unit userID ) {
		return getCart();
	}
	
	public void addItem() {
		AddItemReq req = ch.< AddItemReq >com( Unit.id );
		state.addItem( req.userID(), req.item() );
	}
	
	public void emptyCart() {
		EmptyCartReq req = ch.< EmptyCartReq >com( Unit.id );
		state.emptyCart( req.userID() );
	}
	
	public Unit getCart() {
		GetCartReq req = ch.< GetCartReq >com( Unit.id );
		List < CartItem > userItems = this.state.getUserCart( req.userID() );
		ch.< GetCartRes >com( new GetCartRes( req.userID(), userItems ) );
		return Unit.id;
	}

}
