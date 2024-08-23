package webshop.cart;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;

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

}
