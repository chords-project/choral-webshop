package webshop.orchestrated;

import java.util.List;

import choral.runtime.LocalChannel.LocalChannel_A;
import choral.runtime.LocalChannel.LocalChannel_B;
import choral.runtime.Media.MessageQueue;
import choral.utils.Pair;
import webshop.orchestrated.cart.CartService_Cart;
import webshop.orchestrated.cart.CartService_Client;
import webshop.orchestrated.checkout.CheckoutService_Cart;
import webshop.orchestrated.checkout.CheckoutService_Checkout;
import webshop.orchestrated.checkout.CheckoutService_Client;

public class Main {
    public static void main(String[] args) {

        Pair<LocalChannel_A, LocalChannel_B> ch_CartCheckout = makeChannel();

        CartService_Cart cartServiceCart = new CartService_Cart(ch_CartCheckout.left());
        CartService_Client cartServiceCheckout = new CartService_Client(ch_CartCheckout.right());

        Pair<LocalChannel_A, LocalChannel_B> ch_CheckoutClient = makeChannel();

        CheckoutService_Client checkoutFrontend = new CheckoutService_Client(ch_CheckoutClient.left());
        CheckoutService_Cart checkoutCart = new CheckoutService_Cart(cartServiceCart);
        CheckoutService_Checkout checkoutService = new CheckoutService_Checkout(
                ch_CheckoutClient.right(), cartServiceCheckout);

        Webshop_Frontend webshopFrontend = new Webshop_Frontend(checkoutFrontend);
        Webshop_Cart webshopCart = new Webshop_Cart(checkoutCart);
        Webshop_Checkout webshopCheckout = new Webshop_Checkout(checkoutService);

        List<Runnable> services = List.of(
                () -> webshopFrontend.run(),
                () -> webshopCart.run(),
                () -> webshopCheckout.run());

        services.parallelStream().forEach(Runnable::run);
    }

    public static Pair<LocalChannel_A, LocalChannel_B> makeChannel() {
        MessageQueue msqA = new MessageQueue();
        MessageQueue msqB = new MessageQueue();

        return new Pair<LocalChannel_A, LocalChannel_B>(
                new LocalChannel_A(msqA, msqB), new LocalChannel_B(msqB, msqA));
    }
}