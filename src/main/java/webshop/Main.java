package webshop;

import java.util.List;

import webshop.cart.CartItem;
import webshop.cart.CartState;
import webshop.frontend.FrontendState;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        FrontendState frontend = new FrontendState("100");

        CartState cart = new CartState();
        cart.addItem(frontend.userID(), new CartItem("t-shirt", 3));
        cart.addItem(frontend.userID(), new CartItem("sunglasses", 1));

        Webshop_Frontend frontendService = new Webshop_Frontend(frontend);
        Webshop_Cart cartService = new Webshop_Cart(cart);

        List<Runnable> services = List.of(() -> frontendService.run(), () -> cartService.run());
        services.parallelStream().forEach(Runnable::run);
    }
}