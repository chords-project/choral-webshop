package webshop.orchestrated.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CartState {
	private HashMap < String, List < CartItem > > userItems;

	public CartState() {
		this.userItems = new HashMap < String, List < CartItem > >();
	}

	public void addItem( String userID, CartItem item ) {
		List < CartItem > userItems = this.userItems.get( userID );
		if( userItems == null ){
			userItems = new ArrayList < CartItem >();
		}
		userItems.add( item );
		this.userItems.put( userID, userItems );
	}
	
	public List < CartItem > getUserCart( String userID ) {
		List < CartItem > items = this.userItems.get( userID );
		if( items == null ){
			items = new ArrayList < CartItem >();
			this.userItems.put( userID, items );
		}
		return items;
	}
	
	public void emptyCart( String userID ) {
		userItems.remove( userID );
	}

}
