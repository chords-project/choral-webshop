package webshop.loopback.cart;

import webshop.loopback.events.LocalHandler;

public interface CartSenderLocalHandler extends LocalHandler {
	void onGetCart();
}
